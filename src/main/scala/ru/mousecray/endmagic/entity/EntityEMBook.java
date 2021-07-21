package ru.mousecray.endmagic.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import ru.mousecray.endmagic.api.embook.BookApi;
import ru.mousecray.endmagic.api.embook.PageContainer;
import ru.mousecray.endmagic.client.render.book.RenderEntityEMBook;
import ru.mousecray.endmagic.util.registry.EMEntity;

import java.util.Optional;

import static ru.mousecray.endmagic.entity.EntityEMBook.BookState.stay;

@EMEntity(renderClass = RenderEntityEMBook.class)
public class EntityEMBook extends EntityItem {
    public EntityEMBook(World worldIn) {
        super(worldIn);

        init();
    }

    public EntityEMBook(World world, EntityItem base) {
        super(world, base.posX, base.posY, base.posZ, base.getItem().copy());

        motionX = base.motionX;
        motionY = base.motionY;
        motionZ = base.motionZ;

        hoverStart = base.hoverStart;
        rotationYaw = base.rotationYaw;

        setPickupDelay(40);

        init();
    }

    private void init() {
        setSize(0.5f, 0.5f);

        setNoDespawn();
    }


    PageContainer current = BookApi.mainChapter();
    BookState state = stay;
    Optional<PageContainer> next = Optional.empty();

    //=>

    public enum BookState {
        stay, flipLeft, flipRight, flipFar

    }
}
