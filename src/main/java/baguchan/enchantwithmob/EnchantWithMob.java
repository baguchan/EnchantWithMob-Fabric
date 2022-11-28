package baguchan.enchantwithmob;

import baguchan.enchantwithmob.registry.MobEnchants;
import baguchan.enchantwithmob.registry.ModItems;
import baguchan.enchantwithmob.registry.ModLootItemFunctions;
import baguchan.enchantwithmob.registry.ModRegistry;
import baguchan.enchantwithmob.registry.ModTrackedDatas;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantWithMob implements ModInitializer {
	public static final String MODID = "enchantwithmob";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello EnchantWithMob world!");
		ModRegistry.init();
		MobEnchants.init();
		ModLootItemFunctions.init();
		ModItems.init();
		ModTrackedDatas.init();
	}
}
