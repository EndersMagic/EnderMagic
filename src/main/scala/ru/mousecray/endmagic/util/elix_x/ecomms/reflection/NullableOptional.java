package ru.mousecray.endmagic.util.elix_x.ecomms.reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A container that may contain a certain value, possibly, or not contain one.<br>
 * Similar to {@link Optional}, except that it can either contain a non-null value, contain a null value or not contain one.
 *
 * @param <T>
 * @author Elix_x
 */
public final class NullableOptional<T> {

	private static final NullableOptional EMPTY = new NullableOptional(null);

	public static <T> NullableOptional<T> empty(){
		return EMPTY;
	}

	public static <T> NullableOptional<T> of(@Nullable T t){
		return new NullableOptional<>(Optional.ofNullable(t));
	}

	public static <T> NullableOptional<T> ofNull(){
		return of(null);
	}

	public static <T> NullableOptional<T> ofNullable(@Nullable Optional<T> t){
		return t != null ? of(t.orElse(null)) : empty();
	}

	private final Optional<T> value;

	private NullableOptional(@Nullable Optional<T> value){
		this.value = value;
	}

	/**
	 * If a value is present in this {@code Optional}, returns the value,
	 * otherwise throws {@code NoSuchElementException}.
	 *
	 * @return the value held by this {@code Optional}
	 * @throws NoSuchElementException if there is no value present
	 * @see NullableOptional#isPresent()
	 */
	@Nullable
	public T get(){
		if(value == null) throw new NoSuchElementException("No value present");
		return value.orElse(null);
	}

	/**
	 * If a value is present in this {@code NullableOptional}, returns the value wrapped in an {@link Optional}, otherwise throws {@code NoSuchElementException}.
	 *
	 * @return the value held by this {@code NullableOptional}
	 * @throws NoSuchElementException if there is no value present
	 * @see NullableOptional#isPresent()
	 */
	@Nonnull
	public Optional<T> getOpt(){
		if(value == null) throw new NoSuchElementException("No value present");
		return value;
	}

	/**
	 * Return {@code true} if there is a value present, otherwise {@code false}.
	 *
	 * @return {@code true} if there is a value present, otherwise {@code false}
	 */
	public boolean isPresent(){
		return value != null;
	}

	/**
	 * If a value is present, invoke the specified consumer with the value, otherwise do nothing.
	 *
	 * @param consumer block to be executed if a value is present
	 * @throws NullPointerException if value is present and {@code consumer} is null
	 */
	public void ifPresent(@Nonnull Consumer<? super T> consumer){
		if(value != null) consumer.accept(get());
	}

	/**
	 * If a value is present, invoke the specified consumer with the value wrapped in an {@link Optional}, otherwise do nothing.
	 *
	 * @param consumer block to be executed if a value is present
	 * @throws NullPointerException if value is present and {@code consumer} is null
	 */
	public void ifPresentOpt(@Nonnull Consumer<Optional<? super T>> consumer){
		if(value != null) consumer.accept(getOpt());
	}

	/**
	 * If a value is present, and the value matches the given predicate, return an {@code NullableOptional} describing the value, otherwise return an empty {@code NullableOptional}.
	 *
	 * @param predicate a predicate to apply to the value, if present
	 * @return a {@code NullableOptional} describing the value of this {@code NullableOptional} if a value is present and the value matches the given predicate, otherwise an empty {@code NullableOptional}
	 * @throws NullPointerException if the predicate is null
	 */
	@Nonnull
	public NullableOptional<T> filter(@Nonnull Predicate<? super T> predicate){
		return isPresent() && predicate.test(get()) ? this : empty();
	}

	/**
	 * If a value is present, and the wrapped in an {@link Optional} value matches the given predicate, return an {@code NullableOptional} describing the value, otherwise return an empty {@code NullableOptional}.
	 *
	 * @param predicate a predicate to apply to the value, if present
	 * @return a {@code NullableOptional} describing the value of this {@code NullableOptional} if a value is present and the value wrapped in an {@link Optional} matches the given predicate, otherwise an empty {@code NullableOptional}
	 * @throws NullPointerException if the predicate is null
	 */
	@Nonnull
	public NullableOptional<T> filterOpt(@Nonnull Predicate<Optional<? super T>> predicate){
		return isPresent() && predicate.test(getOpt()) ? this : empty();
	}

	/**
	 * If a value is present, apply the provided mapping function to it, and if the result is non-null, return an {@code NullableOptional} describing the result. Otherwise return an empty {@code NullableOptional}.
	 *
	 * @param <U>    The type of the result of the mapping function
	 * @param mapper a mapping function to apply to the value, if present
	 * @return an {@code NullableOptional} describing the result of applying a mapping
	 * function to the value of this {@code Optional}, if a value is present,
	 * otherwise an empty {@code NullableOptional}
	 * @throws NullPointerException if the mapping function is null
	 * @apiNote This method supports post-processing on optional values, without the need to explicitly check for a return status. See/same as {@link Optional#map(Function)}.
	 */
	@Nonnull
	public <U> NullableOptional<U> map(@Nonnull Function<? super T, Optional<U>> mapper){
		if(!isPresent()) return empty();
		else return NullableOptional.ofNullable(mapper.apply(get()));
	}

	/**
	 * If a value is present, apply the provided {@code NullableOptional}-bearing mapping function to it, return that result, otherwise return an empty {@code NullableOptional}. This method is similar to {@link #map(Function)}, but the provided mapper is one whose result is already a {@code NullableOptional}, and if invoked, {@code flatMap} does not wrap it with an additional {@code NullableOptional}.
	 *
	 * @param <U>    The type parameter to the {@code NullableOptional} returned by
	 * @param mapper a mapping function to apply to the value, if present the mapping function
	 * @return the result of applying an {@code NullableOptional}-bearing mapping function to the value of this {@code Optional}, if a value is present, otherwise an empty {@code Optional}
	 * @throws NullPointerException if the mapping function is null or returns a null result
	 */
	@Nonnull
	public <U> NullableOptional<U> flatMap(@Nonnull Function<? super T, NullableOptional<U>> mapper){
		if(!isPresent()) return empty();
		else return Objects.requireNonNull(mapper.apply(get()));
	}

	/**
	 * Return the value, if present, otherwise return {@code other}.
	 *
	 * @param other the value to be returned if there is no value present, may be null
	 * @return the value, if present, otherwise {@code other}
	 */
	@Nullable
	public T orElse(@Nullable T other){
		return value != null ? get() : other;
	}

	/**
	 * Return the value wrapped in an {@link Optional}, if present, otherwise return {@code other}.
	 *
	 * @param other the value to be returned if there is no value present
	 * @return the value wrapped in an {@link Optional}, if present, otherwise {@code other}
	 */
	@Nonnull
	public Optional<T> orElseOpt(@Nonnull Optional<T> other){
		return value != null ? getOpt() : other;
	}

	/**
	 * Return the value, if present, otherwise invoke {@code other} and return the result of that invocation.
	 *
	 * @param other a {@code Supplier} whose result is returned if no value is present
	 * @return the value if present otherwise the result of {@code other.get()}
	 * @throws NullPointerException if value is not present and {@code other} is null
	 */
	@Nullable
	public T orElseGet(@Nonnull Supplier<? extends T> other){
		return value != null ? get() : other.get();
	}

	/**
	 * Return the value wrapped in an {@link Optional}, if present, otherwise invoke {@code other} and return the result of that invocation.
	 *
	 * @param other a {@code Supplier} whose result is returned if no value is present
	 * @return the value wrapped in an {@link Optional} if present otherwise the result of {@code other.get()}
	 * @throws NullPointerException if value is not present and {@code other} is null
	 */
	@Nonnull
	public Optional<T> orElseGetOpt(@Nonnull Supplier<Optional<? extends T>> other){
		return value != null ? getOpt() : (Optional) other.get();
	}

	/**
	 * Return the contained value, if present, otherwise throw an exception to be created by the provided supplier.
	 *
	 * @param <X>               Type of the exception to be thrown
	 * @param exceptionSupplier The supplier which will return the exception to be thrown
	 * @return the present value
	 * @throws X                    if there is no value present
	 * @throws NullPointerException if no value is present and {@code exceptionSupplier} is null
	 * @apiNote A method reference to the exception constructor with an empty argument list can be used as the supplier. For example, {@code IllegalStateException::new}
	 */
	@Nullable
	public <X extends Throwable> T orElseThrow(@Nonnull Supplier<? extends X> exceptionSupplier) throws X{
		if(value != null) return get();
		else throw exceptionSupplier.get();
	}

	/**
	 * Return the contained value wrapped in an {@link Optional}, if present, otherwise throw an exception to be created by the provided supplier.
	 *
	 * @param <X>               Type of the exception to be thrown
	 * @param exceptionSupplier The supplier which will return the exception to be thrown
	 * @return the present value wrapped in an {@link Optional}
	 * @throws X                    if there is no value present
	 * @throws NullPointerException if no value is present and {@code exceptionSupplier} is null
	 * @apiNote A method reference to the exception constructor with an empty argument list can be used as the supplier. For example, {@code IllegalStateException::new}
	 */
	@Nonnull
	public <X extends Throwable> Optional<T> orElseThrowOpt(@Nonnull Supplier<? extends X> exceptionSupplier) throws X{
		if(value != null) return getOpt();
		else throw exceptionSupplier.get();
	}

	/*
	 * EH2S
	 */

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || !(o instanceof NullableOptional)) return false;
		NullableOptional<?> that = (NullableOptional<?>) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode(){
		return Objects.hash(value);
	}

	/**
	 * Returns a non-empty string representation of this Optional suitable for debugging. The exact presentation format is unspecified and may vary between implementations and versions.
	 *
	 * @return the string representation of this instance
	 * @implSpec If a value is present the result must include its string representation in the result. Empty and present Optionals must be unambiguously differentiable.
	 */
	@Override
	public String toString(){
		return value != null ? String.format("NullableOptional[%s]", get()) : "NullableOptional.empty";
	}

}
