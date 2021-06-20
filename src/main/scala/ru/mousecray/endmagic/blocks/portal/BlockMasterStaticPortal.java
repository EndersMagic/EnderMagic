package ru.mousecray.endmagic.blocks.portal;

import ru.mousecray.endmagic.tileentity.portal.TileMasterStaticPortal;

public class BlockMasterStaticPortal extends BlockMasterBasePortal<TileMasterStaticPortal> {
    public BlockMasterStaticPortal() {
        super(TileMasterStaticPortal::new);
    }
}
