package endmagic.com.github.dahaka934.jhocon.fieldlhandler;

import endmagic.com.github.dahaka934.jhocon.JHoconHelper;
import endmagic.com.github.dahaka934.jhocon.annotations.Comment;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Field;

/**
 * Implementation for {@link Comment} annotation.
 *
 * @see Comment
 */
public class FieldHandlerComment implements FieldHandler {
    @Override
    public Object onWrite(JsonWriter writer, Field field, Object value) {
        Comment ann = field.getAnnotation(Comment.class);
        if (ann != null) {
            String line = ann.value();
            if (line.contains("$value")) {
                line = line.replace("$value", JHoconHelper.objectToString(value));
            }
            JHoconHelper.comment(writer, line);
        }
        return value;
    }
}
