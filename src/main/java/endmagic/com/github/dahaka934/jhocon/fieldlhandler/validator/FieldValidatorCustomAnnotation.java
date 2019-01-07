package endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator;

import endmagic.com.github.dahaka934.jhocon.annotations.ValidatorCustom;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation for {@link ValidatorCustom} annotation.
 *
 * @see ValidatorCustom
 */
public class FieldValidatorCustomAnnotation implements FieldValidator {

    protected final Map<Class<? extends FieldValidator>, FieldValidator> validators = new HashMap<>();

    protected FieldValidator getValidator(Field field) {
        ValidatorCustom ann = field.getAnnotation(ValidatorCustom.class);
        FieldValidator validator = null;
        if (ann != null) {
            validator = validators.get(ann.value());
            if (validator == null) {
                try {
                    validator = ann.value().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    validators.put(ann.value(), validator);
                }
            }
        }
        return validator;
    }

    @Override
    public boolean isValid(Field field, Object value) {
        FieldValidator it = getValidator(field);
        return (it != null) ? it.isValid(field, value) : true;
    }

    @Override
    public String getComment(Field field, Object value) {
        FieldValidator it = getValidator(field);
        return (it != null) ? it.getComment(field, value) : null;
    }
}
