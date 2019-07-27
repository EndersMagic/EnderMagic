package ru.mousecray.endmagic.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.mousecray.endmagic.client.gui.GuiScreenEMBook;
import ru.mousecray.endmagic.util.registry.NameProvider;

public class EMBook extends Item implements NameProvider, ItemOneWhiteEMTextured {

    public EMBook() {
        setMaxStackSize(1);
    }

    @Override
    public String name() {
        return "em_book";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (world.isRemote) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenEMBook());
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public String texture() {
        return "em_book";
    }
}
