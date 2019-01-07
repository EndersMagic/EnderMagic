package endmagic.com.github.dahaka934.jhocon.reader;

import java.util.List;

class NodeArray extends Node {
    private List<Object> list;
    private int index = 0;

    @SuppressWarnings("unchecked")
    NodeArray(Node prev, Object value) {
        super(prev, null);
        list = (List<Object>) value;
        nextElement();
    }

    @Override
    Type getType() { return Type.ARRAY; }

    @Override
    void nextElement() {
        setCursor(index < list.size() ? list.get(index) : null);
        ++index;
    }

    @Override
    String nextName() {
        return null;
    }

    @Override
    void buildPath(StringBuilder buf) {
        prev.buildPath(buf);
        buf.append('[').append(index - 1).append(']');
    }
}
