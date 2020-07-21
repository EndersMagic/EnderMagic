package ru.mousecray.endmagic.entity;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
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

import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EMEntity(renderClass = RenderEntityCustomEnderEye.class)
public class EntityCustomEnderEye extends EntityEnderEye {
    ///kill @e[type=endmagic:custom_ender_eye]

    public static Set<BlockPos> occupiedPoses = new HashSet<>();
    public List<Vec3d> path;
    public Function<Double, Vec3d> curve;
    public int curveLen;
    public List<Vec3d> curveCache;

    public int clientOrientedT = 0;

    private final static DataParameter<Integer> T = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("T", PacketBuffer::writeInt, PacketBuffer::readInt));
    private final static DataParameter<BlockPos> TARGET_POS = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("TARGET_POS", PacketBuffer::writeBlockPos, PacketBuffer::readBlockPos));
    private final static DataParameter<Vec3d> START_POS = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("START_POS", (buf, value) -> {
        buf.writeDouble(value.x);
        buf.writeDouble(value.y);
        buf.writeDouble(value.z);
    }, buf -> new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble())));

    public EntityCustomEnderEye(World worldIn, double x, double y, double z, BlockPos targetPos) {
        super(worldIn, x, y, z);

        getDataManager().register(T, 0);
        getDataManager().register(TARGET_POS, targetPos);
        getDataManager().register(START_POS, new Vec3d(x, y, z));

        notifyDataManagerChange(START_POS);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (key == START_POS) {
            path = makePath(world, startPos(), targetPos());
            curve = bezierCurve(path);
            curveLen = calcCurveLen(curve);

            curveCache = IntStream.range(0, curveLen).mapToDouble(i -> 1 - ((double) i) / curveLen).mapToObj(curve::apply).collect(Collectors.toList());
        } else if (key == T)
            clientOrientedT = Math.max(clientOrientedT, t());
    }

    private int calcCurveLen(Function<Double, Vec3d> curve) {
        return 100;
    }

    private Function<Double, Vec3d> bezierCurve(List<Vec3d> path) {
        //Можно развернуть точки так, чтобы они лежали в плоскости ZoY, заюзать двумерноые безье, и повернуть обратно
        Vec3d first = path.get(0);
        Vec3d last = path.get(path.size() - 1);
        float angle = (float) Math.atan2(last.x - first.x, last.z - first.z);

        List<Vec3d> rotatedPath = path.stream().map(v -> v.rotateYaw(-angle)).collect(Collectors.toList());

        return t -> {
            double z = 0;
            double y = 0;

            int n = rotatedPath.size() - 1;

            Vec3d vec = rotatedPath.get(0);
            double pow_1_minus_t_n = Math.pow((1 - t), n);
            z += vec.z * pow_1_minus_t_n;
            y += vec.y * pow_1_minus_t_n;

            double factorial_n = factorial(n);

            for (int index = 1; index < rotatedPath.size(); index++) {
                Vec3d item = rotatedPath.get(index);
                z += factorial_n / factorial(index) / factorial(n - index) * item.z * Math.pow((1 - t), n - index) * Math.pow(t, index);
                y += factorial_n / factorial(index) / factorial(n - index) * item.y * Math.pow((1 - t), n - index) * Math.pow(t, index);
            }
            return new Vec3d(vec.x, y, z).rotateYaw(angle);
        };
    }

    private Int2IntMap factorialCache = new Int2IntOpenHashMap();

    private double factorial(int num) {
        return factorialCache.computeIfAbsent(num, num1 -> factorial(num1, 1));
    }

    private int factorial(int num, int acc) {
        if (num <= 1) {
            return acc;
        } else {
            return factorial(num - 1, acc * num);
        }
    }

    private List<Vec3d> makePath(World worldIn, Vec3d startPos, BlockPos targetPos) {

        List<Vec3d> r = new ArrayList<>();
        r.add(new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 0.75, targetPos.getZ() + 0.5));
        Vec3d aboveFrame = new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 3, targetPos.getZ() + 0.5);
        r.add(aboveFrame);
        double height = aboveFrame.y - startPos.y;
        if (height > 1) {
            Vector2d direction = new Vector2d(aboveFrame.x, aboveFrame.z);
            direction.sub(new Vector2d(startPos.x, startPos.z));
            direction.normalize();
            r.add(new Vec3d(
                    startPos.x + height * direction.x,
                    startPos.y + height,
                    startPos.z + height * direction.y
            ));
        }
        r.add(startPos);
        return r;
    }

    public EntityCustomEnderEye(World world) {
        super(world);
        getDataManager().register(T, 0);
        getDataManager().register(TARGET_POS, BlockPos.ORIGIN);
        getDataManager().register(START_POS, Vec3d.ZERO);
    }

    public static boolean isEmptyPortalFrame(IBlockState blockState) {
        return blockState.getBlock() == Blocks.END_PORTAL_FRAME && !blockState.getValue(BlockEndPortalFrame.EYE);
    }

    @Override
    public void onUpdate() {
        if (!world.isRemote) {
            if (curve != null) {
                int t = t();
                if (t >= curveLen)
                    insertEyeToFrame();
                else {
                    Vec3d vec = curveCache.get(t);

                    setPosition(vec.x, vec.y, vec.z);

                    setClientOrientedT(t + 1);
                }

                onSuperUpdate();
            } else {
                onSuperUpdate();

                posX += motionX;
                posY += motionY;
                posZ += motionZ;
            }
        } else
            clientOrientedT++;
    }

    private BlockPos targetPos() {
        return getDataManager().get(TARGET_POS);
    }

    private Vec3d startPos() {
        return getDataManager().get(START_POS);
    }

    public int t() {
        return getDataManager().get(T);
    }

    private void setClientOrientedT(int clientOrientedT) {
        getDataManager().set(T, clientOrientedT);
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

    @Override
    public void setDead() {
        occupiedPoses.remove(targetPos());
        super.setDead();
    }

    private void onSuperUpdate() {
        if (!world.isRemote)
            setFlag(6, isGlowing());

        onEntityUpdate();
    }
}
