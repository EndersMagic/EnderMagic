package endmagic.com.github.dahaka934.jhocon.reader;

import com.google.gson.stream.JsonReaderStub;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

/**
 * Using for read object from specific structure instead of json.
 */
public class JHoconReader extends JsonReaderStub {
    private Node curr;

    public JHoconReader(Object value) {
        curr = new Node(null, value);
    }

    @Override
    public void beginArray() throws IOException {
        curr = curr.beginArray();
    }

    @Override
    public void endArray() throws IOException {
        curr = curr.endArray();
    }

    @Override
    public void beginObject() throws IOException {
        curr = curr.beginObject();
    }

    @Override
    public void endObject() throws IOException {
        curr = curr.endObject();
    }

    @Override
    public boolean hasNext() throws IOException {
        return curr.hasNext();
    }

    @Override
    public JsonToken peek() throws IOException {
        setPeeked(0);
        return curr.peek();
    }

    @Override
    public String nextName() throws IOException {
        return curr.nextName();
    }

    @Override
    public String nextString() throws IOException {
        return curr.nextString();
    }

    @Override
    public boolean nextBoolean() throws IOException {
        return curr.nextBoolean();
    }

    @Override
    public void nextNull() throws IOException {
        curr.nextNull();
    }

    @Override
    public double nextDouble() throws IOException {
        return curr.nextDouble();
    }

    @Override
    public long nextLong() throws IOException {
        return curr.nextLong();
    }

    @Override
    public int nextInt() throws IOException {
        return curr.nextInt();
    }

    @Override
    public void skipValue() throws IOException {
        curr.skipValue();
    }

    @Override
    public String getPath() {
        StringBuilder builder = new StringBuilder();
        curr.buildPath(builder);
        return builder.toString();
    }

    @Override
    public void close() throws IOException {}

    @Override
    public String toString() {
        return curr.getCursor().toString();
    }

    @Override
    protected int doPeek() {
        // Hack
        // Signal from JsonReaderInternalAccess.INSTANCE.promoteNameToValue
        // Start reading map
        curr.signalReadMap();
        return 13; // PEEKED_DOUBLE_QUOTED_NAME
    }
}
