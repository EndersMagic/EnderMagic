package ru.mousecray.endmagic.capability.world;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.tileentity.TilePhantomAvoidingBlockBase;
import ru.mousecray.endmagic.util.worldgen.WorldGenUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.mousecray.endmagic.worldgen.WorldGenPhantomTree.areaRequirementsMax;
import static ru.mousecray.endmagic.worldgen.WorldGenPhantomTree.areaRequirementsMin;

public class PhantomTreeCapability {

    public final World world;

    public Map<BlockPos, PhantonTreeAvoidProcess> posToProcess = new HashMap<>();
    public Set<PhantonTreeAvoidProcess> processes = new HashSet<>();

    public PhantomTreeCapability(World world) {
        this.world = world;
    }

    public PhantonTreeAvoidProcess getTree(BlockPos pos) throws Exception {
        PhantonTreeAvoidProcess exists = posToProcess.get(pos);
        if (exists != null)
            return exists;
        else {
            TileEntity tileEntity1 = world.getTileEntity(pos);
            if (tileEntity1 instanceof TilePhantomAvoidingBlockBase) {
                BlockPos saplingPos = pos.subtract(((TilePhantomAvoidingBlockBase) tileEntity1).offsetFromSapling);
                PhantonTreeAvoidProcess r = new PhantonTreeAvoidProcess(world);
                WorldGenUtils.generateInArea(saplingPos.add(areaRequirementsMin), saplingPos.add(areaRequirementsMax), pos1 -> {
                    TileEntity tileEntity = world.getTileEntity(pos1);
                    if (tileEntity instanceof TilePhantomAvoidingBlockBase) {
                        r.addTile((TilePhantomAvoidingBlockBase) tileEntity);
                        world.setBlockState(pos1, Blocks.REDSTONE_BLOCK.getDefaultState());
                        //posToProcess.put(pos1, r);
                    }
                });
                 //processes.add(r);
                return r;
            } else
                throw new Exception("Phantom tree are not found");
        }
    }
}
