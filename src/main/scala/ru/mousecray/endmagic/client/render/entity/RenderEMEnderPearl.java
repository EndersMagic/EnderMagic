package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.entity.EntityEMEnderPearl;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderEMEnderPearl extends RenderSnowball<EntityEMEnderPearl> {

    public RenderEMEnderPearl(RenderManager renderManager) {
        super(renderManager, Items.AIR, Minecraft.getMinecraft().getRenderItem());
    }

    @Nonnull
    @Override
    public ItemStack getStackToRender(EntityEMEnderPearl entity) {
        return entity.getItemStack();
    }
}