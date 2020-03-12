package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.entity.EntityEMEnderPearl;

@SideOnly(Side.CLIENT)
public class RenderEMEnderPearl<T extends EntityEMEnderPearl> extends RenderSnowball<T> {

	public RenderEMEnderPearl(RenderManager renderManager) {
		super(renderManager, Items.AIR, Minecraft.getMinecraft().getRenderItem());
	}
	
    @Override
	public ItemStack getStackToRender(T entity) {
        return entity.getItemStack();
    }
}