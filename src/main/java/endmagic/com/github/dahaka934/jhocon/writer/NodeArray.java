package endmagic.com.github.dahaka934.jhocon.writer;

import java.util.ArrayList;

class NodeArray extends Node {
    NodeArray(Node prev) {
        super(prev);
        setValue(new ArrayList<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    void put(Object value) {
        ((ArrayList<Object>) getValue()).add(convert(value));
    }
}
