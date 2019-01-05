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
import ru.mousecray.endmagic.util.NameProvider;

import javax.annotation.Nullable;

public class ItemNamed extends Item implements NameProvider, IEMModel, CreativeTabProvider {

    public ItemNamed(String name) {
        this.name = name;
        ClientEventHandler.registerTexture(new ResourceLocation(textureName()));
    }

    private String name;

    @Override
    public String name() {
        return name;
    }

    public String textureName() {
        return EM.ID + ":items/" + name;
    }

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
        public static ItemNamed simpletexturemodelItem = new ItemNamed("simpletexturemodel") {
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
