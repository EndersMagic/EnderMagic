package ru.mousecray.endmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import ru.mousecray.endmagic.rune.RuneIndex;
import ru.mousecray.endmagic.util.PlanarGeometry;
import ru.mousecray.endmagic.util.Vec2i;

public class RuneEraser extends Item {

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Vec2i coord = PlanarGeometry.projectTo2d(new Vec3i((int) (hitX * 16), (int) (hitY * 16), (int) (hitZ * 16)), facing);

        RuneIndex.getRune(world, pos, facing)
                .ifPresent(rune -> rune.parts().remove(coord));

        return EnumActionResult.SUCCESS;
    }
}
