package ru.mousecray.endmagic.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import ru.mousecray.endmagic.entity.EntityCustomEnderEye;

public class RenderEntityCustomEnderEye extends RenderSnowball<EntityCustomEnderEye> {
    public RenderEntityCustomEnderEye(RenderManager renderManagerIn) {
        super(renderManagerIn, Items.ENDER_EYE, Minecraft.getMinecraft().getRenderItem());
    }

    @Override
    public void doRender(EntityCustomEnderEye entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x + entity.motionX * partialTicks, y + entity.motionY * partialTicks, z + entity.motionZ * partialTicks, entityYaw, partialTicks);
    }
}
