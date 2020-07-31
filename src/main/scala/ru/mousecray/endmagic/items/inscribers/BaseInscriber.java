package ru.mousecray.endmagic.items.inscribers;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.capability.chunk.RunePart;
import ru.mousecray.endmagic.items.ItemTextured;
import ru.mousecray.endmagic.rune.RuneColor;
import ru.mousecray.endmagic.rune.RuneIndex;
import ru.mousecray.endmagic.util.PlanarGeometry;
import ru.mousecray.endmagic.util.Vec2i;
import ru.mousecray.endmagic.util.registry.IExtendedProperties;
import ru.mousecray.endmagic.util.registry.NameAndTabUtils;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Map;

public class BaseInscriber extends Item implements IExtendedProperties, ItemTextured {

    public RuneColor runeColor() {
        return runeColor;
    }

    private RuneColor runeColor;

    public BaseInscriber(RuneColor runeColor) {
        this.runeColor = runeColor;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Vec2i coord = PlanarGeometry.projectTo2d(new Vec3i((int) (hitX * 16), (int) (hitY * 16), (int) (hitZ * 16)), facing);
        RuneIndex.addRunePart(world, pos, facing, coord, new RunePart(runeColor()));

        return EnumActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public String getCustomName() {
        return NameAndTabUtils.getName(this) + "_" + runeColor.name();
    }

    @Override
    public Map<String, Integer> textures() {
        return ImmutableMap.of(EM.ID + ":items/inscribers/" + runeColor.name(), 0xffffffff);
    }
}
