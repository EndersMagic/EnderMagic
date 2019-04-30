package ru.mousecray.endmagic.util.elix_x.baked.pipeline.quad;


import ru.mousecray.endmagic.util.elix_x.baked.UnpackedBakedQuad;
import ru.mousecray.endmagic.util.elix_x.baked.vertex.DefaultUnpackedVertices;
import ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.PipelineElement;

public class QuadVerticesPipeline implements PipelineElement<UnpackedBakedQuad, UnpackedBakedQuad> {

	private final PipelineElement<DefaultUnpackedVertices, DefaultUnpackedVertices> pipeline;

	public QuadVerticesPipeline(PipelineElement<DefaultUnpackedVertices, DefaultUnpackedVertices> pipeline){
		this.pipeline = pipeline;
	}

	@Override
	public UnpackedBakedQuad pipe(UnpackedBakedQuad in){
		in.setVertices(pipeline.pipe(in.getVertices()));
		return in;
	}

}
