package ru.mousecray.endmagic.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.client.render.model.baked.TexturedModel;
import ru.mousecray.endmagic.items.ItemTextured;
import ru.mousecray.endmagic.proxy.ClientProxy;

public class ClientEventHandler {

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        ModelResourceLocation model = ItemTextured.companion.simpletexturemodel;
        IBakedModel existingModel = event.getModelRegistry().getObject(model);
        if (existingModel != null) {
            IBakedModel customModel = new TexturedModel(existingModel);
            event.getModelRegistry().putObject(model, customModel);
        }
    }
}
