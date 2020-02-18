package ru.mousecray.endmagic.util.render.elix_x.ecomms.reflection;

import com.google.common.primitives.Primitives;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionHelper {

	private static final Field fieldConstructorModifiers;
	private static final Field fieldFieldModifiers;
	private static final Field fieldMethodModifiers;

	static{
		try{
			fieldConstructorModifiers = Constructor.class.getDeclaredField("modifiers");
			fieldConstructorModifiers.setAccessible(true);
			fieldFieldModifiers = Field.class.getDeclaredField("modifiers");
			fieldFieldModifiers.setAccessible(true);
			fieldMethodModifiers = Method.class.getDeclaredField("modifiers");
			fieldMethodModifiers.setAccessible(true);
		} catch(Exception e){
			throw new IllegalArgumentException("Could not initialize reflection framework - required internals not found!");
		}
	}

	public static Optional<Field> findField(Class<?> claz, String... names){
		Class<?> clas = claz;
		while(claz != null){
			for(String name : names) try{
				return Optional.of(clas.getDeclaredField(name));
			} catch(Exception ee){}
			clas = claz.getSuperclass();
		}
		return Optional.empty();
	}

	public static Optional<Method> findMethod(Class<?> claz, String[] names, Class<?>... args){
		Class<?> clas = claz;
		while(claz != null){
			for(String name : names) try{
				return Optional.of(clas.getDeclaredMethod(name, args));
			} catch(Exception ee){}
			clas = claz.getSuperclass();
		}
		return Optional.empty();
	}

	public static Class<?> wrapIfPrimitive(Class<?> c){
		return c.isPrimitive() ? Primitives.wrap(c) : c;
	}

	public static boolean canArgApply(Class<?> param, Class<?> arg){
		return arg == null ? !param.isPrimitive() : wrapIfPrimitive(param).isAssignableFrom(wrapIfPrimitive(arg));
	}

	public static boolean canArgApply(Class<?> param, Object arg){
		return arg == null ? !param.isPrimitive() : wrapIfPrimitive(param).isInstance(arg);
	}

	public static boolean allArgsApplicable(Class[] params, Class[] args){
		if(params.length != args.length) return false;
		for(int a = 0; a < params.length; a++) if(!canArgApply(params[a], args[a])) return false;
		return true;
	}

	public static boolean allArgsApplicable(Class[] params, Object[] args){
		if(params.length != args.length) return false;
		for(int a = 0; a < params.length; a++) if(!canArgApply(params[a], args[a])) return false;
		return true;
	}

	private static final Supplier<IllegalArgumentException> MODIFIERSEXC = () -> new IllegalArgumentException("Could not initialize modifiers manipulation framework - required internals not found!");
	private static final Supplier<IllegalArgumentException> ENUMEXC = () -> new IllegalArgumentException("Could not initialize enum manipulation framework - required internals not found!");

	public enum Modifier {

		PUBLIC, PRIVATE, PROTECTED, STATIC, FINAL, SYNCHRONIZED, VOLATILE, TRANSIENT, NATIVE, INTERFACE, ABSTRACT, STRICT, BRIDGE, VARARGS, SYNTHETIC, ANNOTATION, ENUM, MANDATED;

		final int modifier;

		Modifier(){
			modifier = new AClass<>(java.lang.reflect.Modifier.class).<Integer>getDeclaredField(this.name()).orElseThrow(MODIFIERSEXC).setAccessible(true).get(null).orElseThrow(MODIFIERSEXC);
		}

		private boolean is(int original){
			return (original & modifier) != 0;
		}

		private int set(int original, boolean on){
			if(on) return original | modifier;
			else return original & (~modifier);
		}

	}

	public static class AClass<C> {

		public static <C> Optional<AClass<C>> find(String... names){
			for(String name : names) try {
				return (Optional) Optional.of(Class.forName(name)).map(AClass::new);
			} catch(ClassNotFoundException e){}
			return Optional.empty();
		}

		protected final Class<C> clas;

		public AClass(Class<C> clas){
			this.clas = clas;
		}

		public Class<C> get(){
			return clas;
		}

		public Optional<AClass<? super C>> getSuperclass(){
			return Optional.ofNullable(clas.getSuperclass()).map(AClass::new);
		}

		public boolean isInterface(){
			return clas.isInterface();
		}

		public AInterface<C> asInterface(){
			return new AInterface<>(clas);
		}

		public boolean isEnum(){
			return clas.isEnum();
		}

		public <E extends Enum<E>> AEnum<E> asEnum(){
			return new AEnum<>((Class<E>) clas);
		}

		public boolean isAnnotation(){
			return clas.isAnnotation();
		}

		public AAnnotation<C> asAnnotation(){
			return new AAnnotation<>(clas);
		}

		public Optional<AConstructor<C>> getDeclaredConstructor(Class<?>... args){
			try{
				return Optional.of(new AConstructor<C>(this, clas.getDeclaredConstructor(args)));
			} catch(ReflectiveOperationException e){}
			return Optional.empty();
		}

		public Stream<AConstructor<C>> getDeclaredConstructors(){
			return Arrays.stream(clas.getDeclaredConstructors()).map(constructor -> new AConstructor<C>(this, (Constructor) constructor));
		}

		public Stream<AConstructor<C>> findConstructorsForArgs(Class<?>... args){
			return getDeclaredConstructors().filter(ac -> allArgsApplicable(ac.get().getParameterTypes(), args));
		}

		public Stream<AConstructor<C>> findConstructorsForArgs(Object... args){
			return getDeclaredConstructors().filter(ac -> allArgsApplicable(ac.get().getParameterTypes(), args));
		}

		public <T> Optional<AField<C, T>> getDeclaredField(String... names){
			return findField(clas, names).map(f -> new AField<C, T>(this, f));
		}

		public <T> Stream<AField<C, T>> getDeclaredFields(){
			return Arrays.stream(clas.getDeclaredFields()).map(f -> new AField<C, T>(this, f));
		}

		public <T> Stream<AField<? super C, T>> getFields(){
			MutableObject<Stream> stream = new MutableObject<Stream>(getDeclaredFields());
			getSuperclass().ifPresent(clas -> stream.setValue(Stream.concat(stream.getValue(), clas.getFields())));
			return stream.getValue();
		}

		public <T> Optional<AMethod<C, T>> getDeclaredMethod(String[] names, Class<?>... args){
			return findMethod(clas, names, args).map(m -> new AMethod<C, T>(this, m));
		}

		public <T> Stream<AMethod<C, T>> getDeclaredMethods(){
			return Arrays.stream(clas.getDeclaredMethods()).map(m -> new AMethod<C, T>(this, m));
		}

		public <T> Stream<AMethod<C, T>> findDeclaredMethodsForArgs(Class<?>... args){
			return this.<T>getDeclaredMethods().filter(am -> allArgsApplicable(am.get().getParameterTypes(), args));
		}

		public <T> Stream<AMethod<C, T>> findDeclaredMethodsForArgs(Object... args){
			return this.<T>getDeclaredMethods().filter(am -> allArgsApplicable(am.get().getParameterTypes(), args));
		}

		public <T> Stream<AMethod<? super C, T>> getMethods(){
			MutableObject<Stream> stream = new MutableObject<Stream>(getDeclaredMethods());
			getSuperclass().ifPresent(clas -> stream.setValue(Stream.concat(stream.getValue(), clas.getMethods())));
			return stream.getValue();
		}

		public <T> Stream<AMethod<? super C, T>> findMethodsForArgs(Object... args){
			return this.<T>getMethods().filter(am -> allArgsApplicable(am.get().getParameterTypes(), args));
		}

		public static class AInterface<C> extends AClass<C> {

			private AInterface(Class<C> clas){
				super(clas);
			}

			public C proxy(InvocationHandler handler, AInterface<?>... interfaces){
				return proxy(clas.getClassLoader(), handler, interfaces);
			}

			public C proxy(ClassLoader loader, InvocationHandler handler, AInterface<?>... interfaces){
				return (C) Proxy.newProxyInstance(loader, Stream.concat(Stream.of(this), Arrays.stream(interfaces)).map(iface -> iface.clas).toArray(Class[]::new), handler);
			}

		}

		public static class AEnum<C extends Enum<C>> extends AClass<C> {

			private static final AClass<?> reflectionFactory = AClass.find("sun.reflect.ReflectionFactory").orElseThrow(ENUMEXC);
			private static final AMethod<?, ?> getReflectionFactory = reflectionFactory.getDeclaredMethod(new String[]{"getReflectionFactory"}).orElseThrow(ENUMEXC).setAccessible(true);
			private static final AMethod newConstructorAccessor = reflectionFactory.getDeclaredMethod(new String[]{"newConstructorAccessor"}, Constructor.class).orElseThrow(ENUMEXC).setAccessible(true);
			private static final AClass<?> constructorAccessor = AClass.find("sun.reflect.ConstructorAccessor").orElseThrow(ENUMEXC);
			private static final AMethod newInstance = constructorAccessor.getDeclaredMethod(new String[]{"newInstance"}, Object[].class).orElseThrow(ENUMEXC).setAccessible(true);

			private final AField<C, C[]> VALUES;
			private final Object factory;

			private AEnum(Class<C> clas){
				super(clas);
				VALUES = this.<C[]>getDeclaredField("$VALUES", "ENUM$VALUES").orElseThrow(() -> new IllegalArgumentException(String.format("Could not initialize enum (%s) reflector - VALUES[] not found!", clas.getName()))).setAccessible(true).setFinal(false);
				factory = getReflectionFactory.invoke(null).orElseThrow(() -> new IllegalArgumentException(String.format("Could not initialize enum (%s) reflector - could not get ReflectionFactory!", clas.getName())));
			}

			public C[] enums(){
				return clas.getEnumConstants();
			}

			public C getEnum(int ordinal){
				return enums()[ordinal];
			}

			public C getEnum(String name){
				return Enum.valueOf(clas, name);
			}

			public Optional<EnumCreator> findEnumCreatorForArgs(Class<?>... args){
				return findConstructorsForArgs(ArrayUtils.addAll(new Class[]{String.class, int.class}, args)).findAny().map(EnumCreator::new);
			}

			public Optional<EnumCreator> findEnumCreatorForArgs(Object... args){
				return findConstructorsForArgs(ArrayUtils.addAll(new Object[]{"", 0}, args)).findAny().map(EnumCreator::new);
			}

			public Optional<C> createEnum(String name, Object... args){
				return findEnumCreatorForArgs(args).flatMap(creator -> creator.create(name, args));
			}

			public Optional<C> addEnum(String name, Object... args){
				return findEnumCreatorForArgs(args).flatMap(creator -> creator.add(name, args));
			}

			public void removeEnum(C c){
				VALUES.set(null, ArrayUtils.removeElement(enums(), c));
			}

			public class EnumCreator {

				private final AConstructor<C> constructor;

				private EnumCreator(AConstructor<C> constructor){
					this.constructor = constructor;
				}

				public Optional<C> create(String name, Object... args){
					return newConstructorAccessor.invoke(factory, constructor.get()).orElseOpt(Optional.empty()).flatMap(ca -> newInstance.invoke(ca, (Object) ArrayUtils.addAll(new Object[]{name, enums().length}, args)).orElseOpt(Optional.empty()));
				}

				public Optional<C> add(String name, Object... args){
					return create(name, args).map(c -> {
						VALUES.set(null, ArrayUtils.add(clas.getEnumConstants(), c));
						return c;
					});
				}

			}

		}

		public static class AAnnotation<C> extends AClass<C> {

			private AAnnotation(Class<C> clas){
				super(clas);
			}

		}

	}

	private static abstract class ReflectionObject<C, T, R extends ReflectionObject<C, T, R>> {

		private final AClass<C> clas;
		private final T t;

		private ReflectionObject(AClass<C> clas, T t){
			this.clas = clas;
			this.t = t;
		}

		public AClass<C> clas(){
			return clas;
		}

		public final T get(){
			return t;
		}

		public List<Modifier> modifiers(){
			return Arrays.stream(Modifier.values()).filter(this::is).collect(Collectors.toList());
		}

		public abstract boolean is(Modifier modifier);

		public abstract R set(Modifier modifier, boolean on);

	}

	private static abstract class AccessibleReflectionObject<C, T extends AccessibleObject, R extends AccessibleReflectionObject<C, T, R>> extends ReflectionObject<C, T, R> {

		private AccessibleReflectionObject(AClass<C> clas, T t){
			super(clas, t);
		}

		public boolean isAccessible(){
			return get().isAccessible();
		}

		public R setAccessible(boolean accessible){
			get().setAccessible(accessible);
			return (R) this;
		}

	}

	public static class AConstructor<C> extends AccessibleReflectionObject<C, Constructor<C>, AConstructor<C>> {

		private AConstructor(AClass<C> clas, Constructor<C> constructor){
			super(clas, constructor);
		}

		@Override
		public boolean is(Modifier modifier){
			return modifier.is(get().getModifiers());
		}

		@Override
		public AConstructor<C> set(Modifier modifier, boolean on){
			try{
				fieldConstructorModifiers.setInt(get(), modifier.set(get().getModifiers(), on));
			} catch(Exception e){
				throw new RuntimeException(e);
			}
			return this;
		}

		public Optional<C> newInstance(Object... args){
			try{
				return Optional.of(get().newInstance(args));
			} catch(Exception e){}
			return Optional.empty();
		}

	}

	public static class AField<C, T> extends AccessibleReflectionObject<C, Field, AField<C, T>> {

		private AField(AClass<C> clas, Field field){
			super(clas, field);
		}

		@Override
		public boolean is(Modifier modifier){
			return modifier.is(get().getModifiers());
		}

		@Override
		public AField<C, T> set(Modifier modifier, boolean on){
			try{
				fieldFieldModifiers.setInt(get(), modifier.set(get().getModifiers(), on));
			} catch(Exception e){
				throw new RuntimeException(e);
			}
			return this;
		}

		public AField<C, T> setFinal(boolean finall){
			return set(Modifier.FINAL, finall);
		}

		public <I extends C> NullableOptional<T> get(I instance){
			try{
				return NullableOptional.of((T) get().get(instance));
			} catch(Exception e){}
			return NullableOptional.empty();
		}

		public <I extends C> boolean set(I instance, Object t){
			try{
				get().set(instance, t);
				return true;
			} catch(Exception e){}
			return false;
		}

		public <I extends C> Mutable<T> asMutable(I instance){
			return new Mutable<T>() {

				@Override
				public T getValue(){
					return get(instance).orElseThrow(() -> new IllegalArgumentException(String.format("Could not get field (%s) value!", get())));
				}

				@Override
				public void setValue(T value){
					if(!set(instance, value)) throw new IllegalArgumentException(String.format("Could not set field (%s) value (to %s)!", get(), value));
				}

			};
		}

	}

	public static class AMethod<C, T> extends AccessibleReflectionObject<C, Method, AMethod<C, T>> {

		private AMethod(AClass<C> clas, Method method){
			super(clas, method);
		}

		@Override
		public boolean is(Modifier modifier){
			return modifier.is(get().getModifiers());
		}

		@Override
		public AMethod<C, T> set(Modifier modifier, boolean on){
			try{
				fieldMethodModifiers.setInt(get(), modifier.set(get().getModifiers(), on));
			} catch(Exception e){
				throw new RuntimeException(e);
			}
			return this;
		}

		public <I extends C> NullableOptional<T> invoke(I instance, Object... args){
			try{
				return NullableOptional.of((T) get().invoke(instance, args));
			} catch(Exception e){}
			return NullableOptional.empty();
		}

	}

}
