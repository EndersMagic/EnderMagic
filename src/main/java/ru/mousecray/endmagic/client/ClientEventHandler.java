package ru.mousecray.endmagic.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.mousecray.endmagic.client.render.model.baked.TexturedModel;
import ru.mousecray.endmagic.items.ItemNamed;

import java.util.ArrayList;

public class ClientEventHandler {

    private static ArrayList<ResourceLocation> forRegister = new ArrayList<>();

    public static void registerTexture(ResourceLocation resourceLocation) {
        forRegister.add(resourceLocation);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void stitcherEventPre(TextureStitchEvent.Pre event) {
        TextureMap map = event.getMap();
        TextureMap map1 = Minecraft.getMinecraft().getTextureMapBlocks();
        System.out.println(map==map1);
        ClientEventHandler.forRegister.forEach(map::registerSprite);
        System.out.println();
    }

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        ModelResourceLocation model = ItemNamed.companion.simpletexturemodel;
        IBakedModel existingModel = event.getModelRegistry().getObject(model);
        if (existingModel != null) {
            IBakedModel customModel = new TexturedModel(existingModel);
            event.getModelRegistry().putObject(model, customModel);
        }
    }
}
