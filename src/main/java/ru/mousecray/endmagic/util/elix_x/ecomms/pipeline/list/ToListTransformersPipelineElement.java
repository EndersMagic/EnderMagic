package ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.list;

import java.util.List;

import com.google.common.collect.Lists;
import ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.PipelineElement;


/**
 * Transforms an iter[] of elements into list of elements.
 * 
 * @author elix_x
 */
public class ToListTransformersPipelineElement {

	/**
	 * Transforms an iterable of elements into list of elements. If the provided iterable is a list, it is returned.
	 * 
	 * @author elix_x
	 *
	 * @param <T>
	 *            Elements of iterable
	 * @param <I>
	 *            Iterable itself
	 */
	public static class Iterable<T, I extends java.lang.Iterable<T>> implements PipelineElement<I, List<T>> {

		@Override
		public List<T> pipe(I in){
			return in instanceof List ? (List<T>) in : Lists.newArrayList(in);
		}

	}

	/**
	 * Transforms an iterator of elements into list of elements.
	 * 
	 * @author elix_x
	 *
	 * @param <T>
	 *            Elements of iterator
	 * @param <I>
	 *            Iterator itself
	 */
	public static class Iterator<T, I extends java.util.Iterator<T>> implements PipelineElement<I, List<T>> {

		@Override
		public List<T> pipe(I in){
			return Lists.newArrayList(in);
		}

	}

}
