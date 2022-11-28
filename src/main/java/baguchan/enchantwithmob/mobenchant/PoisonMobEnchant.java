package baguchan.enchantwithmob.mobenchant;

import baguchan.enchantwithmob.registry.MobEnchants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.CaveSpider;

public class PoisonMobEnchant extends MobEnchant {
	public PoisonMobEnchant(Properties properties) {
		super(properties);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 5 + (enchantmentLevel - 1) * 10;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 30;
	}

	@Override
	public boolean isCompatibleMob(LivingEntity livingEntity) {
		return !(livingEntity instanceof Bee) && !(livingEntity instanceof CaveSpider);
	}

	@Override
	protected boolean canApplyTogether(MobEnchant ench) {
		return ench != MobEnchants.POISON_CLOUD && super.canApplyTogether(ench);
	}
}
