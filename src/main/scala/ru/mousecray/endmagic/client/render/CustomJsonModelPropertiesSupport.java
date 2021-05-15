package ru.mousecray.endmagic.client.render;

import net.minecraft.client.renderer.block.model.CustomPropertiesBlockPartFaceDeserializer;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderAccessor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.NotifiableByModelLoader_FMLClientHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import ru.mousecray.endmagic.EM;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = EM.ID, value = Side.CLIENT)
public class CustomJsonModelPropertiesSupport {

    //private static FMLClientHandler original;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void preSetup(ModelRegistryEvent e) {
        System.out.println("preSetupCustomJsonModelPropertiesSupport");
        replaceJsomModelParser();
        replaceFMLClientHandler();
    }

    public static void postSetup() {
        System.out.println("postSetupCustomJsonModelPropertiesSupport");
        replaceFaceBakery();
    }

    private static void replaceFMLClientHandler() {
        try {
            Field INSTANCE = FMLClientHandler.class.getDeclaredField("INSTANCE");
            FMLClientHandler original = ReflectionHelper.getPrivateValue(FMLClientHandler.class, null, "INSTANCE");
            EnumHelper.setFailsafeFieldValue(INSTANCE, null, new NotifiableByModelLoader_FMLClientHandler(original));
            System.out.println("Successfully to replace FMLClientHandler#INSTANCE");
        } catch (Exception e1) {
            System.out.println("Failed to replace FMLClientHandler#INSTANCE");
            e1.printStackTrace();
        }
    }

    private static void replaceJsomModelParser() {
        try {
            Field SERIALIZER = ModelBlock.class.getDeclaredField("SERIALIZER");
            EnumHelper.setFailsafeFieldValue(SERIALIZER, null, CustomPropertiesBlockPartFaceDeserializer.create());
            System.out.println("Successfully to replace ModelBlock#SERIALIZER");
        } catch (Exception e1) {
            System.out.println("Failed to replace ModelBlock#SERIALIZER");
            e1.printStackTrace();
        }
    }

    private static void replaceFaceBakery() {
        try {
            ReflectionHelper.setPrivateValue(ModelBakery.class, ModelLoaderAccessor.get(), new MarkingFaceBakery(), "faceBakery");
            System.out.println("Successfully to replace ModelBakery#faceBakery");
        } catch (Exception e1) {
            System.out.println("Failed to replace ModelBakery#faceBakery");
            e1.printStackTrace();
        }
    }
}
