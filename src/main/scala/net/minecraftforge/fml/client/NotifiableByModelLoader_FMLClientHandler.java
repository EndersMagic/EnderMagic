package net.minecraftforge.fml.client;

import ru.mousecray.endmagic.client.render.CustomJsonModelPropertiesSupport;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class NotifiableByModelLoader_FMLClientHandler extends FMLClientHandler {
    public NotifiableByModelLoader_FMLClientHandler(FMLClientHandler original) throws IllegalAccessException {
        for (Field field : FMLClientHandler.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                field.set(this, field.get(original));
            }
        }
    }

    @Override
    public boolean hasError() {
        if (isCallFromModelLoader())
            CustomJsonModelPropertiesSupport.postSetup();

        return super.hasError();
    }

    private boolean isCallFromModelLoader() {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        //stackTrace[0] == isCallFromModelLoader
        //stackTrace[1] == hasError
        //stackTrace[2] == who call hasError
        return stackTrace[2].getMethodName().equals("setupModelRegistry");
    }
}
