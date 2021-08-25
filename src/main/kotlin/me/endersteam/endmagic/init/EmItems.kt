package me.endersteam.endmagic.init

import net.minecraft.item.Item
import net.minecraftforge.registries.ForgeRegistries

object EmItems : AutoRegisterer<Item>(ForgeRegistries.ITEMS)
{
    val naturalCoal: Item by register("natural_coal") { Item(Item.Properties()) }
    val phantomCoal: Item by register("phantom_coal") { Item(Item.Properties()) }
    val dragonCoal: Item by register("dragon_coal") { Item(Item.Properties()) }
    val immortalCoal: Item by register("immortal_coal") { Item(Item.Properties()) }

    val naturalSteel: Item by register("natural_steel") { Item(Item.Properties()) }
    val phantomSteel: Item by register("phantom_steel") { Item(Item.Properties()) }
    val dragonSteel: Item by register("dragon_steel") { Item(Item.Properties()) }
    val immortalSteel: Item by register("immortal_steel") { Item(Item.Properties()) }

    val naturalDiamond: Item by register("natural_diamond") { Item(Item.Properties()) }
    val phantomDiamond: Item by register("phantom_diamond") { Item(Item.Properties()) }
    val dragonDiamond: Item by register("dragon_diamond") { Item(Item.Properties()) }
    val immortalDiamond: Item by register("immortal_diamond") { Item(Item.Properties()) }
}