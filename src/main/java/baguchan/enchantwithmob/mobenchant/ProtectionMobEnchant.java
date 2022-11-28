package baguchan.enchantwithmob.mobenchant;

public class ProtectionMobEnchant extends MobEnchant {
	public ProtectionMobEnchant(Properties properties) {
		super(properties);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 1 + (enchantmentLevel - 1) * 10;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 11;
	}
}
