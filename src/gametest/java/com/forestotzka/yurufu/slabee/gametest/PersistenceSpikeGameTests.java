package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.slabee.gametest.persistence.ComponentStateCodec;
import com.forestotzka.yurufu.slabee.gametest.persistence.ComponentStateCodec.Catalog;
import com.forestotzka.yurufu.slabee.gametest.persistence.ComponentStateCodec.Decoded;
import com.forestotzka.yurufu.slabee.gametest.persistence.ComponentStateCodec.Family;
import com.forestotzka.yurufu.slabee.gametest.persistence.ComponentStateCodec.Issue;
import com.forestotzka.yurufu.slabee.gametest.persistence.ComponentStateCodec.Outcome;
import com.forestotzka.yurufu.slabee.gametest.persistence.ComponentStateCodec.Source;
import com.forestotzka.yurufu.slabee.gametest.persistence.Diagnostics;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.Properties;
import net.minecraft.test.GameTest;
import net.minecraft.test.GameTestException;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.*;

/**
 * B2 永続化 spike のテスト。保存形式の変換だけを確かめ、ゲームの動きは変えない。
 * 実際の Block Entity とのやりとりは「今の Slabee が新形式を読める」「今の保存データを新しい読み手が読める」の2点だけ。
 */
public class PersistenceSpikeGameTests implements FabricGameTest {
    private static final BlockPos POS = new BlockPos(1, 1, 1);

    /** 葉を「横のハーフ」として扱う、テスト用の対応表。まだ Slabee に property を持つハーフがないので、所有権の確認に使う */
    private static final Catalog WITH_LEAVES = (family, block) ->
            block instanceof LeavesBlock || Catalog.SLABEE.accepts(family, block);

    // ---- 新形式の往復 ----

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void roundTripHorizontal(TestContext context) {
        NbtCompound nbt = new NbtCompound();
        ComponentStateCodec.write(nbt, Family.HORIZONTAL, top(Blocks.OAK_SLAB), bottom(ModBlocks.GLASS_SLAB), null);

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectSource(context, decoded, Source.V1);
        expectOutcome(context, decoded, Outcome.DOUBLE);
        expectNoProblems(context, decoded);
        expectHalf(context, decoded.positive(), top(Blocks.OAK_SLAB));
        expectHalf(context, decoded.negative(), bottom(ModBlocks.GLASS_SLAB));
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void roundTripVerticalBothAxes(TestContext context) {
        for (VerticalSlabAxis axis : VerticalSlabAxis.values()) {
            BlockState positive = vertical(ModBlocks.STONE_VERTICAL_SLAB, axis == VerticalSlabAxis.X ? Direction.EAST : Direction.SOUTH);
            BlockState negative = vertical(ModBlocks.OAK_VERTICAL_SLAB, axis == VerticalSlabAxis.X ? Direction.WEST : Direction.NORTH);
            NbtCompound nbt = new NbtCompound();
            ComponentStateCodec.write(nbt, Family.VERTICAL, positive, negative, axis);

            Decoded decoded = read(context, nbt, Family.VERTICAL, axis);
            expectSource(context, decoded, Source.V1);
            expectNoProblems(context, decoded);
            context.assertTrue(decoded.axis() == axis, "軸が違う: 期待=" + axis + " 実際=" + decoded.axis());
            expectHalf(context, decoded.positive(), positive);
            expectHalf(context, decoded.negative(), negative);
        }
        context.complete();
    }

    /** 軸は容器に1回だけ保存し、ハーフの中には FACING を書かない */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void verticalAxisIsStoredOnceOnContainer(TestContext context) {
        NbtCompound nbt = new NbtCompound();
        ComponentStateCodec.write(nbt, Family.VERTICAL,
                vertical(ModBlocks.STONE_VERTICAL_SLAB, Direction.SOUTH), vertical(ModBlocks.OAK_VERTICAL_SLAB, Direction.NORTH), VerticalSlabAxis.Z);

        context.assertTrue("Z".equals(nbt.getString(ComponentStateCodec.AXIS_KEY)), "容器の axis が Z でない: " + nbt);
        NbtCompound components = nbt.getCompound(ComponentStateCodec.COMPONENTS_KEY);
        for (String side : List.of(ComponentStateCodec.POSITIVE_KEY, ComponentStateCodec.NEGATIVE_KEY)) {
            NbtCompound half = components.getCompound(side);
            context.assertFalse(half.getCompound("Properties").contains("facing"), "ハーフに facing が保存された: " + half);
            context.assertFalse(half.getCompound("Properties").contains("is_double"), "ハーフに is_double が保存された: " + half);
            context.assertFalse(half.getCompound("Properties").contains("waterlogged"), "ハーフに waterlogged が保存された: " + half);
        }
        context.complete();
    }

    // ---- property の所有権 ----

    /** 雪が積もった草のハーフ：SNOWY は周りから決まるので保存せず、読んだ後は default（あとで再計算する） */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void environmentDerivedPropertyIsNotSaved(TestContext context) {
        BlockState snowyGrass = top(ModBlocks.GRASS_SLAB).with(Properties.SNOWY, true);
        NbtCompound nbt = new NbtCompound();
        ComponentStateCodec.write(nbt, Family.HORIZONTAL, snowyGrass, bottom(Blocks.STONE_SLAB), null);

        NbtCompound positive = nbt.getCompound(ComponentStateCodec.COMPONENTS_KEY).getCompound(ComponentStateCodec.POSITIVE_KEY);
        context.assertFalse(positive.contains("Properties"), "保存しない property しかないのに Properties が書かれた: " + positive);

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectHalf(context, decoded.positive(), top(ModBlocks.GRASS_SLAB));
        context.complete();
    }

    /** ハーフ自身の property（葉の PERSISTENT）は保存し、周りから決まる DISTANCE と容器の WATERLOGGED は保存しない */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void componentOwnedPropertyRoundTrips(TestContext context) {
        BlockState leaves = Blocks.OAK_LEAVES.getDefaultState()
                .with(LeavesBlock.PERSISTENT, true)
                .with(LeavesBlock.DISTANCE, 3)
                .with(LeavesBlock.WATERLOGGED, true);
        NbtCompound nbt = new NbtCompound();
        ComponentStateCodec.write(nbt, Family.HORIZONTAL, leaves, bottom(Blocks.STONE_SLAB), null);

        NbtCompound properties = nbt.getCompound(ComponentStateCodec.COMPONENTS_KEY)
                .getCompound(ComponentStateCodec.POSITIVE_KEY).getCompound("Properties");
        context.assertTrue(properties.getKeys().equals(java.util.Set.of("persistent")), "保存された property が違う: " + properties);

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null, WITH_LEAVES, Diagnostics.silent());
        expectNoProblems(context, decoded);
        expectHalf(context, decoded.positive(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true));
        context.complete();
    }

    /** 保存データに書かれていても、TYPE はスロットから決める（下向きと書かれた上側のハーフは上向きになる） */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void slotDerivedPropertyInDataIsIgnored(TestContext context) {
        NbtCompound nbt = parse("""
                {slabee_components: {version: 1,
                    positive: {Name: "minecraft:oak_slab", Properties: {type: "bottom", waterlogged: "true"}},
                    negative: {Name: "minecraft:stone_slab", Properties: {type: "top"}}}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectNoProblems(context, decoded);
        expectHalf(context, decoded.positive(), top(Blocks.OAK_SLAB));
        expectHalf(context, decoded.negative(), bottom(Blocks.STONE_SLAB));
        context.complete();
    }

    /** property だけが壊れていれば、その property だけ default にしてハーフは残す */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void invalidPropertyUsesDefault(TestContext context) {
        NbtCompound nbt = parse("""
                {slabee_components: {version: 1,
                    positive: {Name: "minecraft:oak_leaves", Properties: {persistent: "maybe", no_such_property: "1"}},
                    negative: {Name: "minecraft:birch_leaves", Properties: {persistent: 1b}}}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null, WITH_LEAVES, Diagnostics.silent());
        expectOutcome(context, decoded, Outcome.DOUBLE);
        expectHalf(context, decoded.positive(), Blocks.OAK_LEAVES.getDefaultState());
        expectHalf(context, decoded.negative(), Blocks.BIRCH_LEAVES.getDefaultState());
        expectProblem(context, decoded, Issue.INVALID_PROPERTY_VALUE);
        expectProblem(context, decoded, Issue.UNKNOWN_PROPERTY);
        context.complete();
    }

    // ---- 壊れた・消えた・対応外のブロック ----

    /** 消えたブロック（削除された mod など）の側だけを失い、残った側は単体のハーフになる */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void unknownIdLosesOnlyThatHalf(TestContext context) {
        NbtCompound nbt = parse("""
                {slabee_components: {version: 1,
                    positive: {Name: "removedmod:marble_slab"},
                    negative: {Name: "minecraft:stone_slab"}}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectOutcome(context, decoded, Outcome.SINGLE_NEGATIVE);
        expectProblem(context, decoded, Issue.UNKNOWN_ID);
        expectHalf(context, decoded.singleizedState(), bottom(Blocks.STONE_SLAB));
        context.complete();
    }

    /** 縦で片側を失うと、残った側はそのスロットの向きの単体の縦ハーフになる */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void verticalSingleizationKeepsSlotFacing(TestContext context) {
        NbtCompound nbt = parse("""
                {axis: "X", slabee_components: {version: 1,
                    positive: {Name: "slabee:stone_vertical_slab"},
                    negative: {Name: "removedmod:marble_vertical_slab"}}}""");

        Decoded decoded = read(context, nbt, Family.VERTICAL, VerticalSlabAxis.X);
        expectOutcome(context, decoded, Outcome.SINGLE_POSITIVE);
        expectHalf(context, decoded.singleizedState(), vertical(ModBlocks.STONE_VERTICAL_SLAB, Direction.EAST));
        context.complete();
    }

    /** id として読めない文字列でも例外にせず、その側を失う */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void malformedIdLosesHalf(TestContext context) {
        NbtCompound nbt = parse("""
                {slabee_components: {version: 1,
                    positive: {Name: "Not A Valid Id!"},
                    negative: {Name: "minecraft:stone_slab"}}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectOutcome(context, decoded, Outcome.SINGLE_NEGATIVE);
        expectProblem(context, decoded, Issue.MALFORMED_ID);
        context.complete();
    }

    /** ハーフでないブロックや、形の違うハーフ（横に縦のハーフ）は対応外として失う */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void unsupportedBlockLosesHalf(TestContext context) {
        NbtCompound nbt = parse("""
                {slabee_components: {version: 1,
                    positive: {Name: "minecraft:stone"},
                    negative: {Name: "slabee:stone_vertical_slab"}}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectOutcome(context, decoded, Outcome.AIR);
        expectProblem(context, decoded, Issue.UNSUPPORTED_BLOCK);
        context.complete();
    }

    /** 両側とも復元できなければ air */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void bothHalvesLostBecomesAir(TestContext context) {
        NbtCompound nbt = parse("""
                {slabee_components: {version: 1, positive: "not a compound", negative: {Properties: {}}}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectOutcome(context, decoded, Outcome.AIR);
        expectProblem(context, decoded, Issue.MISSING_COMPONENT);
        expectHalf(context, decoded.singleizedState(), Blocks.AIR.getDefaultState());
        context.complete();
    }

    // ---- 旧形式 ----

    /** 今の Slabee が書く形式（横：id と facing） */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void legacyHorizontalReads(TestContext context) {
        NbtCompound nbt = parse("""
                {positive_slab: {id: "minecraft:oak_slab", facing: "south"},
                 negative_slab: {id: "slabee:glass_slab", facing: "south"}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectSource(context, decoded, Source.LEGACY);
        expectNoProblems(context, decoded);
        expectHalf(context, decoded.positive(), top(Blocks.OAK_SLAB));
        expectHalf(context, decoded.negative(), bottom(ModBlocks.GLASS_SLAB));
        context.complete();
    }

    /** 旧形式の縦：NBT の axis を移行の入力にする（外側 BlockState と違っても NBT を使う） */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void legacyVerticalUsesNbtAxis(TestContext context) {
        NbtCompound nbt = parse("""
                {axis: "Z", positive_slab: {id: "slabee:oak_vertical_slab"}, negative_slab: {id: "slabee:stone_vertical_slab"}}""");

        Decoded decoded = read(context, nbt, Family.VERTICAL, VerticalSlabAxis.X);
        expectSource(context, decoded, Source.LEGACY);
        expectNoProblems(context, decoded);
        context.assertTrue(decoded.axis() == VerticalSlabAxis.Z, "旧形式の axis が使われていない: " + decoded.axis());
        expectHalf(context, decoded.positive(), vertical(ModBlocks.OAK_VERTICAL_SLAB, Direction.SOUTH));
        expectHalf(context, decoded.negative(), vertical(ModBlocks.STONE_VERTICAL_SLAB, Direction.NORTH));
        context.complete();
    }

    /** 旧形式の axis が壊れていれば外側 BlockState、それもなければ X */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void legacyInvalidAxisFallsBack(TestContext context) {
        NbtCompound nbt = parse("""
                {axis: "Y", positive_slab: {id: "slabee:oak_vertical_slab"}, negative_slab: {id: "slabee:stone_vertical_slab"}}""");

        Decoded withState = read(context, nbt, Family.VERTICAL, VerticalSlabAxis.Z);
        context.assertTrue(withState.axis() == VerticalSlabAxis.Z, "BlockState の軸が使われていない: " + withState.axis());
        expectProblem(context, withState, Issue.INVALID_AXIS);

        Decoded withoutState = read(context, nbt, Family.VERTICAL, null);
        context.assertTrue(withoutState.axis() == VerticalSlabAxis.X, "既定の X になっていない: " + withoutState.axis());
        context.complete();
    }

    /** 旧形式でも、消えた id は今のように既定のハーフに置き換えず、その側を失う */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void legacyUnknownIdLosesHalf(TestContext context) {
        NbtCompound nbt = parse("""
                {positive_slab: {id: "removedmod:marble_slab"}, negative_slab: {id: "minecraft:stone_slab"}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectOutcome(context, decoded, Outcome.SINGLE_NEGATIVE);
        expectProblem(context, decoded, Issue.UNKNOWN_ID);
        context.complete();
    }

    /** 新形式で軸が食い違えば、外側 BlockState を正として診断する */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void v1AxisConflictKeepsBlockState(TestContext context) {
        NbtCompound nbt = new NbtCompound();
        ComponentStateCodec.write(nbt, Family.VERTICAL,
                vertical(ModBlocks.STONE_VERTICAL_SLAB, Direction.EAST), vertical(ModBlocks.OAK_VERTICAL_SLAB, Direction.WEST), VerticalSlabAxis.X);

        Decoded decoded = read(context, nbt, Family.VERTICAL, VerticalSlabAxis.Z);
        context.assertTrue(decoded.axis() == VerticalSlabAxis.Z, "BlockState の軸が使われていない: " + decoded.axis());
        expectProblem(context, decoded, Issue.AXIS_CONFLICT);
        expectHalf(context, decoded.positive(), vertical(ModBlocks.STONE_VERTICAL_SLAB, Direction.SOUTH));
        context.complete();
    }

    /** 新しい版で保存されたデータは、一緒に書かれている旧形式で読む */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void futureSchemaFallsBackToLegacy(TestContext context) {
        NbtCompound nbt = parse("""
                {positive_slab: {id: "minecraft:oak_slab"}, negative_slab: {id: "minecraft:stone_slab"},
                 slabee_components: {version: 2, positive: {Name: "minecraft:oak_slab", NewField: 1}}}""");

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectSource(context, decoded, Source.LEGACY);
        expectOutcome(context, decoded, Outcome.DOUBLE);
        expectProblem(context, decoded, Issue.FUTURE_SCHEMA);
        context.complete();
    }

    // ---- 今の Block Entity とのやりとり ----

    /** 今の Slabee が保存したデータを、新しい読み手で読める */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void readsCurrentHorizontalBlockEntityNbt(TestContext context) {
        placeDouble(context, POS, Blocks.OAK_SLAB, ModBlocks.GLASS_SLAB);
        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);
        NbtCompound nbt = entity.createNbt(context.getWorld().getRegistryManager());

        Decoded decoded = read(context, nbt, Family.HORIZONTAL, null);
        expectSource(context, decoded, Source.LEGACY);
        expectNoProblems(context, decoded);
        expectHalf(context, decoded.positive(), top(Blocks.OAK_SLAB));
        expectHalf(context, decoded.negative(), bottom(ModBlocks.GLASS_SLAB));
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void readsCurrentVerticalBlockEntityNbt(TestContext context) {
        context.setBlockState(POS, ModBlocks.STONE_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, Direction.NORTH));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.SOUTH, new Vec3d(0.5, 0.5, 0.5));
        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);
        NbtCompound nbt = entity.createNbt(context.getWorld().getRegistryManager());
        VerticalSlabAxis stateAxis = context.getBlockState(POS).get(DoubleVerticalSlabBlock.AXIS);

        Decoded decoded = read(context, nbt, Family.VERTICAL, stateAxis);
        expectNoProblems(context, decoded);
        context.assertTrue(decoded.axis() == VerticalSlabAxis.Z, "Z軸のはず: " + decoded.axis());
        expectHalf(context, decoded.positive(), vertical(ModBlocks.OAK_VERTICAL_SLAB, Direction.SOUTH));
        expectHalf(context, decoded.negative(), vertical(ModBlocks.STONE_VERTICAL_SLAB, Direction.NORTH));
        context.complete();
    }

    /** 新形式は追加型：今の Slabee の読み手（Block Entity）でも、旧形式の部分から正しく読める */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void currentBlockEntityReadsNewFormat(TestContext context) {
        placeDouble(context, POS, Blocks.STONE_SLAB, Blocks.STONE_SLAB);
        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        NbtCompound nbt = new NbtCompound();
        ComponentStateCodec.write(nbt, Family.HORIZONTAL, top(ModBlocks.GLASS_SLAB), bottom(Blocks.OAK_SLAB), null);
        entity.read(nbt, context.getWorld().getRegistryManager());

        expectHalves(context, entity, ModBlocks.GLASS_SLAB, Blocks.OAK_SLAB);
        context.complete();
    }

    // ---- 診断ログ ----

    /** 一定時間の件数に上限があり、超えた分は件数だけまとめて出る */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void diagnosticsAreRateLimited(TestContext context) {
        AtomicLong clock = new AtomicLong(0);
        List<String> lines = new ArrayList<>();
        Diagnostics diagnostics = new Diagnostics(clock::get, 3, 1000, lines::add);
        NbtCompound broken = parse("""
                {positive_slab: {id: "removedmod:a_slab"}, negative_slab: {id: "removedmod:b_slab"}}""");

        for (int i = 0; i < 10; i++) {
            read(context, broken, Family.HORIZONTAL, null, Catalog.SLABEE, diagnostics);
        }
        context.assertTrue(lines.size() == 3, "上限の3件だけ出るはず: " + lines);

        clock.set(1000);
        read(context, broken, Family.HORIZONTAL, null, Catalog.SLABEE, diagnostics);
        context.assertTrue(lines.size() == 6, "区間が変わったら、まとめ1件と新しい2件が出るはず: " + lines);
        context.assertTrue(lines.get(3).contains("17 more problems suppressed"), "まとめの件数が違う: " + lines.get(3));
        context.complete();
    }

    // ---- 補助 ----

    private static Decoded read(TestContext context, NbtCompound nbt, Family family, VerticalSlabAxis blockStateAxis) {
        return read(context, nbt, family, blockStateAxis, Catalog.SLABEE, Diagnostics.silent());
    }

    private static Decoded read(TestContext context, NbtCompound nbt, Family family, VerticalSlabAxis blockStateAxis,
                                Catalog catalog, Diagnostics diagnostics) {
        RegistryEntryLookup<Block> blocks = context.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.BLOCK);
        return ComponentStateCodec.read(nbt, family, blocks, catalog, blockStateAxis, diagnostics);
    }

    private static NbtCompound parse(String snbt) {
        try {
            return StringNbtReader.parse(snbt);
        } catch (CommandSyntaxException e) {
            throw new GameTestException("fixture の SNBT が読めない: " + e.getMessage());
        }
    }

    private static BlockState top(Block slab) {
        return slab.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
    }

    private static BlockState bottom(Block slab) {
        return slab.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);
    }

    private static BlockState vertical(Block slab, Direction facing) {
        return slab.getDefaultState().with(VerticalSlabBlock.FACING, facing);
    }

    private static void expectHalf(TestContext context, Optional<BlockState> actual, BlockState expected) {
        context.assertTrue(actual.isPresent() && actual.get().equals(expected),
                "ハーフの状態が違う: 期待=" + expected + " 実際=" + actual.map(Object::toString).orElse("(なし)"));
    }

    private static void expectOutcome(TestContext context, Decoded decoded, Outcome expected) {
        context.assertTrue(decoded.outcome() == expected, "結果が違う: 期待=" + expected + " 実際=" + decoded.outcome() + " " + decoded.problems());
    }

    private static void expectSource(TestContext context, Decoded decoded, Source expected) {
        context.assertTrue(decoded.source() == expected, "読んだ形式が違う: 期待=" + expected + " 実際=" + decoded.source());
    }

    private static void expectNoProblems(TestContext context, Decoded decoded) {
        context.assertTrue(decoded.problems().isEmpty(), "問題が出るはずがない: " + decoded.problems());
    }

    private static void expectProblem(TestContext context, Decoded decoded, Issue issue) {
        context.assertTrue(decoded.has(issue), issue + " が出るはず: " + decoded.problems());
    }
}
