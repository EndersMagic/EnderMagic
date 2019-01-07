package endmagic.com.github.dahaka934.jhocon.fieldlhandler;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Field;

/**
 * Allows to make some action before writing value to {@link JsonWriter}
 * or after reading value from {@link JsonReader}.
 */
public interface FieldHandler {
    /**
     * Action before writing field.
     *
     * @param field target field
     * @param value value of target field
     * @return the new value
     */
    Object onWrite(JsonWriter writer, Field field, Object value);

    /**
     * Action after reading value from {@code reader} and before writing {@code value} to {@code field}.
     * You can overload writing value, if return something else value.
     *
     * @param field target field
     * @param value the read value
     * @return the new value
     */
    default Object onRead(JsonReader reader, Field field, Object value) {
        return value;
    }
}
