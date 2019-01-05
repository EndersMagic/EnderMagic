package ru.mousecray.endmagic.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.ClientEventHandler;
import ru.mousecray.endmagic.render.IModelRegistration;
import ru.mousecray.endmagic.util.CreativeTabProvider;
import ru.mousecray.endmagic.util.IEMModel;

import javax.annotation.Nullable;

public abstract class ItemTextured extends Item implements IEMModel, CreativeTabProvider {
    public ItemTextured() {
        ClientEventHandler.registerTexture(new ResourceLocation(textureName()));
    }

    public abstract String textureName();

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, companion.simpletexturemodel);
        ModelLoader.setCustomModelResourceLocation(this, 0, companion.simpletexturemodel);
    }

    @Nullable
    @Override
    public CreativeTabs creativeTab() {
        return EM.EM_CREATIVE;
    }

    public static class companion {
        public static ItemTextured simpletexturemodelItem = new ItemTextured() {
            public String textureName() {
                return "none";
            }

            public CreativeTabs creativeTab() {
                return null;
            }
        };
        public static ModelResourceLocation simpletexturemodel = new ModelResourceLocation(EM.ID + ":simpletexturemodel", "inventory");

    }
}
