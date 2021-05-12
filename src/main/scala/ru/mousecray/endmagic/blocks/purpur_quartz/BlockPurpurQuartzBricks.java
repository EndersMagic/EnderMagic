package ru.mousecray.endmagic.blocks.purpur_quartz;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.Configuration;
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

    @SideOnly(Side.CLIENT)
    private static MovementInput manualInputCheck;


    //Based on Chisel code: https://github.com/Chisel-Team/Chisel/blob/1.12/dev/src/main/java/team/chisel/client/handler/BlockSpeedHandler.java#L32
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void speedupPlayer(TickEvent.PlayerTickEvent event) {
        if (Configuration.purpurQuartzBricksVelocityModifier > 1)
            if (event.phase == TickEvent.Phase.START && event.side.isClient() && event.player instanceof EntityPlayerSP) {
                if (manualInputCheck == null) {
                    manualInputCheck = new MovementInputFromOptions(Minecraft.getMinecraft().gameSettings);
                }
                EntityPlayerSP player = (EntityPlayerSP) event.player;
                Block below = player.getEntityWorld().getBlockState(new BlockPos(player.posX, player.posY - (1 / 16D), player.posZ)).getBlock();
                Block below1 = player.getEntityWorld().getBlockState(new BlockPos(player.posX, player.posY - 2, player.posZ)).getBlock();
                Block below2 = player.getEntityWorld().getBlockState(new BlockPos(player.posX, player.posY - 1, player.posZ)).getBlock();
                manualInputCheck.updatePlayerMoveState();
                if ((manualInputCheck.moveForward != 0 || manualInputCheck.moveStrafe != 0) && !player.isInWater()) {
                    if (event.player.onGround && below == EMBlocks.purpurQuartzBricks) {
                        player.motionX *= Configuration.purpurQuartzBricksVelocityModifier;
                        player.motionZ *= Configuration.purpurQuartzBricksVelocityModifier;
                    } else if (!player.capabilities.isFlying && (below1 == EMBlocks.purpurQuartzBricks || below2 == EMBlocks.purpurQuartzBricks)) {
                        double sprintJumpModifier = (Configuration.purpurQuartzBricksVelocityModifier - 1) / 3 + 1;
                        player.motionX *= sprintJumpModifier;
                        player.motionZ *= sprintJumpModifier;
                    }
                }
            }
    }
}
