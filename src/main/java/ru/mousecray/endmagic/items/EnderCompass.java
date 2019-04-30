package ru.mousecray.endmagic.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.client.render.model.baked.ModelEnderCompass;
import ru.mousecray.endmagic.util.IEMModel;

public class EnderCompass extends Item implements IEMModel {
    public static double getAngle(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getDouble("targetAngle") : 0;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        setAngle(stack, calcAngle(entityIn.getPosition(), new BlockPos(0, 0, 0)));
    }

    private void setAngle(ItemStack stack, double angle) {
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setDouble("targetAngle", angle);
    }

    private double calcAngle(BlockPos pos1, BlockPos pos) {
        return Math.atan2(pos1.getX() - pos.getX(), pos1.getZ() - pos.getZ());
    }

    @Override
    public void registerModels(IModelRegistration modelRegistration) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        modelRegistration.addBakedModelOverride(new ModelResourceLocation(getRegistryName(), "inventory"), ModelEnderCompass::new);
    }
}
