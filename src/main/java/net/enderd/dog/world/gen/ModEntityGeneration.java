package net.enderd.dog.world.gen;

import net.enderd.dog.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntityGeneration {
    public static void  addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.CHERRY_GROVE, BiomeKeys.BIRCH_FOREST, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_PLAINS), SpawnGroup.CREATURE,
                ModEntities.BORZOI, 5, 2, 5);

        SpawnRestriction.register(ModEntities.BORZOI, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
    }
}
