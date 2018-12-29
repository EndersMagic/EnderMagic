package ru.mousecray.endmagic.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EndMagicData;

public class RenderEnderArrow extends RenderArrow<EMEnderArrow>{

    public RenderEnderArrow(RenderManager manager) {
        super(manager);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EMEnderArrow entity) {
        return new ResourceLocation(EndMagicData.ID + ":textures/entity/projectiles/ender_arrow.png");
    }
}
