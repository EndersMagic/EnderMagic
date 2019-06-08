package ru.mousecray.endmagic.util.elix_x.baked.pipeline.model;

import net.minecraft.util.EnumFacing;
import ru.mousecray.endmagic.util.elix_x.baked.UnpackedBakedQuad;
import ru.mousecray.endmagic.util.elix_x.baked.UnpackedSimpleBakedModel;
import ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.PipelineElement;
import ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.list.ListPipelineElement;

public class ModelQuadsPipeline implements PipelineElement<UnpackedSimpleBakedModel, UnpackedSimpleBakedModel> {

	private final ListPipelineElement<UnpackedBakedQuad, UnpackedBakedQuad> pipeline;

	public ModelQuadsPipeline(ListPipelineElement<UnpackedBakedQuad, UnpackedBakedQuad> pipeline){
		this.pipeline = pipeline;
	}

	@Override
	public UnpackedSimpleBakedModel pipe(UnpackedSimpleBakedModel in){
		in.setGeneralQuads(pipeline.pipe(in.getGeneralQuads()));
		for(EnumFacing facing : EnumFacing.values()) in.setFaceQuads(facing, pipeline.pipe(in.getFaceQuads(facing)));
		return in;
	}

}
