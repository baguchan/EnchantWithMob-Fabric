package baguchan.enchantwithmob.registry;

import baguchan.enchantwithmob.EnchantWithMob;
import baguchan.enchantwithmob.item.MobEnchantBookItem;
import baguchan.enchantwithmob.item.MobUnEnchantBookItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final Item MOB_ENCHANT_BOOK = new MobEnchantBookItem((new Item.Properties()).stacksTo(1).durability(5).tab(CreativeModeTab.TAB_MISC));
	public static final Item MOB_UNENCHANT_BOOK = new MobUnEnchantBookItem((new Item.Properties()).stacksTo(1).durability(5).tab(CreativeModeTab.TAB_MISC));


	public static void init() {
		Registry.register(Registry.ITEM, new ResourceLocation(EnchantWithMob.MODID, "mob_enchant_book"), MOB_ENCHANT_BOOK);
		Registry.register(Registry.ITEM, new ResourceLocation(EnchantWithMob.MODID, "mob_unenchant_book"), MOB_UNENCHANT_BOOK);
	}
}
