package ru.mousecray.endmagic.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import ru.mousecray.endmagic.client.render.entity.RenderDungeonSlime;
import ru.mousecray.endmagic.util.registry.EMEntity;

@EMEntity(renderClass = RenderDungeonSlime.class)
public class EntityDungeonSlime extends EntityLiving {
    public EntityDungeonSlime(World worldIn) {
        super(worldIn);
    }

    public double getV() {
        return v;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public double getD() {
        return d;
    }

    private double v = 10;

    private double w, h, d = Math.cbrt(v);

    public void consistInvariant() {
        d = v / (w * h);
    }
}
