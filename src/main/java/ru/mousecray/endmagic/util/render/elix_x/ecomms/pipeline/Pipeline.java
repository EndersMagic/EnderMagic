package ru.mousecray.endmagic.util.render.elix_x.ecomms.pipeline;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

/**
 * Pipeline consists of multiple {@linkplain PipelineElement}s. All of the elements are arranged in fixed user defined order, more specifically, the input of the pipeline is piped through the first element, the result is given to the next one and so on until the end of pipeline is reached and output of the last element is returned. <br>
 * <br>
 * Pipeline is a {@linkplain PipelineElement} itself, so pipelines can be stacked.
 * 
 * @author elix_x
 *
 * @param <I>
 *            Input
 * @param <O>
 *            Output
 */
public class Pipeline<I, O> implements PipelineElement<I, O> {

	private final ImmutableList<PipelineElement<?, ?>> elements;

	public Pipeline(PipelineElement<?, ?>... elements){
		this.elements = ImmutableList.copyOf(elements);
	}

	public Pipeline(Collection<PipelineElement<?, ?>> elements){
		this.elements = ImmutableList.copyOf(elements);
	}

	@SuppressWarnings("unchecked")
	@Override
	public O pipe(I in){
		Object o = in;
		for(PipelineElement<?, ?> element : elements){
			o = ((PipelineElement<Object, Object>) element).pipe(o);
		}
		return (O) o;
	}

}
