package ru.mousecray.endmagic.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import ru.mousecray.endmagic.util.EnderBlockTypes;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.mousecray.endmagic.worldgen.WorldGenPhantomTree.areaRequirementsMax;
import static ru.mousecray.endmagic.worldgen.WorldGenPhantomTree.areaRequirementsMin;

public class TilePhantomAvoidingBlockMaster extends TilePhantomAvoidingBlockBase implements ITickable {
    public static final int teleportRadius = 20;

    private boolean startAvoiding = true;
    List<TilePhantomAvoidingBlockBase> allBlocks = new ArrayList<>();

    public TilePhantomAvoidingBlockMaster() {
    }

    public TilePhantomAvoidingBlockMaster(TilePhantomAvoidingBlockBase baseTile) {
        avoidTicks = baseTile.avoidTicks;
        pos = baseTile.getPos();
        world = baseTile.getWorld();

        WorldGenUtils.generateInArea(pos.add(areaRequirementsMin), pos.add(areaRequirementsMax), pos -> {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TilePhantomAvoidingBlockBase) {
                allBlocks.add((TilePhantomAvoidingBlockBase) tileEntity);
            }
        });
        startAvoiding = true;
    }

    void notifyAboutCutting() {
        if (!startAvoiding) {
            WorldGenUtils.generateInArea(pos.add(areaRequirementsMin), pos.add(areaRequirementsMax), pos -> {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof TilePhantomAvoidingBlockBase) {
                    allBlocks.add((TilePhantomAvoidingBlockBase) tileEntity);
                }
            });
            startAvoiding = true;
        }
    }

    @Override
    public void update() {
        if (startAvoiding) {
            if (avoidTicks >= 30)
                teleportTree();
            else
                updateTree(avoidTicks + 1);

        }
    }

    private void teleportTree() {
        Optional<BlockPos> newPos = findNewPos();
        System.out.println(newPos);
        newPos.ifPresent(blockPos -> {
            Vec3i offset = blockPos.subtract(pos);
            allBlocks.forEach(tile -> tile.teleportBlock(offset));
            world.playerEntities.get(0).setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        });

    }

    private Optional<BlockPos> findNewPos() {
        for (int i = 0; i < 10; i++) {
            int x = world.rand.nextInt(teleportRadius * 2) - teleportRadius;
            int z = world.rand.nextInt(teleportRadius * 2) - teleportRadius;
            BlockPos newPos = world.getTopSolidOrLiquidBlock(pos.add(x, 255, z));
            if (EnderBlockTypes.EnderTreeType.PHANTOM.generator().canGenerateThere(world, newPos))
                return Optional.of(newPos);
        }
        return Optional.empty();
    }

    private void updateTree(int newAvoidTicks) {
        allBlocks.forEach(tile -> tile.avoidTicks = newAvoidTicks);
    }
}
