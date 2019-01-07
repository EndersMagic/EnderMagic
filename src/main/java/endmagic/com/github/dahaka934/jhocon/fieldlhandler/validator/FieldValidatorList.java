package endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator;

import endmagic.com.github.dahaka934.jhocon.annotations.ValidatorStringList;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Implementation for {@link ValidatorStringList} annotation.
 *
 * @see ValidatorStringList
 */
public class FieldValidatorList implements FieldValidator {

    @Override
    public boolean isValid(Field field, Object value) {
        if (value instanceof String) {
            String str = (String) value;
            ValidatorStringList ann = field.getAnnotation(ValidatorStringList.class);
            if (ann != null) {
                return !ann.invert() == Arrays.asList(ann.value()).contains(str);
            }
        }
        return true;
    }

    @Override
    public String getComment(Field field, Object value) {
        if (String.class.isAssignableFrom(field.getType())) {
            ValidatorStringList ann = field.getAnnotation(ValidatorStringList.class);
            if (ann != null) {
                StringBuilder b = new StringBuilder("valid values: ");
                if (ann.invert()) {
                    b.append("not ");
                }
                return b.append(Arrays.toString(ann.value())).toString();
            }
        }
        return null;
    }
}
