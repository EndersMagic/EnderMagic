package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.mousecray.endmagic.util.CompoundList;

import javax.annotation.Nullable;
import java.util.List;

public class FinalisedModelEnderCompass extends BakedModelDelegate {
    private final IBakedModel originalModel;
    private final ItemStack stack;
    private final World world;
    private final EntityLivingBase entity;

    public FinalisedModelEnderCompass(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
        super(originalModel);
        this.originalModel = originalModel;
        this.stack = stack;
        this.world = world;
        this.entity = entity;
    }

    private List<BakedQuad> eye= ImmutableList.of();

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return new CompoundList<>(
                originalModel.getQuads(state, side, rand),
                generateEye(getEyePos())
        );
    }

    private Vec3d getEyePos() {
        return new Vec3d(0, 0, 0);
    }

    private List<BakedQuad> generateEye(Vec3d eyePos) {
        return translate(eye, eyePos);
    }

    private List<BakedQuad> translate(List<BakedQuad> eye, Vec3d eyePos) {
        eye.stream().map(i->i.)
    }
}
