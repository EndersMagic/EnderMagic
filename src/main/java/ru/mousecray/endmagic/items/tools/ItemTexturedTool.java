package ru.mousecray.endmagic.items.tools;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.items.ItemTextured;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import java.util.Map;

public interface ItemTexturedTool extends ItemTextured {
    @Override
	default Map<String, Integer> textures() {
        return ImmutableMap.of(
                stick(), 0xffffffff,
                EM.ID + ":items/tools/colorless_" + toolType(), color());
    }

    @SideOnly(Side.CLIENT)
    @Override
    default Matrix4f handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return Minecraft.getMinecraft().getRenderItem()
                .getItemModelWithOverrides(new ItemStack(Items.DIAMOND_PICKAXE), Minecraft.getMinecraft().world, null).handlePerspective(cameraTransformType).getRight();
    }

    default String stick() {
        return "minecraft:items/stick";
    }

    String toolType();

    int color();
}
