package com.forestotzka.yurufu.slabee.gametest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.test.GameTest;
import net.minecraft.test.GameTestException;
import net.minecraft.test.TestContext;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Slabee の Mixin が全部、差し込み先に正しく適用されることを確かめる。
 *
 * Mixin は差し込み先のクラスが読み込まれたときに初めて適用される。ゲーム中に一度も使われない
 * クラスだと、差し込みに失敗していても気づけない。そこで slabee.mixins.json に書かれた Mixin の
 * 差し込み先を全部強制的に読み込む。mixins.json は defaultRequire: 1 なので、差し込み先の
 * メソッドが見つからなければ、読み込みの時点でエラーになる。
 *
 * 対象はサーバーでも読み込まれる Mixin（slabee.mixins.json）。クライアント専用の Mixin
 * （slabee.client.mixins.json）はサーバーでは読み込まれないため、ここでは確認できない。
 */
public class MixinGameTests implements FabricGameTest {
    private static final String CONFIG = "slabee.mixins.json";
    private static final String MIXIN_ANNOTATION = "Lorg/spongepowered/asm/mixin/Mixin;";

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void allServerMixinTargetsApply(TestContext context) {
        ClassLoader loader = MixinGameTests.class.getClassLoader();
        JsonObject config = readJson(loader, CONFIG);
        String mixinPackage = config.get("package").getAsString();

        List<String> mixinClasses = new ArrayList<>();
        for (String key : new String[]{"mixins", "server"}) {
            JsonArray array = config.getAsJsonArray(key);
            if (array == null) continue;
            for (JsonElement element : array) {
                mixinClasses.add(mixinPackage + "." + element.getAsString());
            }
        }
        context.assertTrue(!mixinClasses.isEmpty(), CONFIG + " に Mixin が1つもない");

        Set<String> targets = new TreeSet<>();
        for (String mixinClass : mixinClasses) {
            targets.addAll(readMixinTargets(loader, mixinClass));
        }
        context.assertTrue(!targets.isEmpty(), "Mixinの差し込み先が1つも見つからない");

        List<String> failures = new ArrayList<>();
        for (String target : targets) {
            try {
                Class.forName(target, false, loader);
            } catch (Throwable t) {
                failures.add(target + " : " + t);
            }
        }

        context.assertTrue(failures.isEmpty(),
                "Mixinの適用に失敗した差し込み先 " + failures.size() + " 件:\n" + String.join("\n", failures));
        context.complete();
    }

    private static JsonObject readJson(ClassLoader loader, String path) {
        try (InputStream in = loader.getResourceAsStream(path)) {
            if (in == null) throw new GameTestException(path + " が見つからない");
            return JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (IOException e) {
            throw new GameTestException(path + " を読めない: " + e);
        }
    }

    /** Mixin クラスを読み込まずに（バイトコードから）@Mixin の差し込み先を取り出す。 */
    private static List<String> readMixinTargets(ClassLoader loader, String mixinClass) {
        String resource = mixinClass.replace('.', '/') + ".class";
        ClassNode node = new ClassNode();
        try (InputStream in = loader.getResourceAsStream(resource)) {
            if (in == null) throw new GameTestException(resource + " が見つからない");
            new ClassReader(in).accept(node, ClassReader.SKIP_CODE);
        } catch (IOException e) {
            throw new GameTestException(resource + " を読めない: " + e);
        }

        List<String> targets = new ArrayList<>();
        List<AnnotationNode> annotations = new ArrayList<>();
        if (node.invisibleAnnotations != null) annotations.addAll(node.invisibleAnnotations);
        if (node.visibleAnnotations != null) annotations.addAll(node.visibleAnnotations);

        for (AnnotationNode annotation : annotations) {
            if (!MIXIN_ANNOTATION.equals(annotation.desc) || annotation.values == null) continue;
            for (int i = 0; i + 1 < annotation.values.size(); i += 2) {
                String name = (String) annotation.values.get(i);
                Object value = annotation.values.get(i + 1);
                if (!(value instanceof List<?> list)) continue;
                for (Object item : list) {
                    if ("value".equals(name) && item instanceof Type type) {
                        targets.add(type.getClassName());
                    } else if ("targets".equals(name) && item instanceof String string) {
                        targets.add(string.replace('/', '.'));
                    }
                }
            }
        }

        if (targets.isEmpty()) {
            throw new GameTestException(mixinClass + " から差し込み先を読み取れない");
        }
        return targets;
    }
}
