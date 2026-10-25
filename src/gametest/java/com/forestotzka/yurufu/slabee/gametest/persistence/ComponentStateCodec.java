package com.forestotzka.yurufu.slabee.gametest.persistence;

import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * B2 永続化 spike：ダブルハーフの中のハーフ1枚ずつの状態を、バージョン付きの NBT と相互に変換する。
 * <p>
 * 実際の Block Entity にはまだつないでいない（Stage 6 で本実装する）。ここでは
 * 「検査済みの {@link NbtHelper} 経路」「property の所有権」「旧形式の読み込み」「壊れたデータの扱い」が
 * Minecraft 1.21.1 で成り立つかを確かめる。
 * <p>
 * 保存形式（追加型）：
 * <pre>
 * positive_slab: {id: "minecraft:oak_slab"}      ← 旧形式。今の Slabee もこれを読める
 * negative_slab: {id: "minecraft:stone_slab"}
 * axis: "X"                                      ← 縦のダブルハーフだけ。軸はここに1回だけ保存する
 * slabee_components: {version: 1,
 *     positive: {Name: ..., Properties: {...}},  ← ハーフ自身が持つ property だけ
 *     negative: {...}}
 * </pre>
 */
public final class ComponentStateCodec {
    public static final int SCHEMA_VERSION = 1;
    public static final String COMPONENTS_KEY = "slabee_components";
    public static final String VERSION_KEY = "version";
    public static final String POSITIVE_KEY = "positive";
    public static final String NEGATIVE_KEY = "negative";
    public static final String LEGACY_POSITIVE_KEY = "positive_slab";
    public static final String LEGACY_NEGATIVE_KEY = "negative_slab";
    public static final String LEGACY_ID_KEY = "id";
    public static final String AXIS_KEY = "axis";

    /**
     * ハーフ自身の状態として保存しない property。
     * TYPE／FACING はスロットから、SNOWY／葉の DISTANCE は周りから決まり、
     * WATERLOGGED と IS_DOUBLE はダブルハーフ全体（容器）の状態なので、片側の正データにしない。
     */
    static final Set<Property<?>> NON_COMPONENT_PROPERTIES = Set.of(
            Properties.SLAB_TYPE,
            VerticalSlabBlock.FACING,
            Properties.WATERLOGGED,
            Properties.SNOWY,
            Properties.DISTANCE_1_7,
            ModProperties.IS_DOUBLE
    );

    private ComponentStateCodec() {}

    public enum Family { HORIZONTAL, VERTICAL }

    public enum Side { POSITIVE, NEGATIVE }

    /** 読み込み時に見つかった問題。テストで確認し、診断ログにも出す */
    public enum Issue {
        MISSING_COMPONENT,
        MALFORMED_ID,
        UNKNOWN_ID,
        UNSUPPORTED_BLOCK,
        UNKNOWN_PROPERTY,
        INVALID_PROPERTY_VALUE,
        MALFORMED_SCHEMA,
        FUTURE_SCHEMA,
        INVALID_AXIS,
        AXIS_CONFLICT
    }

    public record Problem(Issue issue, String detail) {}

    /** どの形式から読んだか */
    public enum Source { V1, LEGACY }

    /** 読み込み後にセルがどうなるか */
    public enum Outcome { DOUBLE, SINGLE_POSITIVE, SINGLE_NEGATIVE, AIR }

    /** どのブロックをハーフとして受け入れるか。通常は Slabee の対応表、テストでは差し替える */
    @FunctionalInterface
    public interface Catalog {
        boolean accepts(Family family, Block block);

        Catalog SLABEE = (family, block) -> family == Family.HORIZONTAL
                ? ModBlockMap.isMappedSlab(block)
                : ModBlockMap.isMappedVerticalSlab(block);
    }

    public record Decoded(Family family, Source source,
                          Optional<BlockState> positive, Optional<BlockState> negative,
                          @Nullable VerticalSlabAxis axis, List<Problem> problems) {
        public Outcome outcome() {
            if (positive.isPresent() && negative.isPresent()) return Outcome.DOUBLE;
            if (positive.isPresent()) return Outcome.SINGLE_POSITIVE;
            if (negative.isPresent()) return Outcome.SINGLE_NEGATIVE;
            return Outcome.AIR;
        }

        /** 片側だけ残ったときの単体ブロック、両方失ったときは air。両方残っていれば empty */
        public Optional<BlockState> singleizedState() {
            return switch (outcome()) {
                case DOUBLE -> Optional.empty();
                case SINGLE_POSITIVE -> positive;
                case SINGLE_NEGATIVE -> negative;
                case AIR -> Optional.of(Blocks.AIR.getDefaultState());
            };
        }

        public boolean has(Issue issue) {
            return problems.stream().anyMatch(p -> p.issue() == issue);
        }
    }

    // ---- 書き込み ----

    /**
     * 両側のハーフを保存する。旧形式の id も一緒に書くので、今の Slabee でも読める。
     * {@code axis} は縦のダブルハーフだけで使い、横では null。
     */
    public static void write(NbtCompound nbt, Family family, BlockState positive, BlockState negative, @Nullable VerticalSlabAxis axis) {
        nbt.put(LEGACY_POSITIVE_KEY, legacyComponent(positive));
        nbt.put(LEGACY_NEGATIVE_KEY, legacyComponent(negative));
        if (family == Family.VERTICAL) {
            nbt.putString(AXIS_KEY, (axis == VerticalSlabAxis.Z) ? "Z" : "X");
        }

        NbtCompound components = new NbtCompound();
        components.putInt(VERSION_KEY, SCHEMA_VERSION);
        components.put(POSITIVE_KEY, encodeComponent(positive));
        components.put(NEGATIVE_KEY, encodeComponent(negative));
        nbt.put(COMPONENTS_KEY, components);
    }

    /** {@link NbtHelper#fromBlockState} の結果から、ハーフ自身が持たない property を取り除く */
    static NbtCompound encodeComponent(BlockState state) {
        NbtCompound encoded = NbtHelper.fromBlockState(state);
        if (encoded.contains("Properties", NbtElement.COMPOUND_TYPE)) {
            NbtCompound properties = encoded.getCompound("Properties");
            for (Property<?> property : state.getProperties()) {
                if (NON_COMPONENT_PROPERTIES.contains(property)) {
                    properties.remove(property.getName());
                }
            }
            if (properties.isEmpty()) {
                encoded.remove("Properties");
            }
        }
        return encoded;
    }

    private static NbtCompound legacyComponent(BlockState state) {
        NbtCompound legacy = new NbtCompound();
        legacy.putString(LEGACY_ID_KEY, Registries.BLOCK.getId(state.getBlock()).toString());
        return legacy;
    }

    // ---- 読み込み ----

    /**
     * 保存データを読む。新形式があればそれを、なければ（または読めない版なら）旧形式を読む。
     *
     * @param blockStateAxis 縦のダブルハーフの外側 BlockState の AXIS（分からなければ null）
     */
    public static Decoded read(NbtCompound nbt, Family family, RegistryEntryLookup<Block> blocks, Catalog catalog,
                               @Nullable VerticalSlabAxis blockStateAxis, Diagnostics diagnostics) {
        List<Problem> problems = new ArrayList<>();

        Source source = Source.LEGACY;
        NbtCompound components = null;
        if (nbt.contains(COMPONENTS_KEY)) {
            if (nbt.contains(COMPONENTS_KEY, NbtElement.COMPOUND_TYPE)
                    && nbt.getCompound(COMPONENTS_KEY).contains(VERSION_KEY, NbtElement.NUMBER_TYPE)) {
                int version = nbt.getCompound(COMPONENTS_KEY).getInt(VERSION_KEY);
                if (version == SCHEMA_VERSION) {
                    source = Source.V1;
                    components = nbt.getCompound(COMPONENTS_KEY);
                } else if (version > SCHEMA_VERSION) {
                    // 新しい Slabee で保存された：読めない部分は捨て、一緒に書かれている旧形式で読む
                    problems.add(new Problem(Issue.FUTURE_SCHEMA, "version=" + version));
                } else {
                    problems.add(new Problem(Issue.MALFORMED_SCHEMA, "version=" + version));
                }
            } else {
                problems.add(new Problem(Issue.MALFORMED_SCHEMA, String.valueOf(nbt.get(COMPONENTS_KEY))));
            }
        }

        VerticalSlabAxis axis = (family == Family.VERTICAL) ? readAxis(nbt, source, blockStateAxis, problems) : null;

        Optional<BlockState> positive;
        Optional<BlockState> negative;
        if (components != null) {
            positive = decodeComponent(components, POSITIVE_KEY, family, Side.POSITIVE, axis, blocks, catalog, problems);
            negative = decodeComponent(components, NEGATIVE_KEY, family, Side.NEGATIVE, axis, blocks, catalog, problems);
        } else {
            positive = decodeLegacyComponent(nbt, LEGACY_POSITIVE_KEY, family, Side.POSITIVE, axis, blocks, catalog, problems);
            negative = decodeLegacyComponent(nbt, LEGACY_NEGATIVE_KEY, family, Side.NEGATIVE, axis, blocks, catalog, problems);
        }

        for (Problem problem : problems) {
            diagnostics.report(problem.issue(), problem.detail());
        }
        return new Decoded(family, source, positive, negative, axis, List.copyOf(problems));
    }

    /**
     * 縦の軸を決める。新形式では外側 BlockState を正とし、NBT と食い違えば診断する。
     * 旧形式では NBT の axis を移行の入力にし、なければ BlockState、それもなければ X。
     */
    private static VerticalSlabAxis readAxis(NbtCompound nbt, Source source, @Nullable VerticalSlabAxis blockStateAxis, List<Problem> problems) {
        VerticalSlabAxis nbtAxis = null;
        if (nbt.contains(AXIS_KEY, NbtElement.STRING_TYPE)) {
            String raw = nbt.getString(AXIS_KEY);
            if ("X".equals(raw)) {
                nbtAxis = VerticalSlabAxis.X;
            } else if ("Z".equals(raw)) {
                nbtAxis = VerticalSlabAxis.Z;
            } else {
                problems.add(new Problem(Issue.INVALID_AXIS, raw));
            }
        } else if (nbt.contains(AXIS_KEY)) {
            problems.add(new Problem(Issue.INVALID_AXIS, String.valueOf(nbt.get(AXIS_KEY))));
        }

        if (source == Source.V1 && blockStateAxis != null) {
            if (nbtAxis != null && nbtAxis != blockStateAxis) {
                problems.add(new Problem(Issue.AXIS_CONFLICT, "blockState=" + blockStateAxis.asString() + " nbt=" + nbtAxis.asString()));
            }
            return blockStateAxis;
        }
        if (nbtAxis != null) return nbtAxis;
        if (blockStateAxis != null) return blockStateAxis;
        return VerticalSlabAxis.X;
    }

    private static Optional<BlockState> decodeComponent(NbtCompound components, String key, Family family, Side side,
                                                        @Nullable VerticalSlabAxis axis, RegistryEntryLookup<Block> blocks,
                                                        Catalog catalog, List<Problem> problems) {
        if (!components.contains(key, NbtElement.COMPOUND_TYPE)) {
            problems.add(new Problem(Issue.MISSING_COMPONENT, side.name()));
            return Optional.empty();
        }
        NbtCompound encoded = components.getCompound(key);
        if (!encoded.contains("Name", NbtElement.STRING_TYPE)) {
            problems.add(new Problem(Issue.MISSING_COMPONENT, side.name() + " Name"));
            return Optional.empty();
        }

        Optional<Block> block = resolveBlock(encoded.getString("Name"), family, side, blocks, catalog, problems);
        if (block.isEmpty()) return Optional.empty();

        // 先に property を検査し、通ったものだけを NbtHelper に渡す（失敗が黙って捨てられないように）
        NbtCompound sanitized = new NbtCompound();
        sanitized.putString("Name", encoded.getString("Name"));
        if (encoded.contains("Properties", NbtElement.COMPOUND_TYPE)) {
            NbtCompound validProperties = validProperties(block.get(), encoded.getCompound("Properties"), side, problems);
            if (!validProperties.isEmpty()) {
                sanitized.put("Properties", validProperties);
            }
        }

        BlockState state = NbtHelper.toBlockState(blocks, sanitized);
        return Optional.of(applySlot(state, family, side, axis));
    }

    private static Optional<BlockState> decodeLegacyComponent(NbtCompound nbt, String key, Family family, Side side,
                                                              @Nullable VerticalSlabAxis axis, RegistryEntryLookup<Block> blocks,
                                                              Catalog catalog, List<Problem> problems) {
        if (!nbt.contains(key, NbtElement.COMPOUND_TYPE)
                || !nbt.getCompound(key).contains(LEGACY_ID_KEY, NbtElement.STRING_TYPE)) {
            problems.add(new Problem(Issue.MISSING_COMPONENT, side.name()));
            return Optional.empty();
        }
        // 旧形式の facing（横）は今の Slabee でも使っていないので読まない
        return resolveBlock(nbt.getCompound(key).getString(LEGACY_ID_KEY), family, side, blocks, catalog, problems)
                .map(block -> applySlot(block.getDefaultState(), family, side, axis));
    }

    /** id を検査してブロックを引く。壊れた・消えた・対応外の id ならそのハーフを失ったものとする */
    private static Optional<Block> resolveBlock(String rawId, Family family, Side side, RegistryEntryLookup<Block> blocks,
                                                Catalog catalog, List<Problem> problems) {
        Identifier id = Identifier.tryParse(rawId);
        if (id == null) {
            problems.add(new Problem(Issue.MALFORMED_ID, side.name() + " " + rawId));
            return Optional.empty();
        }
        Optional<RegistryEntry.Reference<Block>> entry = blocks.getOptional(RegistryKey.of(RegistryKeys.BLOCK, id));
        if (entry.isEmpty()) {
            problems.add(new Problem(Issue.UNKNOWN_ID, side.name() + " " + id));
            return Optional.empty();
        }
        Block block = entry.get().value();
        if (!catalog.accepts(family, block)) {
            problems.add(new Problem(Issue.UNSUPPORTED_BLOCK, side.name() + " " + id + " (" + family.name() + ")"));
            return Optional.empty();
        }
        return Optional.of(block);
    }

    /** 読める property だけを残す。所有していない property は黙って無視し、読めないものは診断する */
    private static NbtCompound validProperties(Block block, NbtCompound properties, Side side, List<Problem> problems) {
        StateManager<Block, BlockState> stateManager = block.getStateManager();
        NbtCompound valid = new NbtCompound();
        for (String name : properties.getKeys()) {
            Property<?> property = stateManager.getProperty(name);
            if (property == null) {
                problems.add(new Problem(Issue.UNKNOWN_PROPERTY, side.name() + " " + name));
                continue;
            }
            if (NON_COMPONENT_PROPERTIES.contains(property)) {
                continue;
            }
            if (!properties.contains(name, NbtElement.STRING_TYPE) || property.parse(properties.getString(name)).isEmpty()) {
                problems.add(new Problem(Issue.INVALID_PROPERTY_VALUE, side.name() + " " + name + "=" + properties.get(name)));
                continue;
            }
            valid.putString(name, properties.getString(name));
        }
        return valid;
    }

    /** スロットから決まる TYPE／FACING を入れる。片側だけ残ったときも、そのまま単体ブロックになる向き */
    static BlockState applySlot(BlockState state, Family family, Side side, @Nullable VerticalSlabAxis axis) {
        if (family == Family.HORIZONTAL) {
            if (state.contains(Properties.SLAB_TYPE)) {
                return state.with(Properties.SLAB_TYPE, side == Side.POSITIVE ? SlabType.TOP : SlabType.BOTTOM);
            }
            return state;
        }
        if (state.contains(VerticalSlabBlock.FACING)) {
            return state.with(VerticalSlabBlock.FACING, verticalFacing(side, axis));
        }
        return state;
    }

    static Direction verticalFacing(Side side, @Nullable VerticalSlabAxis axis) {
        if (axis == VerticalSlabAxis.Z) {
            return side == Side.POSITIVE ? Direction.SOUTH : Direction.NORTH;
        }
        return side == Side.POSITIVE ? Direction.EAST : Direction.WEST;
    }
}
