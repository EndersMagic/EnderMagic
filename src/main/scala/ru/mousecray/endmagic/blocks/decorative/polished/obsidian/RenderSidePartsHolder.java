package ru.mousecray.endmagic.blocks.decorative.polished.obsidian;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSidePartsHolder.RenderSideParts.FaceVisibility;
import ru.mousecray.endmagic.util.PrettyToStringWithManyElementsCollection;

import java.util.Collection;
import java.util.Map;

import static ru.mousecray.endmagic.blocks.decorative.polished.obsidian.RenderSidePartsHolder.RenderSideParts.FaceVisibility.visible_all;


public enum RenderSidePartsHolder {
    instance;

    private RenderSideParts[] originals;
    private Collection<RenderSideParts> allowedValues;

    RenderSidePartsHolder() {
        originals = new RenderSideParts[1024];
        for (FaceVisibility up : FaceVisibility.verticalValues())
            for (FaceVisibility down : FaceVisibility.verticalValues())
                for (FaceVisibility east : FaceVisibility.horizontalValues())
                    for (FaceVisibility south : FaceVisibility.horizontalValues())
                        for (FaceVisibility west : FaceVisibility.horizontalValues())
                            for (FaceVisibility north : FaceVisibility.horizontalValues())
                                originals[getIndexKey(up, down, east, south, west, north)] = new RenderSideParts(up, down, east, south, west, north);
        allowedValues = new PrettyToStringWithManyElementsCollection<>(ImmutableSet.copyOf(originals));
    }

    private static int getIndexKey(FaceVisibility up, FaceVisibility down, FaceVisibility east, FaceVisibility south, FaceVisibility west, FaceVisibility north) {
        return (north.ordinal() << 8) + (west.ordinal() << 6) + (south.ordinal() << 4) + (east.ordinal() << 2) + (down.ordinal() << 1) + up.ordinal();
    }

    public static class RenderSideParts implements Comparable<RenderSideParts> {

        static RenderSideParts allSidesIsFull() {
            return RenderSideParts.apply(visible_all, visible_all, visible_all, visible_all, visible_all, visible_all);
        }

        public final FaceVisibility up, down, east, south, west, north;

        public static RenderSideParts apply(FaceVisibility up,
                                            FaceVisibility down,
                                            FaceVisibility east,
                                            FaceVisibility south,
                                            FaceVisibility west,
                                            FaceVisibility north) {
            return RenderSidePartsHolder.instance.originals[getIndexKey(up, down, east, south, west, north)];
        }

        public static RenderSideParts apply(Map<EnumFacing, FaceVisibility> values) {
            return apply(
                    values.get(EnumFacing.UP),
                    values.get(EnumFacing.DOWN),
                    values.get(EnumFacing.EAST),
                    values.get(EnumFacing.SOUTH),
                    values.get(EnumFacing.WEST),
                    values.get(EnumFacing.NORTH)
            );
        }

        private RenderSideParts(FaceVisibility up, FaceVisibility down, FaceVisibility east, FaceVisibility south, FaceVisibility west, FaceVisibility north) {
            this.up = up;
            this.down = down;
            this.east = east;
            this.south = south;
            this.west = west;
            this.north = north;
        }

        public enum FaceVisibility {
            visible_all {
                public FaceVisibility invert() {
                    return invisible_all;
                }
            }, invisible_all {
                public FaceVisibility invert() {
                    return visible_all;
                }
            }, invisible_top {
                public FaceVisibility invert() {
                    return invisible_bottom;
                }
            }, invisible_bottom {
                public FaceVisibility invert() {
                    return invisible_top;
                }
            };

            abstract FaceVisibility invert();

            public static FaceVisibility[] verticalValues() {
                return new FaceVisibility[]{visible_all, invisible_all};
            }

            public static FaceVisibility[] horizontalValues() {
                return values();
            }
        }

        public FaceVisibility get(EnumFacing side) {
            switch (side) {
                case DOWN:
                    return down;
                case UP:
                    return up;
                case NORTH:
                    return north;
                case SOUTH:
                    return south;
                case WEST:
                    return west;
                case EAST:
                    return east;
                default:
                    throw new IllegalArgumentException("Unsupported value of EnumFacing: " + side);
            }
        }

        @Override
        public String toString() {
            return "RenderSideParts(" + up + "," + down + "," + east + "," + south + "," + west + "," + north + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof RenderSideParts) {
                RenderSideParts b = (RenderSideParts) obj;
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

        @Override
        public int compareTo(RenderSideParts o) {
            return 0;
        }

        public static IProperty<RenderSideParts> PROPERTY = new PropertyHelper<RenderSideParts>("render_side_parts", RenderSideParts.class) {


            @Override
            public Collection<RenderSideParts> getAllowedValues() {
                return RenderSidePartsHolder.instance.allowedValues;
            }

            @Override
            public Optional<RenderSideParts> parseValue(String value) {
                return Optional.absent();
            }

            @Override
            public String getName(RenderSideParts value) {
                return "default";
            }
        };
    }

}
