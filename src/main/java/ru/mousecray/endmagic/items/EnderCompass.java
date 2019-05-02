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

import java.util.Optional;
import java.util.UUID;

public class EnderCompass extends Item implements IEMModel {
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public static int getCompassKey(ItemStack stack) {
        return Optional.ofNullable(stack.getTagCompound()).map(i -> i.getInteger("compassKey")).orElse(0);
    }

    private static void checkAndSetKey(ItemStack stack, Entity entityIn) {
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());

        if (!stack.getTagCompound().hasKey("compassKey") || stack.getTagCompound().getInteger("entityKey") != entityIn.hashCode()) {
            stack.getTagCompound().setInteger("compassKey", UUID.randomUUID().hashCode());
            stack.getTagCompound().setInteger("entityKey", entityIn.hashCode());
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        checkAndSetKey(stack, entityIn);
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), "inventory"), ModelEnderCompass::new);
    }
}
