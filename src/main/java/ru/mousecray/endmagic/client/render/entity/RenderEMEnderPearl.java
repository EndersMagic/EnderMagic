package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ru.mousecray.endmagic.entity.EntityEMEnderPearl;

public class RenderEMEnderPearl<T extends EntityEMEnderPearl> extends RenderSnowball<T> {

	public RenderEMEnderPearl(RenderManager renderManager, RenderItem itemRenderer) {
		super(renderManager, Items.AIR, itemRenderer);
	}
	
    @Override
	public ItemStack getStackToRender(T entity) {
        return entity.getCurrentItem();
    }
}