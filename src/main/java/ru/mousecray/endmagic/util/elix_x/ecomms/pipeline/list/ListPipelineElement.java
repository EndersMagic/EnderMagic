package ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.PipelineElement;

/**
 * This is a more advanced element of a pipeline. It allows to pipe multiple elements through at once.
 * 
 * @author elix_x
 *
 * @param <I>
 *            Input(s)
 * @param <O>
 *            Output(s)
 */
public interface ListPipelineElement<I, O> extends PipelineElement<List<I>, List<O>>{}
