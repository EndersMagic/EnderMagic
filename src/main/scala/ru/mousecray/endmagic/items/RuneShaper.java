package ru.mousecray.endmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.mousecray.endmagic.capability.chunk.Rune;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.rune.RuneIndex;
import ru.mousecray.endmagic.util.Vec2i;
import scala.collection.JavaConversions;

import java.util.Map;

public class RuneShaper extends Item {
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            RuneIndex.getRune(world, pos, facing).ifPresent(this::println);
        return EnumActionResult.SUCCESS;
    }

    private void println(Rune rune) {
        Map<Vec2i, RunePart> parts = JavaConversions.mapAsJavaMap(rune.parts());
        StringBuilder useful = new StringBuilder("ImmutableMap.<Vec2i, RunePart>builder()\n");
        parts.forEach((coord, part) -> useful.append(".put(new Vec2i(" + coord.x + ", " + coord.y + "), new RunePart(" + part.color().name() + "))\n"));
        useful.append(".build()\n");
        System.out.println(useful.toString());
    }
}
