package ru.mousecray.endmagic.util;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public class EMEntityDSI extends EntityDamageSource {
	
    private final Entity indirectEntity;

    public EMEntityDSI(String damageType, Entity source, @Nullable Entity indirectEntity) {
        super(damageType, source);
        this.indirectEntity = indirectEntity;
    }
    
    @Override
    @Nullable
    public Entity getImmediateSource() {
        return this.damageSourceEntity;
    }
    
    @Override
    @Nullable
    public Entity getTrueSource() {
        return this.indirectEntity;
    }
    
    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBase) {
        ITextComponent itextcomponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
        ItemStack stack = this.indirectEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.indirectEntity).getHeldItemMainhand() : ItemStack.EMPTY;
        String s = "death.attack." + this.damageType;
        String s1 = s + ".item";
        return !stack.isEmpty() && stack.hasDisplayName() && I18n.canTranslate(s1) ? new TextComponentTranslation(s1, new Object[] {entityLivingBase.getDisplayName(), itextcomponent, stack.getTextComponent()}) : new TextComponentTranslation(s, new Object[] {entityLivingBase.getDisplayName(), itextcomponent});
    }
}