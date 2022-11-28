package baguchan.enchantwithmob.mobenchant;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;

public class ThornEnchant extends MobEnchant {
	public ThornEnchant(Properties properties) {
		super(properties);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + 20 * (enchantmentLevel - 1);
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public boolean isCompatibleMob(LivingEntity livingEntity) {
		return !(livingEntity instanceof Guardian);
	}

	/*@Override
	protected boolean canApplyTogether(MobEnchant ench) {
		return super.canApplyTogether(ench) && ench != MobEnchants.DEFLECT;
	}*/
}
