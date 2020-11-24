package ru.mousecray.endmagic.client.render.book;

import net.minecraft.client.renderer.block.model.IBakedModel;
import ru.mousecray.endmagic.client.render.model.baked.BakedModelDelegate;

public class TEISRModelWrapper extends BakedModelDelegate {

    public TEISRModelWrapper(IBakedModel base) {
        super(base);
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

}