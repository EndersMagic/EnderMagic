package endmagic.com.github.dahaka934.jhocon.writer;

import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonWriterStub;

/**
 * Using for write object to specific structure instead of json.
 */
public class JHoconWriter extends JsonWriterStub {
    private Node curr = new Node(null);
    private final boolean withComments;

    public JHoconWriter(boolean withComments) {
        this.withComments = withComments;
    }

    /**
     * @return writing output
     */
    public Object output() {
        return curr.getValue();
    }

    /**
     * Set comment to current node.
     */
    public void comment(String comment) {
        if (withComments) {
            curr.comment(comment);
        }
    }

    @Override
    public JsonWriter beginArray() {
        curr = curr.beginArray();
        return this;
    }

    @Override
    public JsonWriter endArray() {
        curr = curr.endArray();
        return this;
    }

    @Override
    public JsonWriter beginObject() {
        curr = curr.beginObject();
        return this;
    }

    @Override
    public JsonWriter endObject() {
        curr = curr.endObject();
        return this;
    }

    @Override
    public JsonWriter name(String name) {
        curr.name(name);
        return this;
    }

    @Override
    public JsonWriter value(String value) {
        curr.value(value);
        return this;
    }

    @Override
    public JsonWriter jsonValue(String value) {
        curr.jsonValue(value);
        return this;
    }

    @Override
    public JsonWriter nullValue() {
        curr.nullValue();
        return this;
    }

    @Override
    public JsonWriter value(boolean value) {
        curr.value(value);
        return this;
    }

    @Override
    public JsonWriter value(Boolean value) {
        curr.value(value);
        return this;
    }

    @Override
    public JsonWriter value(double value) {
        curr.value(value);
        return this;
    }

    @Override
    public JsonWriter value(long value) {
        curr.value(value);
        return this;
    }

    @Override
    public JsonWriter value(Number value) {
        curr.value(value);
        return this;
    }

    @Override
    public boolean isLenient() {
        return true;
    }

    @Override
    public void flush() {}

    @Override
    public void close() {}
}
