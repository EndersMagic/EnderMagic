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

@Mod.EventBusSubscriber(modid = EM.ID)
public class BlockPurpurQuartzBricks extends Block {
    public BlockPurpurQuartzBricks() {
        super(Material.ROCK);
        setHardness(1.5F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
    }

    static UUID speedModifier = UUID.randomUUID();

    @SubscribeEvent
    public static void speedupPlayer(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.side.isServer()) {
            EntityPlayer player = event.player;
            IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            if (event.player.onGround) {
                Block below = player.getEntityWorld().getBlockState(new BlockPos(player.posX, player.posY - (1 / 16D), player.posZ)).getBlock();

                if (below == EMBlocks.purpurQuartzBricks) {
                    if (entityAttribute.getModifier(speedModifier) == null)
                        entityAttribute.applyModifier(new AttributeModifier(speedModifier, "speedModifier", 0.5, 2));
                } else
                    entityAttribute.removeModifier(speedModifier);

            } else {
                Block below1 = player.getEntityWorld().getBlockState(new BlockPos(player.posX, player.posY - 2, player.posZ)).getBlock();
                Block below2 = player.getEntityWorld().getBlockState(new BlockPos(player.posX, player.posY - 1, player.posZ)).getBlock();
                if (player.capabilities.isFlying || below1 != EMBlocks.purpurQuartzBricks && below2 != EMBlocks.purpurQuartzBricks)
                    entityAttribute.removeModifier(speedModifier);
            }
        }

    }
}
