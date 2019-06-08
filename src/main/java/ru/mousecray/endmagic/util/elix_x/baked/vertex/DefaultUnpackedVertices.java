package ru.mousecray.endmagic.util.elix_x.baked.vertex;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.vertex.VertexFormat;

import java.util.Iterator;
import java.util.List;

public class DefaultUnpackedVertices implements Iterable<DefaultUnpackedVertex> {

    private List<DefaultUnpackedVertex> vertices;

    public DefaultUnpackedVertices(DefaultUnpackedVertex... vertices) {
        this.vertices = Lists.newArrayList(vertices);
    }

    public DefaultUnpackedVertices(List<DefaultUnpackedVertex> vertices) {
        this.vertices = Lists.newArrayList(vertices);
    }

    public DefaultUnpackedVertices(VertexFormat format, List<PackedVertex> vertices) {
        this(Lists.transform(vertices, PackedVertex::unpack));
    }

    public List<DefaultUnpackedVertex> getVertices() {
        return vertices;
    }

    public DefaultUnpackedVertices setVertices(List<DefaultUnpackedVertex> vertices) {
        this.vertices = vertices;
        return this;
    }

    @Override
    public Iterator<DefaultUnpackedVertex> iterator() {
        return vertices.iterator();
    }

    public PackedVertices pack(int mode, VertexFormat format) {
        return new PackedVertices(mode, format, Lists.transform(vertices, vertex -> vertex.pack(format)));
    }

}
