package ru.mousecray.endmagic.capability.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.TilePhantomAvoidingBlockBase;
import ru.mousecray.endmagic.util.EnderBlockTypes;

import java.util.*;

public class PhantonTreeAvoidProcess {
    public final World world;
    private List<TilePhantomAvoidingBlockBase> treeBlocks =new ArrayList<>();
    public int avoidTicks = 0;
    public boolean startAvoiding = true;

    public PhantonTreeAvoidProcess(World world) {
        this.world = world;
    }

    public void notifyCutting() {
        startAvoiding = true;
    }

    public void updateTree(int newAvoidTicks) {
        avoidTicks = newAvoidTicks;
        treeBlocks.forEach(tile -> tile.avoidTicks = newAvoidTicks);
    }

    public void tick() {
        if (startAvoiding) {
            if (avoidTicks >= 30)
                teleportTree();
            else
                updateTree(avoidTicks + 1);
        }
    }

    private void teleportTree() {
        Optional<BlockPos> newPos = findNewPos();
        newPos.ifPresent(blockPos -> {
            Vec3i offset = blockPos.subtract(treeBlocks.iterator().next().getPos().subtract(treeBlocks.iterator().next().offsetFromSapling));
            treeBlocks.forEach(tile -> tile.teleportBlock(blockPos));
            world.playerEntities.get(0).setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            avoidTicks = 0;
            startAvoiding = false;
        });
    }

    public static final int teleportRadius = 20;

    private Optional<BlockPos> findNewPos() {
        for (int i = 0; i < 10; i++) {
            int x = world.rand.nextInt(teleportRadius * 2) - teleportRadius;
            int z = world.rand.nextInt(teleportRadius * 2) - teleportRadius;
            BlockPos newPos = world.getTopSolidOrLiquidBlock(treeBlocks.iterator().next().getPos().add(x, 255, z));
            if (EnderBlockTypes.EnderTreeType.PHANTOM.generator().canGenerateThere(world, newPos))
                return Optional.of(newPos);
        }
        return Optional.empty();
    }

    public void addTile(TilePhantomAvoidingBlockBase tileEntity) {
        treeBlocks.add(tileEntity);
    }
}
