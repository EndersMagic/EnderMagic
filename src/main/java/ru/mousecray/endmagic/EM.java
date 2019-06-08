package ru.mousecray.endmagic;

import javax.annotation.Nullable;
import org.apache.logging.log4j.Level;
import hohserg.jhocon.JHoconConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import ru.mousecray.endmagic.proxy.CommonProxy;
import ru.mousecray.endmagic.util.EMCreativeTab;
import ru.mousecray.endmagic.util.EMEntityDSI;

@Mod(modid = EM.ID, name = EM.NAME, version = EM.VERSION, dependencies = "after:jhocon")
public class EM {
    public static final String ID = "endmagic";
    public static final String VERSION = "@version@";
    public static final String NAME = "Ender's Magic";
    public static final String SERVER = "ru.mousecray.endmagic.proxy.CommonProxy";
    public static final String CLIENT = "ru.mousecray.endmagic.proxy.ClientProxy";
    public static EMCreativeTab EM_CREATIVE = new EMCreativeTab();

    public static DamageSource causeArrowDamage(EntityArrow arrow, @Nullable Entity indirectEntity) {
        return (new EMEntityDSI("arrow", arrow, indirectEntity)).setProjectile();
    }
	
    private static boolean debug = false;
	
	  public static void debug(String message) { 
		  if(debug) FMLRelaunchLog.log("[DEBUG] Ender's Magic", Level.INFO, message);
	  }

    @Instance(EM.ID)
    public static EM instance;

    public final static Config config = JHoconConfig.getOrCreateConfig(ID, Config::new);

    @SidedProxy(clientSide = EM.CLIENT, serverSide = EM.SERVER)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}