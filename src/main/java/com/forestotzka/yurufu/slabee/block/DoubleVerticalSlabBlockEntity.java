package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class DoubleVerticalSlabBlockEntity extends AbstractDoubleSlabBlockEntity {
    private boolean isX = true;

    public DoubleVerticalSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, pos, state, Identifier.of("slabee:purple_concrete_vertical_slab"), Identifier.of("slabee:black_concrete_vertical_slab"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        if (this.positiveSlabId != null) {
            NbtCompound positiveSlab = new NbtCompound();
            positiveSlab.putString("id", this.positiveSlabId.toString());
            nbt.put("positive_slab", positiveSlab);
        }

        if (this.negativeSlabId != null) {
            NbtCompound negativeSlab = new NbtCompound();
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
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains("positive_slab")) {
            NbtCompound positiveSlabData = nbt.getCompound("positive_slab");
            Identifier i = Identifier.of(positiveSlabData.getString("id"));
            this.positiveSlabId = isTrueSlabId(i) ? i : defaultPositiveSlabId;
        } else {
            this.positiveSlabId = defaultPositiveSlabId;
        }

        if (nbt.contains("negative_slab")) {
            NbtCompound negativeSlabData = nbt.getCompound("negative_slab");
            Identifier i = Identifier.of(negativeSlabData.getString("id"));
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
        markDirty();
    }

    protected void updatePositiveSlabState() {
        if (this.isX) {
            this.positiveSlabState = Registries.BLOCK.get(positiveSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST);
        } else {
            this.positiveSlabState = Registries.BLOCK.get(positiveSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH);
        }
    }

    protected void updateNegativeSlabState() {
        if (this.isX) {
            this.negativeSlabState = Registries.BLOCK.get(negativeSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST);
        } else {
            this.negativeSlabState = Registries.BLOCK.get(negativeSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH);
        }
    }

    @Override
    public VoxelShape getPositiveOutlineShape() {
        if (this.positiveSlabState.isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
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
        if (this.negativeSlabState.isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
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
            setPositiveSlabId(Identifier.of(Slabee.MOD_ID, "dirt_vertical_slab"));
        } else {
            setNegativeSlabId(Identifier.of(Slabee.MOD_ID, "dirt_vertical_slab"));
        }
    }
}
