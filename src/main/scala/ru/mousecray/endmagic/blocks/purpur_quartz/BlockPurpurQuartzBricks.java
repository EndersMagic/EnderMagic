package ru.mousecray.endmagic.blocks.purpur_quartz;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.init.EMBlocks;

import java.util.UUID;

public class BlockPurpurQuartzBricks extends Block {
    public BlockPurpurQuartzBricks() {
        super(Material.ROCK);
        setHardness(1.5F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

    static UUID speedModifier = UUID.randomUUID();

}
