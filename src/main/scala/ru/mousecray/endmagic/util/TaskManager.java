package ru.mousecray.endmagic.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class TaskManager
{
    public static List<Truple<World, BlockPos, IBlockState>> blocksToPlace = new ArrayList<>();

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if(!event.world.isRemote)
        {
            if (event.phase == TickEvent.Phase.END)
            {
                System.out.println(blocksToPlace.size());
                for (int i = Math.min(1000, blocksToPlace.size()) - 1; i >= 0; --i)
                {
                    Truple<World, BlockPos, IBlockState> c = blocksToPlace.get(i);
                    c.first.setBlockState(c.second, c.third);
                    blocksToPlace.remove(i);
                }
            }
        }
    }
}
