package ru.mousecray.endmagic.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelAltar extends ModelBase {
	
	public ModelRenderer 
	Shape1 = new ModelRenderer(this, 0, 0).setTextureSize(64, 32),
    Shape2 = new ModelRenderer(this, 8, 17).setTextureSize(64, 32),
    Shape3 = new ModelRenderer(this, 48, 0).setTextureSize(64, 32),
    Shape4 = new ModelRenderer(this, 48, 0).setTextureSize(64, 32),
    Shape5 = new ModelRenderer(this, 48, 0).setTextureSize(64, 32),
    Shape6 = new ModelRenderer(this, 48, 0).setTextureSize(64, 32),
    Shape7 = new ModelRenderer(this, 0, 17).setTextureSize(64, 32);
  
	public ModelAltar() {
		
		Shape1.addBox(0F, 0F, 0F, 12, 5, 12);
		Shape1.setRotationPoint(-6F, 3F, -6F);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		
		Shape2.addBox(0F, 0F, 0F, 6, 3, 6);
		Shape2.setRotationPoint(-3F, 0F, -3F);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		
      	Shape3.addBox(0F, -2F, -7F, 1, 8, 1);
      	Shape3.setRotationPoint(-0.5F, -8F, -1F);
      	Shape3.mirror = true;
      	setRotation(Shape3, 0.6981317F, 0F, 0F);
      	
      	Shape4.addBox(0F, -2F, 6F, 1, 8, 1);
      	Shape4.setRotationPoint(-0.5F, -8F, 1F);
      	Shape4.mirror = true;
      	setRotation(Shape4, -0.6981317F, 0F, 0F);
      	
      	Shape5.addBox(6F, -2F, 0F, 1, 8, 1);
      	Shape5.setRotationPoint(1F, -8F, -0.5F);
      	Shape5.mirror = true;
      	setRotation(Shape5, 0F, 0F, 0.6981317F);
      	
      	Shape6.addBox(-7F, -2F, 0F, 1, 8, 1);
      	Shape6.setRotationPoint(-1F, -8F, -0.5F);
      	Shape6.mirror = true;
      	setRotation(Shape6, 0F, 0F, -0.6981317F);
      	
      	Shape7.addBox(0F, 0F, 0F, 2, 2, 2);
      	Shape7.setRotationPoint(-1F, -6F, -1F);
      	Shape7.mirror = true;
      	setRotation(Shape7, 0F, 0F, 0F);
	}
  
	public void renderAll() {
    	Shape1.render(0.0625F);
    	Shape2.render(0.0625F);
    	Shape3.render(0.0625F);
    	Shape4.render(0.0625F);
    	Shape5.render(0.0625F);
    	Shape6.render(0.0625F);
    	Shape7.render(0.0625F);
	}
  
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}