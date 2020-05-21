package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import ru.mousecray.endmagic.entity.EntityCustomEnderEye;

public class RenderEntityCustomEnderEye extends RenderSnowball<EntityCustomEnderEye> {
    public RenderEntityCustomEnderEye(RenderManager renderManagerIn) {
        super(renderManagerIn, Items.ENDER_EYE, Minecraft.getMinecraft().getRenderItem());
    }
}
