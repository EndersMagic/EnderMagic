package endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator;

import java.lang.reflect.Field;

/**
 * Validation of field by any criterion.
 */
public interface FieldValidator {

    /**
     * Validation of {@code field}.
     *
     * @param field target field
     * @param value the read value
     * @return true if field is valid and false for else.
     */
    boolean isValid(Field field, Object value);

    /**
     * Defines a comment in config for this validator.
     *
     * @param field target field
     * @param value value of target field
     * @return comment (null for empty comment)
     */
    String getComment(Field field, Object value);
}
