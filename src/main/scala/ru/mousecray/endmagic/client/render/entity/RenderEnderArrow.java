package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.gameobj.entity.EntityEnderArrow;

public class RenderEnderArrow extends RenderArrow<EntityEnderArrow>{

    public RenderEnderArrow(RenderManager manager) {
        super(manager);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityEnderArrow entity) {
        return new ResourceLocation(EM.ID + ":textures/entity/projectiles/ender_arrow.png");
    }
}