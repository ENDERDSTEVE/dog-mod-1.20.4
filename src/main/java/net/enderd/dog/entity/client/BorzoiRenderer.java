package net.enderd.dog.entity.client;

import net.enderd.dog.DogMod;
import net.enderd.dog.entity.custom.BorzoiEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BorzoiRenderer extends MobEntityRenderer<BorzoiEntity,BorzoiModel<BorzoiEntity>> {
    private static final Identifier TEXTURE = new Identifier(DogMod.MOD_ID, "textures/entity/borzoi/borzoi.png");

    public BorzoiRenderer(EntityRendererFactory.Context context) {
        super(context, new BorzoiModel<>(context.getPart(ModModelLayers.BORZOI)), 0.5f);
    }

    @Override
    public Identifier getTexture(BorzoiEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BorzoiEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
