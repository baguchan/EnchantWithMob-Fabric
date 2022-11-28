package baguchan.enchantwithmob;

import baguchan.enchantwithmob.registry.MobEnchants;
import baguchan.enchantwithmob.registry.ModCreativeModeTabs;
import baguchan.enchantwithmob.registry.ModItems;
import baguchan.enchantwithmob.registry.ModLootItemFunctions;
import baguchan.enchantwithmob.registry.ModRegistry;
import baguchan.enchantwithmob.registry.ModTrackedDatas;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantWithMob implements ModInitializer {
	public static final String MODID = "enchantwithmob";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	private static final ResourceLocation DESERT_CHEST = BuiltInLootTables.DESERT_PYRAMID;
	private static final ResourceLocation STRONGHOLD_CHEST = BuiltInLootTables.STRONGHOLD_LIBRARY;
	private static final ResourceLocation WOODLAND_MANSION_CHEST = BuiltInLootTables.WOODLAND_MANSION;


	@Override
	public void onInitialize() {
		LOGGER.info("Hello EnchantWithMob world!");
		ModRegistry.init();
		MobEnchants.init();
		ModLootItemFunctions.init();
		ModItems.init();
		ModTrackedDatas.init();
		ModCreativeModeTabs.initModCreativeModeTabs();
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			// Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
			// We also check that the loot table ID is equal to the ID we want.
			if (source.isBuiltin()) {
				if (id.equals(DESERT_CHEST)) {
					LootPool.Builder poolBuilder = LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(EnchantWithMob.MODID, "inject/mob_enchant_desert")).setWeight(10).setQuality(0));
					tableBuilder.pool(poolBuilder.build());
				}

				if (id.equals(WOODLAND_MANSION_CHEST)) {
					LootPool.Builder poolBuilder = LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(EnchantWithMob.MODID, "inject/mob_enchant_mansion")).setWeight(20).setQuality(0));
					tableBuilder.pool(poolBuilder.build());
				}

				if (id.equals(STRONGHOLD_CHEST)) {
					LootPool.Builder poolBuilder = LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(EnchantWithMob.MODID, "inject/mob_enchant_stronghold")).setWeight(5).setQuality(0));
					tableBuilder.pool(poolBuilder.build());
				}
			}
		});
	}
}
