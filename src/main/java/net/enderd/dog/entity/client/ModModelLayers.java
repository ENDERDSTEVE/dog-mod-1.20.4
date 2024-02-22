package net.enderd.dog.entity.client;

import net.enderd.dog.DogMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer BORZOI =
            new EntityModelLayer(new Identifier(DogMod.MOD_ID,"borzoi"),"main");
}
