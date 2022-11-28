package baguchan.enchantwithmob.capability;


import baguchan.enchantwithmob.mobenchant.MobEnchant;
import baguchan.enchantwithmob.registry.ModRegistry;
import baguchan.enchantwithmob.utils.MobEnchantUtils;
import net.minecraft.nbt.CompoundTag;

public class MobEnchantHandler {
	private MobEnchant mobEnchant;
	private int enchantLevel;

	public MobEnchantHandler(MobEnchant mobEnchant, int enchantLevel) {
		this.mobEnchant = mobEnchant;
		this.enchantLevel = enchantLevel;
	}


	public MobEnchant getMobEnchant() {
		return mobEnchant;
	}

	public int getEnchantLevel() {
		return enchantLevel;
	}

	public CompoundTag writeNBT() {
		CompoundTag nbt = new CompoundTag();

		if (mobEnchant != null) {
			nbt.putString("MobEnchant", ModRegistry.MOB_ENCHANT.getKey(mobEnchant).toString());
			nbt.putInt("EnchantLevel", enchantLevel);
		}

		return nbt;
	}

	public void readNBT(CompoundTag nbt) {
		mobEnchant = MobEnchantUtils.getEnchantFromNBT(nbt);
		enchantLevel = MobEnchantUtils.getEnchantLevelFromNBT(nbt);
	}
}
