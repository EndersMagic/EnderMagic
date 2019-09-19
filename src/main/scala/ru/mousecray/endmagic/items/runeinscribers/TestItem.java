package ru.mousecray.endmagic.items.runeinscribers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.client.render.model.baked.RichRectangleBakedQuad;
import ru.mousecray.endmagic.runes.EnumPartType;
import ru.mousecray.endmagic.runes.RuneIndex;
import ru.mousecray.endmagic.runes.RunePart;
import ru.mousecray.endmagic.teleport.Location;
import scala.Tuple3;

public class TestItem extends Item {
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        RichRectangleBakedQuad.VertexPos projectedCoords = RichRectangleBakedQuad.projectTo2d(new RichRectangleBakedQuad.VertexPos(hitX * 16, hitY * 16, hitZ * 16), facing);

        RuneIndex.addRunePart(new Location(pos, world), world.getBlockState(pos).getBlock(),
                facing, (int) ((float) projectedCoords._1()), (int) ((float) projectedCoords._2()), RunePart.apply(EnumPartType.FullPixel, 1));
        return EnumActionResult.SUCCESS;
    }
}
