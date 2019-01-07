package endmagic.com.github.dahaka934.jhocon;

import endmagic.com.github.dahaka934.jhocon.writer.JHoconWriter;
import com.google.gson.stream.JsonWriter;

import java.util.Arrays;

public final class JHoconHelper {
    private JHoconHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Safe hook for insert comment to writer.
     */
    public static void comment(JsonWriter writer, String comment) {
        if (writer instanceof JHoconWriter) {
            ((JHoconWriter) writer).comment(comment);
        }
    }

    /**
     * Convert object to string.
     */
    public static String objectToString(Object obj) {
        if (obj == null) {
            return "(null)";
        } else if (!obj.getClass().isArray()) {
            return obj.toString();
        } else if (obj instanceof Object[]) {
            return Arrays.toString((Object[]) obj);
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        } else {
            return "";
        }
    }
}
