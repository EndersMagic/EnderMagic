package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.entity.EntityEMEnderPearl;

@SideOnly(Side.CLIENT)
public class RenderEMEnderPearl<T extends EntityEMEnderPearl> extends RenderSnowball<T> {

	public RenderEMEnderPearl(RenderManager renderManager, RenderItem itemRenderer) {
		super(renderManager, Items.AIR, itemRenderer);
	}
	
    @Override
	public ItemStack getStackToRender(T entity) {
        return entity.getItemStack();
    }
}