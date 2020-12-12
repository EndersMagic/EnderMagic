package ru.mousecray.endmagic.items.inscribers;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.capability.player.EmCapabilityProvider;
import ru.mousecray.endmagic.items.ItemOneWhiteEMTextured;
import ru.mousecray.endmagic.rune.RuneColor;
import ru.mousecray.endmagic.rune.RuneIndex;
import ru.mousecray.endmagic.util.PlanarGeometry;
import ru.mousecray.endmagic.util.Vec2i;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import static ru.mousecray.endmagic.Configuration.emPerRunePart;

public class BaseInscriber extends Item implements IExtendedProperties, ItemOneWhiteEMTextured {

    public RuneColor runeColor() {
        return runeColor;
    }

    private RuneColor runeColor;

    public double efficiency() {
        return efficiency;
    }

    private double efficiency;

    public BaseInscriber(RuneColor runeColor, double efficiency) {
        this.runeColor = runeColor;
        this.efficiency = efficiency;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Vec2i coord = PlanarGeometry.projectTo2d(new Vec3i((int) (hitX * 16), (int) (hitY * 16), (int) (hitZ * 16)), facing);
        RunePart part = new RunePart(runeColor());

        if (RuneIndex.canAddPart(world, pos, facing, coord, part))
            if (EmCapabilityProvider.getCapa(player).consumeEm(runeColor(), emPerRunePart, efficiency(), false))
                RuneIndex.addRunePart(world, pos, facing, coord, part);

        return EnumActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public String getCustomName() {
        return NameAndTabUtils.getName(this) + "_" + runeColor.name().toLowerCase();
    }

    @Override
    public String texture() {
        return "inscribers/" + runeColor.name().toLowerCase();
    }

    @Override
    public Matrix4f handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        //System.out.println(inUse);
        switch (cameraTransformType) {
            case FIRST_PERSON_LEFT_HAND:
                return transformation(0, 0, 0, 0, 90, 48f, 1, 1, 1);

            case FIRST_PERSON_RIGHT_HAND:
                return transformation(0, 0, 0, 0, 270, 312, 1, 1, 1);

            case THIRD_PERSON_LEFT_HAND:
                return transformation(0, 0, 0, 0, 90, 20, 0.5f, 0.5f, 0.5f);

            case THIRD_PERSON_RIGHT_HAND:
                return transformation(0, 0, 0, 0, 270, 340, 0.5f, 0.5f, 0.5f);

            case GROUND:
                return transformation(0, 3, 0, 0, 0, 0, 0.5f, 0.5f, 0.5f);

            default:
                return TRSRTransformation.identity().getMatrix();
        }
    }
}
