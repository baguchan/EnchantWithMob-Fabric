package baguchan.enchantwithmob.mobenchant;

public class HealthBoostMobEnchant extends MobEnchant {
	public HealthBoostMobEnchant(Properties properties) {
		super(properties);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 15 + (enchantmentLevel - 1) * 10;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public boolean isTresureEnchant() {
		return true;
	}
}
