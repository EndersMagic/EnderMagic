package ru.mousecray.endmagic;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import ru.mousecray.endmagic.util.EMCreativeTab;
import ru.mousecray.endmagic.util.EMEntityDSI;

public class EndMagicData {
	public static final String ID = "endmagic";
	public static final String NAME = "Ender's Magic";
	public static final String VERSION = "1.3.0-beta";
	
	public static final String SERVER = "ru.mousecray.endmagic.proxy.CommonProxy";
	public static final String CLIENT = "ru.mousecray.endmagic.proxy.ClientProxy";
	
    public static DamageSource causeArrowDamage(EntityArrow arrow, @Nullable Entity indirectEntity) {
        return (new EMEntityDSI("arrow", arrow, indirectEntity)).setProjectile();
    }
    
    public static EMCreativeTab EM_CREATIVE = new EMCreativeTab();
}