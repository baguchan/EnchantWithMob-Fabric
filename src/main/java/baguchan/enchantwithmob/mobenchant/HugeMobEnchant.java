package baguchan.enchantwithmob.mobenchant;

public class HugeMobEnchant extends MobEnchant {
	public HugeMobEnchant(Properties properties) {
		super(properties);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 20 + (enchantmentLevel - 1) * 10;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 20;
	}

	@Override
	public boolean isTresureEnchant() {
		return true;
	}

    /*@Override
    public boolean isCompatibleMob(LivingEntity livingEntity) {
        return !(livingEntity instanceof Player) || EnchantConfig.COMMON.bigYourSelf.get();
    }*/
}
