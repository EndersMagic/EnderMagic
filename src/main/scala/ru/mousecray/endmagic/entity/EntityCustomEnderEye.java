package ru.mousecray.endmagic.entity;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.mousecray.endmagic.client.render.entity.RenderEntityCustomEnderEye;
import ru.mousecray.endmagic.util.registry.EMEntity;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.HashSet;
import java.util.Set;

@EMEntity(renderClass = RenderEntityCustomEnderEye.class)
public class EntityCustomEnderEye extends EntityEnderEye {

    public static Set<BlockPos> occupiedPoses = new HashSet<>();

    private BlockPos targetPos;
    private Vec3d target;

    public EntityCustomEnderEye(World worldIn, double x, double y, double z, BlockPos targetPos) {
        super(worldIn, x, y, z);
        this.targetPos = targetPos;
        target = new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 0.75, targetPos.getZ() + 0.5);
    }

    public EntityCustomEnderEye(World world) {
        super(world);
    }

    public static boolean isEmptyPortalFrame(IBlockState blockState) {
        return blockState.getBlock() == Blocks.END_PORTAL_FRAME && !blockState.getValue(BlockEndPortalFrame.EYE);
    }

    @Override
    public void onUpdate() {
        if (!world.isRemote && targetPos != null) {
            double dist1 = target.squareDistanceTo(posX, posY, posZ);
            double speedReversedModifier = Math.sqrt(dist1) * 10;

            if (dist1 < 0.01)
                insertEyeToFrame();

            motionX = ((double) targetPos.getX() + 0.5 - posX) / speedReversedModifier;
            motionY = ((double) targetPos.getY() + 0.75 - posY) / speedReversedModifier;
            motionZ = ((double) targetPos.getZ() + 0.5 - posZ) / speedReversedModifier;


            lastTickPosX = posX;
            lastTickPosY = posY;
            lastTickPosZ = posZ;

            onSuperUpdate();

            posX += motionX;
            posY += motionY;
            posZ += motionZ;
        } else
            onSuperUpdate();
    }

    private void insertEyeToFrame() {
        IBlockState currectBlockState = world.getBlockState(targetPos);
        if (isEmptyPortalFrame(currectBlockState)) {
            world.setBlockState(targetPos, currectBlockState.withProperty(BlockEndPortalFrame.EYE, true));
            occupiedPoses.remove(targetPos);

            BlockPattern.PatternHelper portalPattern = BlockEndPortalFrame.getOrCreatePortalShape().match(world, targetPos);

            if (portalPattern != null) {
                BlockPos blockpos = portalPattern.getFrontTopLeft().add(-3, 0, -3);

                WorldGenUtils.generateInArea(blockpos, blockpos.add(2, 0, 2), pos -> world.setBlockState(pos, Blocks.END_PORTAL.getDefaultState(), 2));

                world.playBroadcastSound(1038, blockpos.add(1, 0, 1), 0);
            }
        } else if (rand.nextInt(5) > 0)
            world.spawnEntity(new EntityItem(world, posX, posY, posZ, new ItemStack(Items.ENDER_EYE)));

        setDead();
    }

    private void onSuperUpdate() {
        if (!world.isRemote) {
            setFlag(6, isGlowing());
        }

        onEntityUpdate();
    }
}
