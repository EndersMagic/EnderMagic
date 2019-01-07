package endmagic.com.github.dahaka934.jhocon;

import endmagic.com.github.dahaka934.jhocon.fieldlhandler.FieldHandler;
import endmagic.com.github.dahaka934.jhocon.reader.JHoconReader;
import endmagic.com.github.dahaka934.jhocon.writer.JHoconWriter;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactoryEx;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JHReflectTypeAdapterFactory extends ReflectiveTypeAdapterFactoryEx {

    protected final List<FieldHandler> handlers = new ArrayList<>();

    /**
     * Register custom {@link FieldHandler}.
     *
     * @see FieldHandler
     */
    public void register(FieldHandler handler) {
        handlers.add(handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void writeField(JsonWriter writer, TypeAdapter adapter, Field field, Object value) throws IOException {
        if (writer instanceof JHoconWriter) {
            for (FieldHandler it : handlers) {
                value = it.onWrite(writer, field, value);
            }
        }
        adapter.write(writer, value);
    }

    @Override
    public Object readField(JsonReader reader, TypeAdapter adapter, Field field) throws IOException {
        Object ret = adapter.read(reader);
        if (reader instanceof JHoconReader) {
            for (FieldHandler it : handlers) {
                if (ret == null) {
                    break;
                }
                ret = it.onRead(reader, field, ret);
            }
        }
        return ret;
    }
}
