package endmagic.com.github.dahaka934.jhocon.reader;

import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class Node {
    final Node prev;
    private Object cursor;
    private boolean isEnd = false;

    enum Type {
        ROOT, OBJECT, ARRAY
    }

    Node(Node prev, Object value) {
        this.prev = prev;
        cursor = value;
    }

    Type getType() { return Type.ROOT; }

    Object getCursor() {
        return cursor;
    }

    void setCursor(Object cursor) {
        this.cursor = cursor;
    }

    void nextElement() throws IOException {
        isEnd = true;
        setCursor(null);
    }

    void signalReadMap() {}

    void assertEnd() throws IOException {
        if (isEnd) {
            throw new IOException("End of file");
        }
    }

    Node beginArray() throws IOException {
        assertEnd();
        return new NodeArray(this, getCursor());
    }

    Node endArray() throws IOException {
        isEnd = true;
        setCursor(null);
        prev.nextElement();
        return prev;
    }

    Node beginObject() throws IOException {
        assertEnd();
        return new NodeObject(this, getCursor());
    }

    Node endObject() throws IOException {
        isEnd = true;
        setCursor(null);
        prev.nextElement();
        return prev;
    }

    boolean hasNext() {
        return !isEnd && getCursor() != null;
    }

    JsonToken peek() {
        if (isEnd) {
            switch (getType()) {
                case OBJECT:
                    return JsonToken.END_OBJECT;
                case ARRAY:
                    return JsonToken.END_ARRAY;
                case ROOT:
                    return JsonToken.END_DOCUMENT;
            }
        }

        Object cursor = getCursor();
        if (cursor == null) {
            return JsonToken.NULL;
        } else if (cursor instanceof String) {
            return JsonToken.STRING;
        } else if (cursor instanceof Boolean) {
            return JsonToken.BOOLEAN;
        } else if (cursor instanceof Number) {
            return JsonToken.NUMBER;
        } else if (cursor instanceof Map) {
            return JsonToken.BEGIN_OBJECT;
        } else if (cursor instanceof List) {
            return JsonToken.BEGIN_ARRAY;
        }
        return JsonToken.NULL;
    }

    String nextName() {
        return null;
    }

    void nextNull() throws IOException {
        assertEnd();
        nextElement();
    }

    String nextString() throws IOException {
        assertEnd();
        String ret = getCursor().toString();
        nextElement();
        return ret;
    }

    boolean nextBoolean() throws IOException {
        assertEnd();
        Object cursor = getCursor();
        boolean ret = false;
        if (cursor instanceof Boolean) {
            ret = (Boolean) cursor;
        } else if (cursor instanceof String) {
            ret = Boolean.valueOf(cursor.toString());
        }
        nextElement();
        return ret;
    }

    double nextDouble() throws IOException {
        assertEnd();
        Object cursor = getCursor();
        double ret = 0.0;
        if (cursor instanceof Number) {
            ret = ((Number) cursor).doubleValue();
        } else if (cursor instanceof String) {
            ret = Double.valueOf(cursor.toString());
        }
        nextElement();
        return ret;
    }

    long nextLong() throws IOException {
        assertEnd();
        Object cursor = getCursor();
        long ret = 0L;
        if (cursor instanceof Number) {
            ret = ((Number) cursor).longValue();
        } else if (cursor instanceof String) {
            ret = Long.valueOf(cursor.toString());
        }
        nextElement();
        return ret;
    }

    int nextInt() throws IOException {
        assertEnd();
        Object cursor = getCursor();
        int ret = 0;
        if (cursor instanceof Number) {
            ret = ((Number) cursor).intValue();
        } else if (cursor instanceof String) {
            ret = Integer.valueOf(cursor.toString());
        }
        nextElement();
        return ret;
    }

    void skipValue() throws IOException {
        assertEnd();
        nextElement();
    }

    void buildPath(StringBuilder buf) {

    }
}
