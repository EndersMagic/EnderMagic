package ru.mousecray.endmagic.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import ru.mousecray.endmagic.EndMagic;
import ru.mousecray.endmagic.EndMagicData;
import ru.mousecray.endmagic.items.ListItem;

public class ListEntity {
	public static void onRegister() {
		EntityRegistry.registerModEntity(new ResourceLocation(EndMagicData.ID, "purple_pearl"), EMEnderPearl.class, EndMagicData.ID + ":purple_pearl", 0, EndMagic.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(EndMagicData.ID, "ender_arrow"), EMEnderArrow.class, EndMagicData.ID + ":ender_arrow", 1, EndMagic.instance, 64, 20, true);
	}
	
	public static void onRender() {
		RenderingRegistry.registerEntityRenderingHandler(EMEnderPearl.class, new EMRenderFactoryS(ListItem.PURPLE_PEARL));
		RenderingRegistry.registerEntityRenderingHandler(EMEnderArrow.class, new IRenderFactory() {@Override public Render createRenderFor(RenderManager manager) {return new RenderEnderArrow(manager);}});
	}
}
