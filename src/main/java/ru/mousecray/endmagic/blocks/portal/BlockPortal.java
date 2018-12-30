package ru.mousecray.endmagic.blocks.portal;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.teleport.Location;
import ru.mousecray.endmagic.teleport.TeleportUtils;

import java.util.List;

public class BlockPortal extends BlockContainer {
    protected BlockPortal() {
        super(Material.PORTAL);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePortal();
    }

    private class TilePortal extends TileEntity implements ITickable {
        Location distination;

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {
            compound.setTag("distination", distination.toNbt());
            return super.writeToNBT(compound);
        }

        @Override
        public void readFromNBT(NBTTagCompound compound) {
            if (compound.hasKey("distination", 10))
                distination = Location.fromNbt(compound.getCompoundTag("distination"));
            else
                distination = new Location(pos, world);

            super.readFromNBT(compound);
        }

        int tick = 0;

        @Override
        public void update() {
            tick++;
            if (tick >= 80)
                world.setBlockToAir(pos);

            if (!world.isRemote) {
                List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPos()));

                list.forEach(it -> TeleportUtils.teleportToLocation(it, distination));
            }

        }
    }
}
