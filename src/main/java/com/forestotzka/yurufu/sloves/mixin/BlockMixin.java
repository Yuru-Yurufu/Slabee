package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.SlovesAccessor;
import com.forestotzka.yurufu.sloves.block.ModBlocks;
import com.forestotzka.yurufu.sloves.block.enums.CustomSlabType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.forestotzka.yurufu.sloves.block.DoubleSlabBlock.TOP_SLAB;
import static com.forestotzka.yurufu.sloves.block.DoubleSlabBlock.BOTTOM_SLAB;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Shadow
    protected abstract void setDefaultState(BlockState state);

    @Shadow
    public abstract BlockState getDefaultState();

    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        //if (world.isClient || world instanceof ServerWorld) {
            if (state.getBlock() instanceof SlabBlock && state.get(Properties.SLAB_TYPE) == SlabType.DOUBLE) {
                SlovesAccessor slovesAccessor = (SlovesAccessor) this;
                boolean bf = slovesAccessor.getBottomFirst(state);
                String first_slab = state.getBlock().toString();
                first_slab = first_slab.substring(first_slab.indexOf('{') + 1, first_slab.indexOf('}'));
                String second_slab = itemStack.getItem().toString();
                CustomSlabType top_slab;
                CustomSlabType bottom_slab;
                if (bf) {
                    top_slab = CustomSlabType.fromString(second_slab);
                    bottom_slab = CustomSlabType.fromString(first_slab);
                } else {
                    top_slab = CustomSlabType.fromString(first_slab);
                    bottom_slab = CustomSlabType.fromString(second_slab);
                }
                world.setBlockState(pos, ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState().with(TOP_SLAB, top_slab).with(BOTTOM_SLAB, bottom_slab), 3);
            }
        //}
    }
}
