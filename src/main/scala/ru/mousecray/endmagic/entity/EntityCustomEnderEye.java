package ru.mousecray.endmagic.entity;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.mousecray.endmagic.client.render.entity.RenderEntityCustomEnderEye;
import ru.mousecray.endmagic.util.BaseDataSerializer;
import ru.mousecray.endmagic.util.registry.EMEntity;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@EMEntity(renderClass = RenderEntityCustomEnderEye.class)
public class EntityCustomEnderEye extends EntityEnderEye {

    public static Set<BlockPos> occupiedPoses = new HashSet<>();
    private List<Vec3d> path;
    private Function<Double, Vec3d> curve;

    private final static DataParameter<Double> T = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("T", PacketBuffer::writeDouble, PacketBuffer::readDouble));
    private final static DataParameter<BlockPos> TARGET_POS = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("TARGET_POS", PacketBuffer::writeBlockPos, PacketBuffer::readBlockPos));
    private final static DataParameter<Vec3d> START_POS = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("START_POS", (buf, value) -> {
        buf.writeDouble(value.x);
        buf.writeDouble(value.y);
        buf.writeDouble(value.z);
    }, buf -> new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble())));

    public EntityCustomEnderEye(World worldIn, double x, double y, double z, BlockPos targetPos) {
        super(worldIn, x, y, z);
        path = makePath(worldIn, new Vec3d(x, y, z), targetPos);
        curve = bezierCurve(path);

        System.out.println("test1");
        getDataManager().register(T, 0d);
        getDataManager().register(TARGET_POS, targetPos);
        getDataManager().register(START_POS, new Vec3d(x, y, z));
        System.out.println("test2");
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (key == START_POS) {
            System.out.println("test");
            path = makePath(world, startPos(), targetPos());
            curve = bezierCurve(path);
        }
    }

    private Function<Double, Vec3d> bezierCurve(List<Vec3d> path) {
        return null;
    }

    private List<Vec3d> makePath(World worldIn, Vec3d startPos, BlockPos targetPos) {
        double x = startPos.x;
        double y = startPos.y;
        double z = startPos.z;

        List<Vec3d> r = new ArrayList<>();
        r.add(new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 0.75, targetPos.getZ() + 0.5));
        Vec3d aboveFrame = new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 3, targetPos.getZ() + 0.5);
        r.add(aboveFrame);
        double height = aboveFrame.y - y;
        if (height > 1) {
            Vec3d direction = aboveFrame.subtract(x, y, z).normalize();
            r.add(new Vec3d(
                    x + height * direction.x,
                    y + height,
                    z + height * direction.z
            ));
        }
        r.add(startPos);
        return r;
    }

    public EntityCustomEnderEye(World world) {
        super(world);
        getDataManager().register(T, 0d);
        getDataManager().register(TARGET_POS, BlockPos.ORIGIN);
        getDataManager().register(START_POS, Vec3d.ZERO);
    }

    public static boolean isEmptyPortalFrame(IBlockState blockState) {
        return blockState.getBlock() == Blocks.END_PORTAL_FRAME && !blockState.getValue(BlockEndPortalFrame.EYE);
    }

    @Override
    public void onUpdate() {
        if (!world.isRemote && !targetPos().equals(BlockPos.ORIGIN)) {

            //System.out.println(targetPos());

            /*
            Vec3d currentTarget = target.peek();

            double dist = currentTarget.distanceTo(getPositionVector());
            double speedModifier = 0.1;

            if (dist < 0.1) {
                if (target.size() == 1)
                    insertEyeToFrame();
                else
                    target.pop();
            }

            double targetMotionX = (currentTarget.x - posX) / dist * speedModifier;
            double targetMotionY = (currentTarget.y - posY) / dist * speedModifier;
            double targetMotionZ = (currentTarget.z - posZ) / dist * speedModifier;

            Vec3d a = new Vec3d(targetMotionX - motionX, targetMotionY - motionY, targetMotionZ - motionZ).scale(0.1);

            motionX += a.x;
            motionY += a.y;
            motionZ += a.z;

            lastTickPosX = posX;
            lastTickPosY = posY;
            lastTickPosZ = posZ;
            */
            onSuperUpdate();
        } else
            onSuperUpdate();
    }

    public BlockPos targetPos() {
        return getDataManager().get(TARGET_POS);
    }

    public Vec3d startPos() {
        return getDataManager().get(START_POS);
    }

    public double t() {
        return getDataManager().get(T);
    }

    private void insertEyeToFrame() {
        BlockPos targetPos = targetPos();
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

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
    }
}
