package endmagic.com.github.dahaka934.jhocon.fieldlhandler;

import endmagic.com.github.dahaka934.jhocon.JHoconHelper;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator.FieldValidator;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Common implementation for field validation.
 */
public class FieldHandlerValidator implements FieldHandler {
    protected final List<FieldValidator> validators = new ArrayList<>();
    protected final Logger logger = Logger.getLogger("JHoconFieldValidator");

    public boolean throwErrorOnFail = true;

    /**
     * Register custom {@link FieldValidator}.
     *
     * @see FieldValidator
     */
    public void register(FieldValidator validator) {
        validators.add(validator);
    }

    @Override
    public Object onWrite(JsonWriter writer, Field field, Object value) {
        for (FieldValidator it : validators) {
            String comment = it.getComment(field, value);
            if (comment != null && !comment.isEmpty()) {
                JHoconHelper.comment(writer, comment);
            }
        }
        return value;
    }

    @Override
    public Object onRead(JsonReader reader, Field field, Object value) {
        for (FieldValidator it : validators) {
            if (!it.isValid(field, value)) {
                if (throwErrorOnFail) {
                    throw new Exception(errorMessage(reader, value));
                } else {
                    logger.log(Level.WARNING, errorMessage(reader, value));
                }
                return null;
            }
        }
        return value;
    }

    protected String errorMessage(JsonReader reader, Object value) {
        return String.format("Field '%s' has incorrect value (%s)",
            reader.getPath(), JHoconHelper.objectToString(value));
    }

    public static class Exception extends RuntimeException {
        public Exception(String errorMessage) {
            super(errorMessage);
        }
    }
}
