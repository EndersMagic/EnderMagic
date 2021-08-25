package me.endersteam.endmagic

import me.endersteam.endmagic.init.EmBlocks
import me.endersteam.endmagic.init.EmItems
import me.endersteam.endmagic.init.EmTiles
import net.minecraftforge.fml.common.Mod

/*
 * Константа modId. Поставляется во все вызовы во время компиляции. Видно только внутри модуля - конфликтов не возникнет.
 */
internal const val modId = "endmagic"

@Mod(modId)
object EndersMagic
{
    init
    {
        EmBlocks.register()
        EmItems.register()
        EmTiles.register()
    }
}