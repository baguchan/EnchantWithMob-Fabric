package baguchan.enchantwithmob.registry;

import baguchan.enchantwithmob.EnchantWithMob;
import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModRegistry {
	public static final Registry<MobEnchant> MOB_ENCHANT;

	static {
		MOB_ENCHANT = FabricRegistryBuilder.createDefaulted(MobEnchant.class, new ResourceLocation(EnchantWithMob.MODID, "mob_enchant"), new ResourceLocation(EnchantWithMob.MODID, "protect")).buildAndRegister();
	}

	public static void init() {

	}
}
