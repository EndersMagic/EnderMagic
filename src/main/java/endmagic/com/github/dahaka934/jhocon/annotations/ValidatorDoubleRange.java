package endmagic.com.github.dahaka934.jhocon.annotations;

import endmagic.com.github.dahaka934.jhocon.JHReflectTypeAdapterFactory;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.FieldHandlerValidator;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator.FieldValidator;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator.FieldValidatorRange;
import com.google.gson.GsonBuilder;

import java.lang.annotation.*;

/**
 * This is annotation used for setting special validation range for field. Only for {@code Number}.<br/>
 * You must register a {@link JHReflectTypeAdapterFactory} in {@link GsonBuilder}.<br/>
 * You must register a {@link FieldHandlerValidator} in {@link JHReflectTypeAdapterFactory}.<br/>
 * You must register implementer ({@link FieldValidator}) of this annotation in {@link FieldHandlerValidator}.<br/>
 * Default implementer is {@link FieldValidatorRange}.<br/>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidatorDoubleRange {
    double min() default Double.NEGATIVE_INFINITY;

    double max() default Double.POSITIVE_INFINITY;
}
