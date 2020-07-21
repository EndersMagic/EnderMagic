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
import net.minecraft.util.EnumParticleTypes;
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

import static ru.mousecray.endmagic.util.MathUtils.bezierCurve;

@EMEntity(renderClass = RenderEntityCustomEnderEye.class)
public class EntityCustomEnderEye extends EntityEnderEye {
    ///kill @e[type=endmagic:custom_ender_eye]

    public static Set<BlockPos> occupiedPoses = new HashSet<>();
    public List<Vec3d> path;
    public Function<Double, Vec3d> curve;
    public int curveLen;

    public int clientOrientedTick = 0;

    private final static DataParameter<Integer> TICK = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("TICK", PacketBuffer::writeInt, PacketBuffer::readInt));
    private final static DataParameter<BlockPos> TARGET_POS = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("TARGET_POS", PacketBuffer::writeBlockPos, PacketBuffer::readBlockPos));
    private final static DataParameter<Vec3d> START_POS = EntityDataManager.createKey(EntityCustomEnderEye.class, new BaseDataSerializer<>("START_POS", (buf, value) -> {
        buf.writeDouble(value.x);
        buf.writeDouble(value.y);
        buf.writeDouble(value.z);
    }, buf -> new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble())));

    public EntityCustomEnderEye(World worldIn, double x, double y, double z, BlockPos targetPos) {
        super(worldIn, x, y, z);

        getDataManager().register(TICK, 0);
        getDataManager().register(TARGET_POS, targetPos);
        getDataManager().register(START_POS, new Vec3d(x, y, z));

        notifyDataManagerChange(START_POS);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (key == START_POS) {
            path = makePath(world, startPos(), targetPos());
            curve = bezierCurve(path);
            curveLen = calcCurveLen(path);
        } else if (key == TICK)
            clientOrientedTick = Math.max(clientOrientedTick, tick());
    }

    private List<Vec3d> makePath(World worldIn, Vec3d startPos, BlockPos targetPos) {
        Vec3d finalPos = new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 0.75, targetPos.getZ() + 0.5);

        List<Vec3d> r = new ArrayList<>();
        r.add(startPos);

        Vec3d aboveFrame = new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 3, targetPos.getZ() + 0.5);
        double height = aboveFrame.y - startPos.y;
        if (height > 1 && startPos.squareDistanceTo(finalPos) > 25) {
            Vector2d direction = new Vector2d(aboveFrame.x, aboveFrame.z);
            direction.sub(new Vector2d(startPos.x, startPos.z));
            direction.normalize();
            r.add(new Vec3d(
                    startPos.x + height * direction.x,
                    startPos.y + height,
                    startPos.z + height * direction.y
            ));
        }
        r.add(aboveFrame);

        r.add(finalPos);
        return r;
    }

    private int calcCurveLen(List<Vec3d> path) {
        Vec3d startPos = path.get(0);
        Vec3d finalPos = path.get(path.size() - 1);
        return (int) (startPos.distanceTo(finalPos) * 7);
    }

    public EntityCustomEnderEye(World world) {
        super(world);
        getDataManager().register(TICK, 0);
        getDataManager().register(TARGET_POS, BlockPos.ORIGIN);
        getDataManager().register(START_POS, Vec3d.ZERO);
    }

    @Override
    public void onUpdate() {
        if (!world.isRemote) {
            if (curve != null) {
                int t = tick();
                if (t >= curveLen)
                    insertEyeToFrame();
                else {
                    Vec3d vec = curve.apply((double) t / curveLen);

                    setPosition(vec.x, vec.y, vec.z);

                    setTick(t + 1);
                }

                onSuperUpdate();
            }
        } else {
            clientOrientedTick++;
            world.spawnParticle(EnumParticleTypes.PORTAL,
                    posX + rand.nextDouble() * 0.1 - 0.3, posY - 0.5, posZ + rand.nextDouble() * 0.1 - 0.3,
                    rand.nextDouble(), rand.nextDouble(), rand.nextDouble());
        }
    }

    private BlockPos targetPos() {
        return getDataManager().get(TARGET_POS);
    }

    private Vec3d startPos() {
        return getDataManager().get(START_POS);
    }

    public int tick() {
        return getDataManager().get(TICK);
    }

    private void setTick(int t) {
        getDataManager().set(TICK, t);
    }

    public static boolean isEmptyPortalFrame(IBlockState blockState) {
        return blockState.getBlock() == Blocks.END_PORTAL_FRAME && !blockState.getValue(BlockEndPortalFrame.EYE);
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
        if (!world.isRemote)
            setFlag(6, isGlowing());

        onEntityUpdate();

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
    }
}
