package com.forestotzka.yurufu.slabee.devclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.GlDebugInfo;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.level.LevelInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 開発用：クライアントを自動操作して、テスト用ワールドの撮影と重さの測定を行い、終了する。
 * {@code -Dslabee.scene=true} のときだけ動く（./gradlew runScene）。
 *
 * 出力（実行フォルダ build/scene の下）：
 * - slabee-scene/screenshots/*.png … 決まった視点からのスクリーンショット
 * - slabee-scene/results.json … 撮影した内容と、重さの測定結果
 */
public class SceneCapture implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("SlabeeScene");
    private static final String WORLD_NAME = "slabee-scene";
    private static final int GROUP_SIZE = 6;
    private static final int MAX_TOTAL_TICKS = 20 * 60 * 45;

    private enum Phase { WAIT_TITLE, WAIT_WORLD, SHOTS, PERF, FINISH, DONE }

    private record View(String name, double x, double y, double z, float yaw, float pitch, String note) {}

    private Phase phase = Phase.WAIT_TITLE;
    private int totalTicks = 0;
    private int phaseTicks = 0;
    private int baseY;

    private final List<View> shots = new ArrayList<>();
    private final List<View> perfStages = new ArrayList<>();
    private int index = 0;
    private int stepTicks = 0;
    private int stableTicks = 0;
    private boolean teleported = false;
    private int lastCompletedChunks = -1;

    private boolean recording = false;
    private long lastFrameNanos = 0;
    private final List<Long> frameNanos = new ArrayList<>();
    private int warmupFrames = 0;

    private final JsonArray shotResults = new JsonArray();
    private final JsonArray perfResults = new JsonArray();
    private File outDir;

    @Override
    public void onInitializeClient() {
        if (!Boolean.getBoolean("slabee.scene")) return;
        LOGGER.info("Slabee scene capture is enabled");
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        WorldRenderEvents.END.register(context -> onFrame());
    }

    private void tick(MinecraftClient client) {
        totalTicks++;
        phaseTicks++;
        if (totalTicks > MAX_TOTAL_TICKS && phase != Phase.DONE && phase != Phase.FINISH) {
            LOGGER.error("Scene capture timed out in phase {}", phase);
            setPhase(Phase.FINISH);
        }

        switch (phase) {
            case WAIT_TITLE -> {
                // 初回起動時はタイトル画面の前にアクセシビリティの案内画面が出るので、
                // 画面の種類ではなく「読み込みが終わってワールドに入っていない」ことで判断する
                if (client.world == null && client.getOverlay() == null && client.currentScreen != null && phaseTicks > 40) {
                    client.options.onboardAccessibility = false;
                    configureOptions(client.options);
                    outDir = new File(client.runDirectory, WORLD_NAME);
                    deleteRecursively(outDir.toPath());
                    deleteRecursively(client.runDirectory.toPath().resolve("saves").resolve(WORLD_NAME));
                    try {
                        Files.createDirectories(outDir.toPath().resolve("screenshots"));
                    } catch (IOException e) {
                        LOGGER.error("Could not create screenshot directory", e);
                    }
                    createWorld(client);
                    setPhase(Phase.WAIT_WORLD);
                }
            }
            case WAIT_WORLD -> {
                if (client.world != null && client.player != null && client.getServer() != null && client.currentScreen == null) {
                    if (phaseTicks > 40) {
                        buildScene(client.getServer());
                        setPhase(Phase.SHOTS);
                    }
                }
            }
            case SHOTS -> stepShot(client);
            case PERF -> stepPerf(client);
            case FINISH -> {
                if (phaseTicks == 1) writeResults(client);
                if (phaseTicks > 60) {
                    setPhase(Phase.DONE);
                    client.scheduleStop();
                }
            }
            case DONE -> {}
        }
    }

    private void setPhase(Phase next) {
        LOGGER.info("Scene capture phase: {} -> {}", phase, next);
        phase = next;
        phaseTicks = 0;
        index = 0;
        stepTicks = 0;
        stableTicks = 0;
        teleported = false;
    }

    private static void configureOptions(GameOptions options) {
        options.hudHidden = true;
        options.pauseOnLostFocus = false;
        options.getViewDistance().setValue(8);
        options.getSimulationDistance().setValue(8);
        // 環境変数 SLABEE_SCENE_MAX_FPS=60（または -Dslabee.scene.maxFps=60）で、フレーム数の上限を固定して測れる
        // （描画が速くなるとフレーム数が増えてCPUを多く使い、同じマシンのサーバー側の測定に影響するため）
        options.getMaxFps().setValue(maxFps());
        options.getEnableVsync().setValue(false);
        options.getCloudRenderMode().setValue(CloudRenderMode.OFF);
        options.getBobView().setValue(false);
        options.getParticles().setValue(ParticlesMode.MINIMAL);
        options.getFov().setValue(70);
        options.getGraphicsMode().setValue(GraphicsMode.FANCY);
        options.getAo().setValue(true);
    }

    private static int maxFps() {
        String env = System.getenv("SLABEE_SCENE_MAX_FPS");
        if (env != null) {
            try {
                return Integer.parseInt(env.trim());
            } catch (NumberFormatException ignored) {
            }
        }
        return Integer.getInteger("slabee.scene.maxFps", 260);
    }

    private static void createWorld(MinecraftClient client) {
        GameRules rules = new GameRules();
        rules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, null);
        rules.get(GameRules.DO_WEATHER_CYCLE).set(false, null);
        rules.get(GameRules.DO_MOB_SPAWNING).set(false, null);
        rules.get(GameRules.DO_FIRE_TICK).set(false, null);
        rules.get(GameRules.RANDOM_TICK_SPEED).set(0, null);

        DataConfiguration data = new DataConfiguration(
                new DataPackSettings(List.of("vanilla", "fabric"), List.of()),
                FeatureFlags.DEFAULT_ENABLED_FEATURES);
        LevelInfo info = new LevelInfo(WORLD_NAME, GameMode.CREATIVE, false, Difficulty.PEACEFUL, true, rules, data);
        GeneratorOptions generator = new GeneratorOptions(0L, false, false);

        client.createIntegratedServerLoader().createAndStart(WORLD_NAME, info, generator,
                registries -> registries.get(RegistryKeys.WORLD_PRESET).entryOf(WorldPresets.FLAT).value().createDimensionsRegistryHolder(),
                client.currentScreen);
    }

    private void buildScene(IntegratedServer server) {
        server.submitAndJoin(() -> {
            ServerWorld world = server.getOverworld();
            world.setTimeOfDay(6000);
            // 撮影中は自動保存を止める（保存中の表示が写り込んだり、測定の数字が揺れたりするため）
            world.savingDisabled = true;
            world.setWeather(1000000, 0, false, false);
            baseY = world.getTopY(Heightmap.Type.MOTION_BLOCKING, 0, 0);

            List<Block> slabs = SceneBuilder.allSlabs();
            List<Block> glass = SceneBuilder.glassSlabs(slabs);
            long start = System.nanoTime();
            SceneBuilder.buildCatalog(world, baseY, slabs);
            SceneBuilder.buildAdjacent(world, baseY, glass);
            SceneBuilder.buildReference(world, baseY, glass);
            SceneBuilder.buildPairs(world, baseY);
            SceneBuilder.buildSlabeeField(world, baseY);
            SceneBuilder.buildVanillaField(world, baseY);
            LOGGER.info("Built scene with {} slab types ({} glass) at y={} in {} ms",
                    slabs.size(), glass.size(), baseY, (System.nanoTime() - start) / 1_000_000);

            planViews(slabs, glass);
        });
    }

    private void planViews(List<Block> slabs, List<Block> glass) {
        int rowsDepth = (SceneBuilder.ROWS.length - 1) * SceneBuilder.ROW_STEP;
        int groups = (slabs.size() + GROUP_SIZE - 1) / GROUP_SIZE;
        for (int g = 0; g < groups; g++) {
            int first = g * GROUP_SIZE;
            int last = Math.min(slabs.size(), first + GROUP_SIZE) - 1;
            double centerX = (first + last) / 2.0 * SceneBuilder.COLUMN_STEP + 0.5;
            String note = idsOf(slabs.subList(first, last + 1));
            shots.add(new View(String.format("catalog_%02d_south", g), centerX, baseY + 7, -5, 0f, 42f, note));
            shots.add(new View(String.format("catalog_%02d_north", g), centerX, baseY + 7, rowsDepth + 6, 180f, 42f, note));
        }

        int glassGroup = 4;
        for (int g = 0; g * glassGroup < glass.size(); g++) {
            int first = g * glassGroup;
            int last = Math.min(glass.size(), first + glassGroup) - 1;
            double centerX = (first * 5 + last * 5 + 2) / 2.0 + 0.5;
            String note = idsOf(glass.subList(first, last + 1));
            shots.add(new View(String.format("adjacent_%02d_south", g), centerX, baseY + 5, SceneBuilder.ADJACENT_Z - 8, 0f, 22f, note));
            shots.add(new View(String.format("adjacent_%02d_top", g), centerX, baseY + 14, SceneBuilder.ADJACENT_Z + 4, 0f, 89f, note));
        }

        for (int k = 0; k < glass.size(); k++) {
            double centerX = k * 10 + 4.5;
            String note = Registries.BLOCK.getId(glass.get(k)).toString() + "：左=通常ブロック（下から石・ガラス・石・ガラス）、右=ダブルハーフ（上=ガラス・下=石）×2段";
            shots.add(new View(String.format("reference_%02d", k), centerX, baseY + 3.5, SceneBuilder.REFERENCE_Z - 6, 0f, 18f, note));
        }

        for (int k = 0; k < SceneBuilder.PAIRS.length; k++) {
            double centerX = k * 8 + 3.0;
            String note = "上=" + SceneBuilder.PAIRS[k][0] + "、下=" + SceneBuilder.PAIRS[k][1] + "：左=通常ブロック（2段）、右=ダブルハーフ";
            shots.add(new View(String.format("pair_%02d", k), centerX, baseY + 4.5, SceneBuilder.PAIR_Z - 3, 0f, 40f, note));
        }

        double fieldCenterX = SceneBuilder.FIELD_SIZE / 2.0;
        perfStages.add(new View("empty", fieldCenterX, baseY + 25, SceneBuilder.EMPTY_Z - 15, 0f, 40f, "何も置いていない場所"));
        perfStages.add(new View("slabee_field", fieldCenterX, baseY + 25, SceneBuilder.SLABEE_FIELD_Z - 15, 0f, 40f,
                "Slabeeのダブルハーフ（オーク/石）100x100 = 10,000個"));
        perfStages.add(new View("vanilla_field", fieldCenterX, baseY + 25, SceneBuilder.VANILLA_FIELD_Z - 15, 0f, 40f,
                "バニラのダブルハーフ（オーク/石）100x100 = 10,000個"));
    }

    private static String idsOf(List<Block> blocks) {
        List<String> ids = new ArrayList<>();
        for (Block b : blocks) ids.add(Registries.BLOCK.getId(b).toString());
        return String.join(", ", ids);
    }

    /** テレポートして、描画が落ち着くまで待つ。true なら準備完了。 */
    private boolean moveAndSettle(MinecraftClient client, View view, int minTicks) {
        stepTicks++;
        if (!teleported) {
            IntegratedServer server = client.getServer();
            if (server == null) return false;
            server.execute(() -> {
                ServerPlayerEntity player = server.getPlayerManager().getPlayerList().getFirst();
                player.getAbilities().flying = true;
                player.sendAbilitiesUpdate();
                player.networkHandler.requestTeleport(view.x(), view.y(), view.z(), view.yaw(), view.pitch());
            });
            teleported = true;
            stableTicks = 0;
            return false;
        }
        boolean arrived = client.player != null
                && client.player.squaredDistanceTo(view.x(), view.y(), view.z()) < 0.25;
        // 描画範囲のチャンクが読み込み・組み立てを終えて、完成数が増えなくなるまで待つ
        // （実機では遠くの地形が読み込み途中のまま測ってしまうことがあったため）
        int completed = client.worldRenderer.getCompletedChunkCount();
        // 光の計算も終わるまで待つ（ブロックを置いたあと遅れて進むため、終わる前に撮ると地面の陰影が毎回ずれる）
        boolean lightQuiet = client.world != null && !client.world.getLightingProvider().hasUpdates()
                && (client.getServer() == null || !client.getServer().getOverworld().getLightingProvider().hasUpdates());
        if (arrived && lightQuiet && client.worldRenderer.isTerrainRenderComplete() && completed == lastCompletedChunks) {
            stableTicks++;
        } else {
            stableTicks = 0;
        }
        lastCompletedChunks = completed;
        if (stepTicks > 20 * 60) {
            LOGGER.warn("View {} did not settle in time; continuing anyway", view.name());
            return true;
        }
        return stepTicks >= minTicks && stableTicks >= 40;
    }

    private void nextStep() {
        index++;
        stepTicks = 0;
        stableTicks = 0;
        teleported = false;
    }

    private void stepShot(MinecraftClient client) {
        if (index >= shots.size()) {
            setPhase(Phase.PERF);
            return;
        }
        View view = shots.get(index);
        if (!moveAndSettle(client, view, 40)) return;

        ScreenshotRecorder.saveScreenshot(outDir, view.name() + ".png", client.getFramebuffer(), message -> {});
        JsonObject result = viewJson(view);
        result.addProperty("file", "screenshots/" + view.name() + ".png");
        shotResults.add(result);
        LOGGER.info("Captured {} ({}/{})", view.name(), index + 1, shots.size());
        nextStep();
    }

    private void stepPerf(MinecraftClient client) {
        if (index >= perfStages.size()) {
            setPhase(Phase.FINISH);
            return;
        }
        View stage = perfStages.get(index);
        if (!recording) {
            if (!moveAndSettle(client, stage, 100)) return;
            frameNanos.clear();
            lastFrameNanos = 0;
            warmupFrames = 30;
            recording = true;
            ScreenshotRecorder.saveScreenshot(outDir, "perf_" + stage.name() + ".png", client.getFramebuffer(), message -> {});
            return;
        }
        if (frameNanos.size() < 300 && stepTicks < 20 * 60 * 3) {
            stepTicks++;
            return;
        }
        recording = false;

        JsonObject result = viewJson(stage);
        long[] sorted = frameNanos.stream().mapToLong(Long::longValue).sorted().toArray();
        double avg = Arrays.stream(sorted).average().orElse(0) / 1e6;
        result.addProperty("frames", sorted.length);
        result.addProperty("completedChunks", client.worldRenderer.getCompletedChunkCount());
        result.addProperty("frameMsAvg", round(avg));
        result.addProperty("frameMsP50", round(percentile(sorted, 0.50) / 1e6));
        result.addProperty("frameMsP95", round(percentile(sorted, 0.95) / 1e6));
        result.addProperty("frameMsMax", round((sorted.length == 0 ? 0 : sorted[sorted.length - 1]) / 1e6));
        IntegratedServer server = client.getServer();
        if (server != null) {
            result.addProperty("serverTickMsAvg", round(server.getAverageNanosPerTick() / 1e6));
        }
        perfResults.add(result);
        LOGGER.info("Perf {}: {}", stage.name(), result);
        nextStep();
    }

    private void onFrame() {
        if (!recording) return;
        long now = System.nanoTime();
        if (lastFrameNanos != 0) {
            if (warmupFrames > 0) {
                warmupFrames--;
            } else {
                frameNanos.add(now - lastFrameNanos);
            }
        }
        lastFrameNanos = now;
    }

    private void writeResults(MinecraftClient client) {
        if (outDir == null) outDir = new File(client.runDirectory, WORLD_NAME);
        JsonObject root = new JsonObject();
        root.addProperty("capturedAt", OffsetDateTime.now().toString());
        root.addProperty("glRenderer", GlDebugInfo.getRenderer());
        root.addProperty("glVersion", GlDebugInfo.getVersion());
        root.addProperty("window", client.getWindow().getFramebufferWidth() + "x" + client.getWindow().getFramebufferHeight());
        root.addProperty("baseY", baseY);
        root.addProperty("maxFps", client.options.getMaxFps().getValue());
        root.add("shots", shotResults);
        root.add("perf", perfResults);
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        try {
            Files.createDirectories(outDir.toPath());
            Files.writeString(outDir.toPath().resolve("results.json"), gson.toJson(root), StandardCharsets.UTF_8);
            LOGGER.info("Wrote scene results to {}", outDir.toPath().resolve("results.json"));
        } catch (IOException e) {
            LOGGER.error("Failed to write scene results", e);
        }
    }

    private static JsonObject viewJson(View view) {
        JsonObject o = new JsonObject();
        o.addProperty("name", view.name());
        o.addProperty("x", view.x());
        o.addProperty("y", view.y());
        o.addProperty("z", view.z());
        o.addProperty("yaw", view.yaw());
        o.addProperty("pitch", view.pitch());
        o.addProperty("note", view.note());
        return o;
    }

    private static double percentile(long[] sorted, double p) {
        if (sorted.length == 0) return 0;
        int i = (int) Math.min(sorted.length - 1, Math.floor(p * (sorted.length - 1)));
        return sorted[i];
    }

    private static double round(double v) {
        return Math.round(v * 1000.0) / 1000.0;
    }

    private static void deleteRecursively(Path path) {
        if (!Files.exists(path)) return;
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    LOGGER.warn("Could not delete {}", p);
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Could not clean {}", path);
        }
    }
}
