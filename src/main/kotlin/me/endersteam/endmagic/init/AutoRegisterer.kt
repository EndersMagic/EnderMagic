package me.endersteam.endmagic.init

import me.endersteam.endmagic.modId
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_CONTEXT
import kotlin.properties.ReadOnlyProperty

abstract class AutoRegisterer<T : IForgeRegistryEntry<T>>(registry : IForgeRegistry<T>)
{
    protected val registerer = KDeferredRegister(registry, modId)

    protected fun register(name : String, value : () -> T) : ReadOnlyProperty<Any?, T> = registerer.register(name, value)
    protected fun register(name : String, value : T) : ReadOnlyProperty<Any?, T> = registerer.register(name) { value }

    fun register() = registerer.register(MOD_CONTEXT.getKEventBus())
}