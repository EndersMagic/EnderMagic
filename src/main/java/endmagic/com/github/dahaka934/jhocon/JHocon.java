package endmagic.com.github.dahaka934.jhocon;

import endmagic.com.github.dahaka934.jhocon.reader.JHoconReader;
import endmagic.com.github.dahaka934.jhocon.writer.JHoconWriter;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;
import com.typesafe.config.*;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper of {@link Gson}.<br/>
 * Provides some useful method for convert objects to hocon files, and vice versa.<br/>
 * JHocon instances are thread-safe, because {@link Gson} is thread is thread-safe.<br/>
 */
public final class JHocon {
    public final Gson gson;
    private final ConfigRenderOptions renderOptions;
    private final ConfigResolveOptions resolveOptions;

    public JHocon(Gson gson, ConfigResolveOptions resolveOptions, boolean withComments) {
        this.gson = gson;
        this.resolveOptions = resolveOptions;
        renderOptions = ConfigRenderOptions.defaults().setJson(false).setOriginComments(withComments);
    }

    public JHocon(Gson gson) {
        this(gson, ConfigResolveOptions.defaults(), false);
    }

    /**
     * Converts generic object to object tree representation.<br/>
     * Analog of {@link Gson#toJson(Object, Type)}.
     *
     * @param src       the generic object for which object tree representation is to be created
     * @param typeOfSrc the specific genericized type of {@code src}
     * @return object tree representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    public Object toObjectTree(Object src, Type typeOfSrc) throws JsonIOException {
        JHoconWriter writer = new JHoconWriter(renderOptions.getOriginComments());
        gson.toJson(src, typeOfSrc, writer);
        return writer.output();
    }

    /**
     * Converts non-generic object to object tree representation.<br/>
     * Analog of {@link Gson#toJson(Object)}.
     *
     * @param src the non-generic object for which object tree representation is to be created
     * @return object tree representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    public Object toObjectTree(Object src) throws JsonIOException {
        Object obj = safeObject(src);
        return toObjectTree(obj, obj.getClass());
    }

    /**
     * Converts generic object to {@link ConfigValue} representation.<br/>
     * Analog of {@link Gson#toJson(Object, Type)}.
     *
     * @param src       the generic object for which {@link ConfigValue} representation is to be created
     * @param typeOfSrc the specific genericized type of {@code src}
     * @return {@link ConfigValue} representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    public ConfigValue toConfigValue(Object src, Type typeOfSrc) throws JsonIOException {
        Object tree = toObjectTree(src, typeOfSrc);
        try {
            return ConfigValueFactory.fromAnyRef(tree);
        } catch (Exception e) {
            throw new JsonIOException(e);
        }
    }

    /**
     * Converts non-generic object to {@link ConfigValue} representation.<br/>
     * Analog of {@link Gson#toJson(Object)}.
     *
     * @param src the non-generic object for which {@link ConfigValue} representation is to be created
     * @return {@link ConfigValue} representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    /**
     * @param src
     * @return
     * @throws JsonIOException
     */
    public ConfigValue toConfigValue(Object src) throws JsonIOException {
        Object obj = safeObject(src);
        return toConfigValue(obj, obj.getClass());
    }

    /**
     * Converts generic object to {@link Config} representation with specific {@code name}.<br/>
     * Analog of {@link Gson#toJson(Object, Type)}.
     *
     * @param name      the name of hocon object
     * @param src       the generic object for which {@link Config} representation is to be created
     * @param typeOfSrc the specific genericized type of {@code src}
     * @return {@link Config} representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    @SuppressWarnings("unchecked")
    public Config toConfig(String name, Object src, Type typeOfSrc) throws JsonIOException {
        Object tree = toObjectTree(src, typeOfSrc);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put(name, tree);
            return ConfigFactory.parseMap(map);
        } catch (Exception e) {
            throw new JsonIOException(e);
        }
    }

    /**
     * Converts non-generic object to {@link Config} representation with specific {@code name}.<br/>
     * Analog of {@link Gson#toJson(Object)}.
     *
     * @param name the name of hocon object
     * @param src  the non-generic object for which {@link Config} representation is to be created
     * @return {@link Config} representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    public Config toConfig(String name, Object src) throws JsonIOException {
        Object obj = safeObject(src);
        return toConfig(name, obj, obj.getClass());
    }

    /**
     * Converts generic object to HOCON representation with specific {@code name}.<br/>
     * Analog of {@link Gson#toJson(Object, Type)}.
     *
     * @param name      the name of hocon object
     * @param src       the generic object for which HOCON representation is to be created
     * @param typeOfSrc the specific genericized type of {@code src}
     * @return HOCON representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    public String toHocon(String name, Object src, Type typeOfSrc) throws JsonIOException {
        Config config = toConfig(name, src, typeOfSrc);

        String hocon;
        try {
            hocon = config.root().render(renderOptions);
        } catch (Throwable throwable) {
            throw new JsonSyntaxException(throwable);
        }

        // Config print '#  ' for empty comment. Try fix it.
        hocon = hocon.replaceAll("[ ]+#\n", "");

        return hocon;
    }

    /**
     * Converts non-generic object to HOCON representation with specific {@code name}.<br/>
     * Analog of {@link Gson#toJson(Object)}.
     *
     * @param name the name of hocon object
     * @param src  the non-generic object for which HOCON representation is to be created
     * @return HOCON representation of {@code src}
     * @throws JsonIOException if there was a problem writing to the writer
     */
    public String toHocon(String name, Object src) throws JsonIOException {
        Object obj = safeObject(src);
        return toHocon(name, obj, obj.getClass());
    }

    /**
     * Create generic object from {@link ConfigValue} representation.<br/>
     * Analog of {@link Gson#fromJson(Reader, Type)}.
     *
     * @param <T>         the type of the desired object
     * @param configValue the hocon parsed value
     * @param typeOfT     The specific genericized type of {@code src}
     * @return an object of type T
     * @throws JsonIOException     if there was a problem reading from {@link ConfigValue}
     * @throws JsonSyntaxException if {@link ConfigValue} is not a valid representation for an object of type
     */
    public <T> T fromHocon(ConfigValue configValue, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        JHoconReader reader = new JHoconReader(configValue.unwrapped());
        return gson.fromJson(reader, typeOfT);
    }

    /**
     * Create non-generic object from {@link ConfigValue} representation.<br/>
     * Analog of {@link Gson#fromJson(Reader, Class)}.
     *
     * @param <T>         the type of the desired object
     * @param configValue the hocon parsed value
     * @param classOfT    the class of T
     * @return an object of type T
     * @throws JsonIOException     if there was a problem reading from {@link ConfigValue}
     * @throws JsonSyntaxException if {@link ConfigValue} is not a valid representation for an object of type
     */
    public <T> T fromHocon(ConfigValue configValue, Class<T> classOfT) throws JsonIOException, JsonSyntaxException {
        T object = fromHocon(configValue, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    /**
     * Create generic object from HOCON representation with specific {@code name}.<br/>
     * Analog of {@link Gson#fromJson(String, Type)}.
     *
     * @param <T>     the type of the desired object
     * @param hocon   the hocon string
     * @param name    the name of hocon object
     * @param typeOfT The specific genericized type of {@code src}
     * @return an object of type T
     * @throws JsonIOException     if there was a problem reading from hocon string
     * @throws JsonSyntaxException if hocon string is not a valid representation for an object of type
     */
    public <T> T fromHocon(String hocon, String name, Type typeOfT) throws JsonSyntaxException {
        ConfigValue config;
        try {
            config = ConfigFactory.parseString(hocon).resolve(resolveOptions).getValue(name);
        } catch (Exception e) {
            throw new JsonSyntaxException(e);
        }

        return fromHocon(config, typeOfT);
    }

    /**
     * Create generic object from HOCON representation with specific {@code name}.<br/>
     * Analog of {@link Gson#fromJson(String, Class)}.
     *
     * @param <T>      the type of the desired object
     * @param hocon    the hocon string
     * @param name     the name of hocon object
     * @param classOfT the class of T
     * @return an object of type T
     * @throws JsonIOException     if there was a problem reading from hocon string
     * @throws JsonSyntaxException if hocon string is not a valid representation for an object of type
     */
    public <T> T fromHocon(String hocon, String name, Class<T> classOfT) throws JsonSyntaxException {
        T object = fromHocon(hocon, name, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    private static Object safeObject(Object obj) {
        return obj != null ? obj : JsonNull.INSTANCE;
    }
}
