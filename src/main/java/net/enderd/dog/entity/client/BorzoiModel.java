package net.enderd.dog.entity.client;

import net.enderd.dog.entity.animation.ModAnimations;
import net.enderd.dog.entity.custom.BorzoiEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class BorzoiModel<T extends BorzoiEntity> extends SinglePartEntityModel<T> {
	private final ModelPart Body;
	private final ModelPart head;

	public BorzoiModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.head = Body.getChild("mainbody").getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Body = modelPartData.addChild("Body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData backbody = Body.addChild("backbody", ModelPartBuilder.create().uv(0, 0).cuboid(-2.4516F, -2.6F, -3.8184F, 5.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.0F, 3.0F));

		ModelPartData backrleg = backbody.addChild("backrleg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0516F, 0.0F, -0.9184F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 29).cuboid(-1.0516F, 0.0F, -1.9184F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 4).cuboid(-1.0516F, 0.0F, -2.9184F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.2F, 2.0F, 4.9F));

		ModelPartData backlleg = backbody.addChild("backlleg", ModelPartBuilder.create().uv(30, 37).cuboid(-1.2516F, 0.0F, -1.0184F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
		.uv(8, 1).cuboid(0.7484F, 0.0F, -3.0184F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 15).cuboid(-0.2516F, 0.0F, -2.0184F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.6F, 2.0F, 5.0F));

		ModelPartData tailtop = backbody.addChild("tailtop", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, 5.9F));

		ModelPartData TopFloof_r1 = tailtop.addChild("TopFloof_r1", ModelPartBuilder.create().uv(20, 15).cuboid(-1.5F, -12.0F, -2.3F, 3.0F, 2.0F, 6.0F, new Dilation(0.0F))
		.uv(22, 23).cuboid(-1.5F, -14.0F, -2.3F, 3.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, -8.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData tailbottom = tailtop.addChild("tailbottom", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.0F, 4.1F));

		ModelPartData BottomMain_r1 = tailbottom.addChild("BottomMain_r1", ModelPartBuilder.create().uv(0, 29).cuboid(-1.0F, -9.3F, 8.7F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F))
		.uv(32, 10).cuboid(-1.0F, -7.3F, 8.7F, 2.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 7.0F, -11.1F, -0.1745F, 0.0F, 0.0F));

		ModelPartData mainbody = Body.addChild("mainbody", ModelPartBuilder.create().uv(0, 15).cuboid(-2.9516F, -12.0F, -4.8184F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F))
		.uv(20, 0).cuboid(-2.9516F, -6.0F, -4.8184F, 6.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.1F, 0.0F, 0.0F));

		ModelPartData Neck_r1 = mainbody.addChild("Neck_r1", ModelPartBuilder.create().uv(35, 20).cuboid(-3.5F, -15.0F, -0.7F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0484F, 0.0F, 1.1816F, 0.48F, 0.0F, 0.0F));

		ModelPartData head = mainbody.addChild("head", ModelPartBuilder.create().uv(16, 31).cuboid(-2.4516F, -3.5F, -1.8184F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F))
		.uv(8, 8).cuboid(-3.4516F, -1.6F, -1.2184F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(2.3516F, -1.6F, -1.2184F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(20, 6).cuboid(-1.9484F, 0.2F, -1.1184F, 4.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -13.0F, -5.0F));

		ModelPartData SnootBridge_r1 = head.addChild("SnootBridge_r1", ModelPartBuilder.create().uv(10, 29).cuboid(-2.5F, -17.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0484F, 13.2F, 6.1816F, 0.7418F, 0.0F, 0.0F));

		ModelPartData TopSnoot_r1 = head.addChild("TopSnoot_r1", ModelPartBuilder.create().uv(32, 31).cuboid(-3.0F, -16.7F, -7.0F, 2.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(2.0484F, 13.2F, 6.1816F, 0.3054F, 0.0F, 0.0F));

		ModelPartData LeftBackfloof_r1 = head.addChild("LeftBackfloof_r1", ModelPartBuilder.create().uv(16, 29).cuboid(3.6F, -14.2F, -8.5F, 2.0F, 3.0F, 0.0F, new Dilation(0.0F))
		.uv(30, 10).cuboid(-2.4031F, -14.2F, -8.5F, 2.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.6484F, 13.0F, 6.1816F, -0.192F, 0.0F, 0.0F));

		ModelPartData Nose_r1 = head.addChild("Nose_r1", ModelPartBuilder.create().uv(4, 21).cuboid(-0.4F, -16.9F, -5.3F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 13.0F, 5.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData earl = head.addChild("earl", ModelPartBuilder.create(), ModelTransform.pivot(2.1F, -3.0F, 1.0F));

		ModelPartData EarL_r1 = earl.addChild("EarL_r1", ModelPartBuilder.create().uv(20, 0).cuboid(-9.7F, -14.9F, 0.3F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.2484F, 15.1F, 5.1816F, 0.347F, -0.16F, 0.5915F));

		ModelPartData EarLFloof_r1 = earl.addChild("EarLFloof_r1", ModelPartBuilder.create().uv(0, 21).cuboid(-7.6F, -14.0F, -0.4F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1484F, 15.1F, 5.1816F, 0.2597F, -0.16F, 0.5915F));

		ModelPartData earr = head.addChild("earr", ModelPartBuilder.create(), ModelTransform.pivot(-1.9F, -3.0F, 1.0F));

		ModelPartData EarRFloof_r1 = earr.addChild("EarRFloof_r1", ModelPartBuilder.create().uv(6, 0).cuboid(5.7F, -14.2F, -0.4F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.6484F, 15.1F, 5.1816F, 0.2597F, 0.16F, -0.5915F));

		ModelPartData EarR_r1 = earr.addChild("EarR_r1", ModelPartBuilder.create().uv(20, 3).cuboid(3.4F, -17.3F, 0.7F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.4484F, 15.1F, 5.1816F, 0.347F, 0.16F, -0.5915F));

		ModelPartData bottomjaw = head.addChild("bottomjaw", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -0.5F, -1.7F));

		ModelPartData BottomSnoot_r1 = bottomjaw.addChild("BottomSnoot_r1", ModelPartBuilder.create().uv(0, 37).cuboid(-3.0F, -15.7F, -6.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.0484F, 13.7F, 7.8816F, 0.3054F, 0.0F, 0.0F));

		ModelPartData frontlleg = mainbody.addChild("frontlleg", ModelPartBuilder.create().uv(4, 13).cuboid(0.9484F, 0.0F, 0.9816F, 0.0F, 6.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 39).cuboid(-1.0516F, 0.0F, -1.0184F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.7F, -7.0F, -3.4F));

		ModelPartData FrontrLeg = mainbody.addChild("FrontrLeg", ModelPartBuilder.create().uv(0, 13).cuboid(-0.9516F, 0.0F, 0.9816F, 0.0F, 6.0F, 2.0F, new Dilation(0.0F))
		.uv(38, 37).cuboid(-0.9516F, 0.0F, -1.0184F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.7F, -7.0F, -3.4F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(BorzoiEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart :: resetTransform);
		this.setheadAngles(netHeadYaw, headPitch);

		if(!entity.isInSittingPose()){
			this.animateMovement(ModAnimations.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		}

		this.updateAnimation(entity.sittingAnimationState, ModAnimations.SIT, ageInTicks, 1f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.babyAnimationState, ModAnimations.BABYSCALE, ageInTicks, 1f);

	}


	private void setheadAngles(float headYaw,float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;


	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void animateModel(T entity, float limbAngle, float limbDistance, float tickDelta) {
		super.animateModel(entity, limbAngle, limbDistance, tickDelta);
	}

	@Override
	public ModelPart getPart() {
		return Body;
	}
}