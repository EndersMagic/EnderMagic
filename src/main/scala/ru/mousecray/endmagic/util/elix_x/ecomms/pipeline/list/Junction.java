package ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.list;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.PipelineElement;


/**
 * A junction of multiple {@linkplain PipelineElement}s. All of the elements are arranged in fixed user defined order, more specifically, the input of the junction is piped through each and every element separately and the results are collected into the list in the same order in which the elements are defined. <br>
 * <br>
 * Note: Junction is a {@linkplain PipelineElement} itself, so junctions can be stacked.
 * 
 * @author elix_x
 *
 * @param <I>
 *            Input
 * @param <O>
 *            Output
 */
public class Junction<I, O> implements PipelineElement<I, List<O>> {

	private final ImmutableList<PipelineElement<?, ?>> elements;

	public Junction(PipelineElement<?, ?>... elements){
		this.elements = ImmutableList.copyOf(elements);
	}

	public Junction(Collection<PipelineElement<?, ?>> elements){
		this.elements = ImmutableList.copyOf(elements);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<O> pipe(I in){
		return elements.stream().map(element -> (O) ((PipelineElement<Object, Object>) element).pipe(in)).collect(Collectors.toList());
	}

}
