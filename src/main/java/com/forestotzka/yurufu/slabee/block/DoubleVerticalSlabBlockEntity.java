package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoubleVerticalSlabBlockEntity extends AbstractDoubleSlabBlockEntity {
    private boolean isX = true;

    public DoubleVerticalSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, pos, state, ResourceLocation.parse("slabee:purple_concrete_vertical_slab"), ResourceLocation.parse("slabee:black_concrete_vertical_slab"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);

        if (this.positiveSlabId != null) {
            CompoundTag positiveSlab = new CompoundTag();
            positiveSlab.putString("id", this.positiveSlabId.toString());
            nbt.put("positive_slab", positiveSlab);
        }

        if (this.negativeSlabId != null) {
            CompoundTag negativeSlab = new CompoundTag();
            negativeSlab.putString("id", this.negativeSlabId.toString());
            nbt.put("negative_slab", negativeSlab);
        }

        if (this.isX) {
            nbt.putString("axis", "X");
        } else {
            nbt.putString("axis", "Z");
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);

        if (nbt.contains("positive_slab")) {
            CompoundTag positiveSlabData = nbt.getCompound("positive_slab");
            ResourceLocation i = ResourceLocation.parse(positiveSlabData.getString("id"));
            this.positiveSlabId = isTrueSlabId(i) ? i : defaultPositiveSlabId;
        } else {
            this.positiveSlabId = defaultPositiveSlabId;
        }

        if (nbt.contains("negative_slab")) {
            CompoundTag negativeSlabData = nbt.getCompound("negative_slab");
            ResourceLocation i = ResourceLocation.parse(negativeSlabData.getString("id"));
            this.negativeSlabId = isTrueSlabId(i) ? i : defaultNegativeSlabId;
        } else {
            this.negativeSlabId = defaultNegativeSlabId;
        }

        if (nbt.contains("axis")) {
            String inputAxis = nbt.getString("axis");
            if ("X".equals(inputAxis)) {
                this.isX = true;
            } else if ("Z".equals(inputAxis)) {
                this.isX = false;
            }
        }

        updateSlabState();
        updateBlockProperties();
    }

    public String getAxis() {
        return this.isX ? "x" : "z";
    }

    public boolean isX() {
        return this.isX;
    }

    public void setAxis(String axis) {
        this.isX = axis.equals("x");
        setChanged();
    }

    protected void updatePositiveSlabState() {
        if (this.isX) {
            this.positiveSlabState = BuiltInRegistries.BLOCK.get(positiveSlabId).defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST);
        } else {
            this.positiveSlabState = BuiltInRegistries.BLOCK.get(positiveSlabId).defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
        }
    }

    protected void updateNegativeSlabState() {
        if (this.isX) {
            this.negativeSlabState = BuiltInRegistries.BLOCK.get(negativeSlabId).defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST);
        } else {
            this.negativeSlabState = BuiltInRegistries.BLOCK.get(negativeSlabId).defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
        }
    }

    @Override
    public VoxelShape getPositiveOutlineShape() {
        if (this.positiveSlabState.is(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
            if (this.isX) {
                return DirtPathVerticalSlabBlock.EAST;
            } else {
                return DirtPathVerticalSlabBlock.SOUTH;
            }
        } else {
            if (this.isX) {
                return VerticalSlabBlock.EAST;
            } else {
                return VerticalSlabBlock.SOUTH;
            }
        }
    }

    @Override
    public VoxelShape getNegativeOutlineShape() {
        if (this.negativeSlabState.is(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
            if (this.isX) {
                return DirtPathVerticalSlabBlock.WEST;
            } else {
                return DirtPathVerticalSlabBlock.NORTH;
            }
        } else {
            if (this.isX) {
                return VerticalSlabBlock.WEST;
            } else {
                return VerticalSlabBlock.NORTH;
            }
        }
    }

    @Override
    protected void convertToDirt(boolean isPositive) {
        if (isPositive) {
            setPositiveSlabId(ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_vertical_slab"));
        } else {
            setNegativeSlabId(ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_vertical_slab"));
        }
    }
}
