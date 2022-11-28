package baguchan.enchantwithmob.mobenchant;

import baguchan.enchantwithmob.registry.MobEnchants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class PoisonCloudMobEnchant extends MobEnchant {
	public PoisonCloudMobEnchant(Properties properties) {
		super(properties);
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 20;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return 50;
	}

	@Override
	public void tick(LivingEntity entity, int level) {
		super.tick(entity, level);
	}

	@Override
	public boolean isCompatibleMob(LivingEntity livingEntity) {
		return !(livingEntity instanceof Witch) || livingEntity instanceof Player;
	}

	@Override
	protected boolean canApplyTogether(MobEnchant ench) {
		return ench != MobEnchants.POISON && super.canApplyTogether(ench);
	}

	@Override
	public boolean isTresureEnchant() {
		return true;
	}

	public static boolean shooterIsLiving(Projectile projectile) {
		return projectile.getOwner() != null && projectile.getOwner() instanceof LivingEntity;
	}
}
