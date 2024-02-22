package net.enderd.dog.item;

import net.enderd.dog.DogMod;
import net.enderd.dog.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item BORZOI_SPAWN_EGG = RegisterItem("dog_spawn_egg",
            new SpawnEggItem(ModEntities.BORZOI, 0xEEEEDE, 0x563C33,new FabricItemSettings()));

    private static void addItemsToSpawnEggGroup(FabricItemGroupEntries entries) {
        entries.add(BORZOI_SPAWN_EGG);
    }

    private static Item RegisterItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(DogMod.MOD_ID, name), item);
        return item;
    }

    public static void registerModItems(){
        DogMod.LOGGER.info("Registering Mod Items for " + DogMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ModItems::addItemsToSpawnEggGroup);
    }
}
