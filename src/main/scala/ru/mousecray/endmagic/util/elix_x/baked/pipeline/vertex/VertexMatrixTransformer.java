package ru.mousecray.endmagic.util.elix_x.baked.pipeline.vertex;

import ru.mousecray.endmagic.util.elix_x.ecomms.pipeline.PipelineElement;
import ru.mousecray.endmagic.util.elix_x.baked.vertex.DefaultUnpackedVertex;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.util.vector.Matrix4f;


public class VertexMatrixTransformer implements PipelineElement<DefaultUnpackedVertex, DefaultUnpackedVertex> {

	public static VertexMatrixTransformer toFace(EnumFacing facing){
		return new VertexMatrixTransformer(TRSRTransformation.getMatrix(facing));
	}

	private javax.vecmath.Matrix4f matrix;

	public VertexMatrixTransformer(Matrix4f matrix){
		setMatrix(matrix);
	}

	public VertexMatrixTransformer(javax.vecmath.Matrix4f matrix){
		setMatrix(matrix);
	}

	public Matrix4f getMatrix(){
		return TRSRTransformation.toLwjgl(matrix);
	}

	public void setMatrix(Matrix4f matrix){
		this.matrix = TRSRTransformation.toVecmath(matrix);
	}

	public void setMatrix(javax.vecmath.Matrix4f matrix){
		this.matrix = matrix;
	}

	@Override
	public DefaultUnpackedVertex pipe(DefaultUnpackedVertex in){
		matrix.transform(TRSRTransformation.toVecmath(in.getPos()));
		//TODO Normals
		// TRSRTransformation.toVecmath(matrix)
		return in;
	}

}
