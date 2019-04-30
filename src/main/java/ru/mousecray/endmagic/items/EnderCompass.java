package ru.mousecray.endmagic.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.ModelEnderCompass;
import ru.mousecray.endmagic.util.IEMModel;

import java.util.UUID;

public class EnderCompass extends Item implements IEMModel {
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public static String getCompassKey(ItemStack stack) {
        checkAndSetKey(stack);
        return stack.getTagCompound().getString("compassKey");
    }

    private static void checkAndSetKey(ItemStack stack) {
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());

        if (!stack.getTagCompound().hasKey("compassKey"))
            stack.getTagCompound().setString("compassKey", UUID.randomUUID().toString());
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        checkAndSetKey(stack);
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), "inventory"), ModelEnderCompass::new);
    }
}
