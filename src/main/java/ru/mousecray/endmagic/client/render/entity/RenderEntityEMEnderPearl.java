package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderEntityEMEnderPearl<T extends Entity> extends Render<T> {

	protected Item item;
	private final RenderItem itemRenderer;

	public RenderEntityEMEnderPearl(RenderManager renderManager, RenderItem itemRenderer) {
		super(renderManager);
		this.itemRenderer = itemRenderer;
	}
	
    public ItemStack getStackToRender(T entity) {
        return new ItemStack(this.item);
    }
	
    @Override
	protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}