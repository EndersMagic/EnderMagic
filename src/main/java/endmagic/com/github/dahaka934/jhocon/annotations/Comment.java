package endmagic.com.github.dahaka934.jhocon.annotations;

import endmagic.com.github.dahaka934.jhocon.JHReflectTypeAdapterFactory;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.FieldHandler;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.FieldHandlerComment;
import com.google.gson.GsonBuilder;

import java.lang.annotation.*;

/**
 * This is annotation used for setting a comment above the field.<br/>
 * You must register a {@link JHReflectTypeAdapterFactory} in {@link GsonBuilder}.<br/>
 * You must register implementer ({@link FieldHandler}) of this annotation in {@link JHReflectTypeAdapterFactory}.<br/>
 * Default implementer is {@link FieldHandlerComment}.<br/>
 * <br/>
 * You can use '$value' for inline default field value to comment line.<br/>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Comment {
    String value() default "default value: $value";
}
