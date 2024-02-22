package net.enderd.dog;

import net.enderd.dog.entity.ModEntities;
import net.enderd.dog.entity.custom.BorzoiEntity;
import net.enderd.dog.item.ModItems;
import net.enderd.dog.world.gen.ModEntityGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DogMod implements ModInitializer {
	public static final String MOD_ID = "dog-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModEntityGeneration.addSpawns();
		ModEntities.registerModEntities();

		FabricDefaultAttributeRegistry.register(ModEntities.BORZOI, BorzoiEntity.createBorzoiAttributes());
	}
}