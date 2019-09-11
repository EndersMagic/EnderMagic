package ru.mousecray.endmagic.util.render.elix_x.ecomms.pipeline.list;

import ru.mousecray.endmagic.util.render.elix_x.ecomms.pipeline.PipelineElement;

import java.util.List;
import java.util.stream.Collector;


/**
 * A mutable reduction operation that accumulates input elements into a mutable result container, optionally transforming the accumulated result into a final representation after all input elements have been processed. By itself it is <i>similar</i> to {@linkplain Collector}, but cannot be used as one. Along with default collector purposes, it can also be used to terminate the list pipeline (piping multiple elements at once) and return to the basic pipeline (piping single elements).
 * 
 * @author elix_x
 *
 * @param <I>
 * @param <O>
 */
public interface CollectorPipelineElement<I, O> extends PipelineElement<List<I>, O> {

}
