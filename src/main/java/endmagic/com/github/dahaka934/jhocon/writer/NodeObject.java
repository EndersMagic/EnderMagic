package endmagic.com.github.dahaka934.jhocon.writer;

import java.util.HashMap;

class NodeObject extends Node {
    private String name = "unnamed";

    NodeObject(Node prev) {
        super(prev);
        setValue(new HashMap<String, Object>());
    }

    @Override
    @SuppressWarnings("unchecked")
    void put(Object value) {
        ((HashMap<String, Object>) getValue()).put(name, convert(value));
    }

    @Override
    void name(String name) {
        this.name = name;
    }
}
