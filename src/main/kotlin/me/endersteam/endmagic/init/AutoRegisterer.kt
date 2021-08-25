package me.endersteam.endmagic.init

import me.endersteam.endmagic.modId
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_CONTEXT

abstract class AutoRegisterer<T : IForgeRegistryEntry<T>>(registry : IForgeRegistry<T>)
{
    protected val registerer = KDeferredRegister(registry, modId)

    fun register() = registerer.register(MOD_CONTEXT.getKEventBus())
}