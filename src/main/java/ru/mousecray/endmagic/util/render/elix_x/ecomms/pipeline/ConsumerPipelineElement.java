package ru.mousecray.endmagic.util.render.elix_x.ecomms.pipeline;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts a single input argument and returns no result. By itself acts as a {@linkplain Consumer}. It can be used as an end of a pipeline (which does not return anything and ends the pipeline).
 * 
 * @author elix_x
 *
 * @param <T>
 *            Consumed input
 */
public interface ConsumerPipelineElement<T> extends PipelineElement<T, Void>, Consumer<T> {

	@Override
	default void accept(T t){
		pipe(t);
	}

}
