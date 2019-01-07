package endmagic.com.github.dahaka934.jhocon.reader;

import java.util.Iterator;
import java.util.Map;

class NodeObject extends Node {
    private Iterator<String> iteratorKeys;
    private Iterator<Object> iteratorValues;

    private String keyCursor;
    private boolean isKeyCursor = false;

    @SuppressWarnings("unchecked")
    NodeObject(Node prev, Object value) {
        super(prev, null);
        Map<String, Object> map = (Map<String, Object>) value;
        iteratorKeys = map.keySet().iterator();
        iteratorValues = map.values().iterator();
        setCursor(iteratorValues.hasNext() ? iteratorValues.next() : null);
    }

    @Override
    Type getType() { return Type.OBJECT; }

    @Override
    void signalReadMap() {
        isKeyCursor = true;
        nextName();
    }

    @Override
    Object getCursor() {
        return (isKeyCursor) ? keyCursor : super.getCursor();
    }

    @Override
    void nextElement() {
        if (isKeyCursor) {
            isKeyCursor = false;
        } else {
            setCursor(iteratorValues.hasNext() ? iteratorValues.next() : null);
        }
    }

    @Override
    String nextName() {
        keyCursor = iteratorKeys.hasNext() ? iteratorKeys.next() : null;
        return keyCursor;
    }

    @Override
    void buildPath(StringBuilder buf) {
        prev.buildPath(buf);
        buf.append('.').append(keyCursor);
    }
}
