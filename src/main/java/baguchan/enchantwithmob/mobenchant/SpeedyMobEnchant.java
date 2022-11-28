package baguchan.enchantwithmob.mobenchant;


import net.minecraft.world.entity.LivingEntity;

public class SpeedyMobEnchant extends MobEnchant {
	public SpeedyMobEnchant(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(LivingEntity entity, int level) {
		super.tick(entity, level);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + (enchantmentLevel - 1) * 10;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 20;
	}
}
