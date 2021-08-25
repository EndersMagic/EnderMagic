package me.endersteam.endmagic.data

import me.endersteam.endmagic.modId
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class ItemModelProvider(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : ItemModelProvider(generator, modId, existingFileHelper)
{
    override fun registerModels()
    {

    }
}