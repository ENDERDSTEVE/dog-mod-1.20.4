package net.enderd.dog.entity;

import net.enderd.dog.DogMod;
import net.enderd.dog.entity.custom.BorzoiEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BorzoiEntity> BORZOI = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(DogMod.MOD_ID,"borzoi"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,BorzoiEntity::new)
                    .dimensions(EntityDimensions.fixed(0.7f,0.7f)).build());
    public static void registerModEntities() {
        DogMod.LOGGER.info("Registering Entities for " + DogMod.MOD_ID);
    }
}
