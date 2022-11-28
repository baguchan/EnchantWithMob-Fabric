package baguchan.enchantwithmob.registry;

import baguchan.enchantwithmob.EnchantWithMob;
import baguchan.enchantwithmob.item.MobEnchantBookItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public class ModCreativeModeTabs {
	private static final List<ItemStack> generalItems = Stream.of(
			ModItems.MOB_UNENCHANT_BOOK
	).map(ItemStack::new).toList();

	public static final CreativeModeTab TAB_MOB_ENCHANT = FabricItemGroup.builder(new ResourceLocation(EnchantWithMob.MODID, "general"))
			.icon(ModItems.MOB_ENCHANT_BOOK::getDefaultInstance)
			.displayItems((featureFlagSet, output, operatorEnabled) -> {
				output.acceptAll(generalItems);
				MobEnchantBookItem.generateMobEnchantmentBookTypesOnlyMaxLevel(output, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
			})
			.build();

	public static void initModCreativeModeTabs() {
	}
}