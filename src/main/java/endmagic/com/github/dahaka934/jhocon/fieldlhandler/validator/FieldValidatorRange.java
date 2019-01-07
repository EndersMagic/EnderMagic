package endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator;

import endmagic.com.github.dahaka934.jhocon.annotations.ValidatorDoubleRange;
import endmagic.com.github.dahaka934.jhocon.annotations.ValidatorRange;
import com.google.gson.internal.Primitives;

import java.lang.reflect.Field;

/**
 * Implementation for {@link ValidatorRange} and {@link ValidatorDoubleRange} annotations.
 *
 * @see ValidatorRange
 * @see ValidatorDoubleRange
 */
public class FieldValidatorRange implements FieldValidator {

    @Override
    public boolean isValid(Field field, Object value) {
        if (value instanceof Number) {
            Number n = (Number) value;
            ValidatorRange ann1 = field.getAnnotation(ValidatorRange.class);
            if (ann1 != null) {
                return ann1.min() <= n.intValue() && n.intValue() <= ann1.max();
            }
            ValidatorDoubleRange ann2 = field.getAnnotation(ValidatorDoubleRange.class);
            if (ann2 != null) {
                return ann2.min() <= n.doubleValue() && n.doubleValue() <= ann2.max();
            }
        }
        return true;
    }

    @Override
    public String getComment(Field field, Object value) {
        String comment = "valid range";
        if (Number.class.isAssignableFrom(Primitives.wrap(field.getType()))) {
            ValidatorRange ann1 = field.getAnnotation(ValidatorRange.class);
            if (ann1 != null) {
                return String.format(comment + ": [%d, %d]", ann1.min(), ann1.max());
            }
            ValidatorDoubleRange ann2 = field.getAnnotation(ValidatorDoubleRange.class);
            if (ann2 != null) {
                return String.format(comment + ": [%f, %f]", ann2.min(), ann2.max());
            }
        }
        return null;
    }
}
