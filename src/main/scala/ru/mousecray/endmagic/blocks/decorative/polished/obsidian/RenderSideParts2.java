package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

public class RenderSideParts2 implements Comparable<RenderSideParts2> {

    public final VerticalFaceVisibility up, down;
    public final HorizontalFaceVisibility east, south, west, north;

    public static RenderSideParts2 apply(VerticalFaceVisibility up, VerticalFaceVisibility down, HorizontalFaceVisibility east, HorizontalFaceVisibility south, HorizontalFaceVisibility west, HorizontalFaceVisibility north) {
        return originals.computeIfAbsent(new RenderSideParts2(up, down, east, south, west, north), Function.identity());
    }

    private static Map<RenderSideParts2, RenderSideParts2> originals = new HashMap<>(325);

    private RenderSideParts2(VerticalFaceVisibility up, VerticalFaceVisibility down, HorizontalFaceVisibility east, HorizontalFaceVisibility south, HorizontalFaceVisibility west, HorizontalFaceVisibility north) {
        this.up = up;
        this.down = down;
        this.east = east;
        this.south = south;
        this.west = west;
        this.north = north;
    }

    public enum VerticalFaceVisibility implements Invertable<VerticalFaceVisibility> {
        visible_all {
            @Override
            public VerticalFaceVisibility invert() {
                return invisible_all;
            }
        }, invisible_all {
            @Override
            public VerticalFaceVisibility invert() {
                return visible_all;
            }
        }
    }

    public enum HorizontalFaceVisibility implements Invertable<HorizontalFaceVisibility> {
        visible_all {
            @Override
            public HorizontalFaceVisibility invert() {
                return invisible_all;
            }
        }, invisible_top {
            @Override
            public HorizontalFaceVisibility invert() {
                return invisible_bottom;
            }
        }, invisible_bottom {
            @Override
            public HorizontalFaceVisibility invert() {
                return invisible_top;
            }
        }, invisible_all {
            @Override
            public HorizontalFaceVisibility invert() {
                return visible_all;
            }
        }
    }

    public interface Invertable<Self extends Invertable> {
        Self invert();
    }

    @Override
    public String toString() {
        return "RenderSideParts2(" + up + "," + down + "," + east + "," + south + "," + west + "," + north + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RenderSideParts2) {
            RenderSideParts2 b = (RenderSideParts2) obj;
            return new EqualsBuilder()
                    .append(up, b.up)
                    .append(down, b.down)
                    .append(east, b.east)
                    .append(south, b.south)
                    .append(west, b.west)
                    .append(north, b.north)
                    .build();
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(up)
                .append(down)
                .append(east)
                .append(south)
                .append(west)
                .append(north)
                .build();
    }

    public static void main(String[] a) {
        System.out.println(new RenderSideParts2(VerticalFaceVisibility.visible_all, VerticalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all)
                .equals(new RenderSideParts2(VerticalFaceVisibility.visible_all, VerticalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all)));
        ReflectionHelper.setPrivateValue(Bootstrap.class, null, true, "alreadyRegistered");
        SoundEvent.registerSounds();
        BlockStateContainer blockStateContainer = new BlockStateContainer(new Block(Material.ROCK), PROPERTY);
        System.out.println(blockStateContainer.getBaseState().withProperty(PROPERTY, RenderSideParts2.apply(VerticalFaceVisibility.visible_all, VerticalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all, HorizontalFaceVisibility.visible_all)));
    }

    @Override
    public int compareTo(RenderSideParts2 o) {
        return 0;
    }

    public static IProperty<RenderSideParts2> PROPERTY = new PropertyHelper<RenderSideParts2>("render_side_parts", RenderSideParts2.class) {

        Collection<RenderSideParts2> allowedValues;

        {
            ImmutableSet.Builder<RenderSideParts2> builder = ImmutableSet.builder();
            for (VerticalFaceVisibility up : VerticalFaceVisibility.values())
                for (VerticalFaceVisibility down : VerticalFaceVisibility.values())
                    for (HorizontalFaceVisibility east : HorizontalFaceVisibility.values())
                        for (HorizontalFaceVisibility south : HorizontalFaceVisibility.values())
                            for (HorizontalFaceVisibility west : HorizontalFaceVisibility.values())
                                for (HorizontalFaceVisibility north : HorizontalFaceVisibility.values())
                                    builder.add(RenderSideParts2.apply(up, down, east, south, west, north));
            allowedValues = new PrettyToStringWithManyElementsCollection(builder.build());
        }

        @Override
        public Collection<RenderSideParts2> getAllowedValues() {
            return allowedValues;
        }

        @Override
        public Optional<RenderSideParts2> parseValue(String value) {
            return Optional.absent();
        }

        @Override
        public String getName(RenderSideParts2 value) {
            return "default";
        }
    };

    private static class PrettyToStringWithManyElementsCollection<E> implements Collection<E> {

        private final Collection<E> base;
        public static int maxFullStringRepresentLength = 50;

        public PrettyToStringWithManyElementsCollection(Collection<E> base) {
            this.base = base;
        }

        @Override
        public String toString() {
            String r = super.toString();
            if (r.length() <= maxFullStringRepresentLength)
                return r;
            else {
                String addition = "...]";
                return r.substring(0, maxFullStringRepresentLength - addition.length()) + addition;
            }
        }

        @Override
        public int size() {
            return base.size();
        }

        @Override
        public boolean isEmpty() {
            return base.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return base.contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return base.iterator();
        }

        @Override
        public Object[] toArray() {
            return base.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return base.toArray(a);
        }

        @Override
        public boolean add(E e) {
            return base.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return base.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return base.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return base.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return base.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return base.retainAll(c);
        }

        @Override
        public void clear() {
            base.clear();
        }
    }
}
