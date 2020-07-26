package ru.mousecray.endmagic.items;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ItemTextured extends IExtendedProperties {

    Map<String, Integer> textures();

    @SideOnly(Side.CLIENT)
    default javax.vecmath.Matrix4f handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return Minecraft.getMinecraft().getRenderItem()
                .getItemModelWithOverrides(new ItemStack(Items.DIAMOND), Minecraft.getMinecraft().world, null).handlePerspective(cameraTransformType).getRight();
        //return net.minecraftforge.client.ForgeHooksClient.handlePerspective(model, cameraTransformType).getRight();
    }

    @Override
    default void registerModels(IModelRegistration modelRegistration) {
        textures().keySet().forEach(t -> modelRegistration.registerTexture(new ResourceLocation(t)));

        Item item = (Item) this;
        if (isModelExists(item.getRegistryName()))
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        else
            ModelLoader.setCustomModelResourceLocation(item, 0, companion.simpletexturemodel);
    }

    static boolean isModelExists(ResourceLocation modelResourceLocation) {
        InputStream inputStream = ItemTextured.class.getResourceAsStream("/assets/" + modelResourceLocation.getResourceDomain() + "/models/item/" + modelResourceLocation.getResourcePath() + ".json");
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignored) {
            }
            return true;
        } else
            return false;
    }

    class companion {
        //may be unused
        public static ItemTextured simpletexturemodelItem = new ItemTextured() {
            @Override
            public Map<String, Integer> textures() {
                return ImmutableMap.of("none", 0xffffff);
            }

            public CreativeTabs creativeTab() {
                return null;
            }
        };
        public static ModelResourceLocation simpletexturemodel = new ModelResourceLocation(EM.ID + ":simpletexturemodel", "inventory");

    }
}
