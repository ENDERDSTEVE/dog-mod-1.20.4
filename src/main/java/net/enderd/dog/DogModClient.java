package net.enderd.dog;

import net.enderd.dog.entity.ModEntities;
import net.enderd.dog.entity.client.BorzoiModel;
import net.enderd.dog.entity.client.BorzoiRenderer;
import net.enderd.dog.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DogModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BORZOI, BorzoiModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BORZOI, BorzoiRenderer::new);

    }
}
