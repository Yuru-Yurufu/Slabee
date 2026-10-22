package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import com.mojang.authlib.GameProfile;
import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.server.network.ConnectedClientData;
import java.util.UUID;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.*;

/** Review-only regressions: assertions describe the expected behavior, not the known bugs. */
public class ReviewGameTests implements FabricGameTest {
    private static final BlockPos POS = new BlockPos(1, 1, 1);

    private static ServerPlayerEntity survival(TestContext c, ItemStack stack) {
        ConnectedClientData data = ConnectedClientData.createDefault(new GameProfile(UUID.randomUUID(), "review-player"), false);
        ServerPlayerEntity p = new ServerPlayerEntity(c.getWorld().getServer(), c.getWorld(), data.gameProfile(), data.syncedOptions());
        ClientConnection connection = new ClientConnection(NetworkSide.SERVERBOUND);
        new EmbeddedChannel(connection);
        c.getWorld().getServer().getPlayerManager().onPlayerConnect(connection, p, data);
        p.changeGameMode(GameMode.SURVIVAL);
        c.assertTrue(!p.isCreative() && p.interactionManager.isSurvivalLike(), "Survival player precondition");
        p.setStackInHand(Hand.MAIN_HAND, stack);
        BlockPos a = c.getAbsolutePos(POS);
        p.refreshPositionAndAngles(a.getX() + 0.5, a.getY() + 2.0, a.getZ() + 0.5, 0, 90);
        return p;
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void earlyStopMustNotInstantlyBreakObsidian(TestContext c) {
        ServerPlayerEntity p = survival(c, ItemStack.EMPTY);
        BlockPos a = c.getAbsolutePos(POS);
        c.setBlockState(POS, Blocks.OBSIDIAN);
        p.interactionManager.processBlockBreakingAction(a, PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, Direction.UP, c.getWorld().getTopY(), 1);
        p.interactionManager.processBlockBreakingAction(a, PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, Direction.UP, c.getWorld().getTopY(), 2);
        c.expectBlock(Blocks.OBSIDIAN, POS);
        placeDouble(c, POS, ModBlocks.OBSIDIAN_SLAB, ModBlocks.OBSIDIAN_SLAB);
        p.interactionManager.processBlockBreakingAction(a, PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, Direction.UP, c.getWorld().getTopY(), 3);
        p.interactionManager.processBlockBreakingAction(a, PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, Direction.UP, c.getWorld().getTopY(), 4);
        c.expectBlock(ModBlocks.DOUBLE_SLAB_BLOCK, POS);
        c.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void upperBreakMustUseServerPlayerTarget(TestContext c) {
        placeDouble(c, POS, Blocks.OAK_SLAB, Blocks.STONE_SLAB);
        ServerPlayerEntity p = survival(c, ItemStack.EMPTY);
        p.setSneaking(true);
        LookingPositionTracker.lookingAtUpperHalf = false;
        p.interactionManager.tryBreakBlock(c.getAbsolutePos(POS));
        c.expectBlock(Blocks.STONE_SLAB, POS);
        c.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void handMustNotHarvestObsidian(TestContext c) {
        c.getWorld().getGameRules().get(GameRules.DO_TILE_DROPS).set(true, c.getWorld().getServer());
        placeDouble(c, POS, ModBlocks.OBSIDIAN_SLAB, ModBlocks.OBSIDIAN_SLAB);
        ServerPlayerEntity p = survival(c, ItemStack.EMPTY);
        p.interactionManager.tryBreakBlock(c.getAbsolutePos(POS));
        c.runAtTick(2, () -> {
            int count = c.getWorld().getEntitiesByClass(ItemEntity.class, new Box(c.getAbsolutePos(POS)).expand(2),
                    e -> e.getStack().isOf(ModBlocks.OBSIDIAN_SLAB.asItem())).stream().mapToInt(e -> e.getStack().getCount()).sum();
            c.assertTrue(count == 0, "Wrong-tool obsidian drops: " + count);
            c.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void miningMustDamageTool(TestContext c) {
        placeDouble(c, POS, Blocks.STONE_SLAB, Blocks.STONE_SLAB);
        ItemStack tool = new ItemStack(Items.DIAMOND_PICKAXE);
        ServerPlayerEntity p = survival(c, tool);
        p.interactionManager.tryBreakBlock(c.getAbsolutePos(POS));
        c.expectBlock(Blocks.AIR, POS);
        c.assertTrue(tool.getDamage() > 0, "Pickaxe damage after breaking: " + tool.getDamage());
        c.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void nonPlayerBreakMustDropHalves(TestContext c) {
        c.getWorld().getGameRules().get(GameRules.DO_TILE_DROPS).set(true, c.getWorld().getServer());
        placeDouble(c, POS, Blocks.OAK_SLAB, Blocks.OAK_SLAB);
        BlockPos a = c.getAbsolutePos(POS);
        c.getWorld().breakBlock(a, true);
        c.runAtTick(2, () -> {
            int count = c.getWorld().getEntitiesByClass(ItemEntity.class, new Box(a).expand(2),
                    e -> e.getStack().isOf(Blocks.OAK_SLAB.asItem())).stream().mapToInt(e -> e.getStack().getCount()).sum();
            c.assertTrue(count == 2, "Non-player break drops: " + count);
            c.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void obsidianPairMustKeepBlastResistance(TestContext c) {
        placeDouble(c, POS, ModBlocks.OBSIDIAN_SLAB, ModBlocks.OBSIDIAN_SLAB);
        float actual = c.getBlockState(POS).getBlock().getBlastResistance();
        float expected = ModBlocks.OBSIDIAN_SLAB.getBlastResistance();
        c.assertTrue(actual == expected, "Blast resistance: " + actual + "; single half: " + expected);
        c.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void vanillaDropControl(TestContext c) {
        c.getWorld().getGameRules().get(GameRules.DO_TILE_DROPS).set(true, c.getWorld().getServer());
        c.setBlockState(POS, Blocks.OAK_SLAB);
        BlockPos a = c.getAbsolutePos(POS);
        c.getWorld().breakBlock(a, true);
        c.runAtTick(2, () -> {
            int count = c.getWorld().getEntitiesByClass(ItemEntity.class, new Box(a).expand(2),
                    e -> e.getStack().isOf(Blocks.OAK_SLAB.asItem())).stream().mapToInt(e -> e.getStack().getCount()).sum();
            c.assertTrue(count == 1, "Vanilla drop control: " + count);
            c.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void coveredIceMustNotMakeStoneSlippery(TestContext c) {
        placeDouble(c, POS, Blocks.STONE_SLAB, ModBlocks.BLUE_ICE_SLAB);
        BlockPos a = c.getAbsolutePos(POS);
        float actual = SlabeeUtils.getSlipperiness(c.getWorld(), Vec3d.ofCenter(a).add(0, 0.5, 0), a, Blocks.STONE_SLAB.getSlipperiness());
        c.assertTrue(actual == Blocks.STONE_SLAB.getSlipperiness(), "Stone over ice slipperiness: " + actual);
        c.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void glowstoneFaceMustRemainVisibleThroughLeaves(TestContext c) {
        c.setBlockState(POS, Blocks.GLOWSTONE);
        c.setBlockState(POS.up(), Blocks.OAK_LEAVES);
        BlockPos a = c.getAbsolutePos(POS);
        c.assertTrue(Block.shouldDrawSide(Blocks.GLOWSTONE.getDefaultState(), c.getWorld(), a, Direction.UP, a.up()),
                "Control: full glowstone face under leaves must be visible");
        c.setBlockState(POS.up(), Blocks.AIR);
        placeDouble(c, POS, ModBlocks.OAK_LEAF_SLAB, ModBlocks.GLOWSTONE_SLAB);
        BlockState half = expectDouble(c, POS, ModBlocks.DOUBLE_SLAB_BLOCK).getNegativeSlabState();
        c.assertTrue(Block.shouldDrawSide(half, c.getWorld(), a, Direction.UP, a.up()),
                "Inner glowstone face was culled under leaf half");
        c.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void shovelUnderVerticalDirtMustNotCrash(TestContext c) {
        c.setBlockState(POS, Blocks.DIRT);
        c.setBlockState(POS.up(), ModBlocks.DIRT_VERTICAL_SLAB);
        ServerPlayerEntity p = survival(c, new ItemStack(Items.IRON_SHOVEL));
        BlockPos a = c.getAbsolutePos(POS);
        p.getMainHandStack().useOnBlock(new ItemUsageContext(p, Hand.MAIN_HAND,
                new BlockHitResult(Vec3d.ofCenter(a), Direction.UP, a, false)));
        c.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void placementQueryMustNotMutateWorld(TestContext c) {
        BlockState before = Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);
        c.setBlockState(POS, before);
        ServerPlayerEntity p = survival(c, new ItemStack(Blocks.OAK_SLAB));
        BlockPos a = c.getAbsolutePos(POS);
        Blocks.OAK_SLAB.getPlacementState(new ItemPlacementContext(p, Hand.MAIN_HAND, p.getMainHandStack(),
                new BlockHitResult(Vec3d.of(a).add(0.5, 0.5, 0.5), Direction.UP, a, false)));
        expectState(c, POS, before);
        c.complete();
    }
}
