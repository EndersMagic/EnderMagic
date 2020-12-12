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
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.util.Map;

import static net.minecraftforge.common.model.TRSRTransformation.quatFromXYZDegrees;
import static ru.mousecray.endmagic.util.ResourcesUtils.isModelExists;

public interface ItemTextured extends IExtendedProperties {

    Map<String, Integer> textures();

    @SideOnly(Side.CLIENT)
    default javax.vecmath.Matrix4f handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return Minecraft.getMinecraft().getRenderItem()
                .getItemModelWithOverrides(new ItemStack(Items.DIAMOND), Minecraft.getMinecraft().world, null).handlePerspective(cameraTransformType).getRight();
        //return net.minecraftforge.client.ForgeHooksClient.handlePerspective(model, cameraTransformType).getRight();
    }

    default Matrix4f transformation(float translateX, float translateY, float translateZ, float rotateX, float rotateY, float rotateZ, float scaleX, float scaleY, float scaleZ) {
        return new TRSRTransformation(new Vector3f(translateX, translateY, translateZ), quatFromXYZDegrees(new Vector3f(rotateX, rotateY, rotateZ)), new Vector3f(scaleX, scaleY, scaleZ), null).getMatrix();
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
