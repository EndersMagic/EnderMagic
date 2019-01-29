package ru.mousecray.endmagic.util.elix_x.ecomms.pipeline;

import java.util.function.Supplier;

/**
 * Represents a supplier of results that can be used in a pipeline. By itself acts as a {@linkplain Supplier}. It can be used as a fixed beginning of a pipeline (whose output does not change based on input).
 * 
 * @author elix_x
 *
 * @param <T>
 *            Supplied output
 */
public interface SupplierPipelineElement<T> extends PipelineElement<Object, T>, Supplier<T> {

	@Override
	default T get(){
		return pipe(null);
	}
}
