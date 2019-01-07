package endmagic.com.github.dahaka934.jhocon;

import endmagic.com.github.dahaka934.jhocon.fieldlhandler.FieldHandler;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.FieldHandlerComment;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.FieldHandlerValidator;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator.FieldValidator;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator.FieldValidatorCustomAnnotation;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator.FieldValidatorList;
import endmagic.com.github.dahaka934.jhocon.fieldlhandler.validator.FieldValidatorRange;
import com.google.gson.GsonBuilder;
import com.typesafe.config.ConfigResolveOptions;

/**
 * Builder for construct instance of {@link JHocon}.
 */
public final class JHoconBuilder {
    public final GsonBuilder gsonBuilder;
    private JHReflectTypeAdapterFactory customReflectFactory;
    private FieldHandlerValidator handlerValidator;
    private ConfigResolveOptions resolveOptions;
    private boolean withComment = false;

    public JHoconBuilder(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
    }

    public JHoconBuilder() {
        this(new GsonBuilder());
    }

    /**
     * Register custom reflective type adapter factory. Supported only one instance.
     */
    public JHoconBuilder registerReflectTypeAdapterFactory(JHReflectTypeAdapterFactory value) {
        if (customReflectFactory != null) {
            throw new RuntimeException("only one reflective type adapter factory is supported");
        }
        customReflectFactory = value;
        gsonBuilder.registerTypeAdapterFactory(customReflectFactory);
        return this;
    }

    /**
     * Register custom {@link FieldHandler}.<br/>
     * This method register default reflective type adapter factory, if its not present.
     */
    public JHoconBuilder registerFieldHandler(FieldHandler value) {
        getReflectFactory().register(value);
        return this;
    }

    /**
     * Register custom {@link FieldValidator}.<br/>
     * This method register default reflective type adapter factory, if its not present.
     */
    public JHoconBuilder registerFieldValidator(FieldValidator value) {
        getFieldHandlerValidator().register(value);
        return this;
    }

    /**
     * Throw exception on validation fail or print message to log. True by default.
     */
    public JHoconBuilder throwErrorOnValidationFail(Boolean value) {
        getFieldHandlerValidator().throwErrorOnFail = value;
        return this;
    }

    /**
     * This method register Register {@link FieldValidatorCustomAnnotation},
     * {@link FieldValidatorRange} and {@link FieldValidatorList}.<br/>
     * This method register default reflective type adapter factory, if its not present.
     */
    public JHoconBuilder registerDefaultValidators() {
        registerFieldValidator(new FieldValidatorCustomAnnotation());
        registerFieldValidator(new FieldValidatorRange());
        registerFieldValidator(new FieldValidatorList());
        return this;
    }

    /**
     * Enable comments.<br/>
     * This method register Register {@link FieldHandlerComment}<br/>
     * This method register default reflective type adapter factory, if its not present.
     */
    public JHoconBuilder withComments() {
        withComment = true;
        registerFieldHandler(new FieldHandlerComment());
        return this;
    }

    /**
     * Setting custom resolve options
     */
    public JHoconBuilder withOptions(ConfigResolveOptions opts) {
        resolveOptions = opts;
        return this;
    }

    public JHocon create() {
        ConfigResolveOptions opts = resolveOptions != null ? resolveOptions : ConfigResolveOptions.defaults();
        return new JHocon(gsonBuilder.create(), opts, withComment);
    }

    private JHReflectTypeAdapterFactory getReflectFactory() {
        if (customReflectFactory == null) {
            registerReflectTypeAdapterFactory(new JHReflectTypeAdapterFactory());
        }
        return customReflectFactory;
    }

    private FieldHandlerValidator getFieldHandlerValidator() {
        if (handlerValidator == null) {
            handlerValidator = new FieldHandlerValidator();
            registerFieldHandler(handlerValidator);
        }
        return handlerValidator;
    }
}
