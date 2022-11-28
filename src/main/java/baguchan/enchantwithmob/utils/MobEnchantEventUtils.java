package baguchan.enchantwithmob.utils;

import baguchan.enchantwithmob.api.IEnchantCap;
import baguchan.enchantwithmob.capability.MobEnchantCapability;
import baguchan.enchantwithmob.registry.MobEnchants;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;

public class MobEnchantEventUtils {
	public static float getDamageAddition(float damage, MobEnchantCapability cap) {
		int level = MobEnchantUtils.getMobEnchantLevelFromHandler(cap.getMobEnchants(), MobEnchants.STRONG);
		if (level > 0) {
			damage += 1.0F + (float) Math.max(0, level - 1) * 1.0F;
		}
		return damage;
	}

	public static float getDamageReduction(float damage, MobEnchantCapability cap) {
		int i = MobEnchantUtils.getMobEnchantLevelFromHandler(cap.getMobEnchants(), MobEnchants.PROTECTION);
		if (i > 0) {
			damage -= (double) Mth.floor(damage * (double) ((float) i * 0.15F));
		}
		return damage;
	}

	public static float extraDamage(LivingEntity entity, DamageSource source, float amount) {
		if (source.getEntity() instanceof IEnchantCap cap) {

			if (source.getEntity() instanceof LivingEntity attacker) {
				if (source.getEntity() != null && source.getEntity() instanceof SnowGolem && amount == 0) {
					amount = MobEnchantEventUtils.getDamageAddition(1, cap.getEnchantCap());
				} else if (amount > 0) {
					amount = MobEnchantEventUtils.getDamageAddition(amount, cap.getEnchantCap());
				}

				if (cap.getEnchantCap().hasEnchant() && MobEnchantUtils.findMobEnchantFromHandler(cap.getEnchantCap().getMobEnchants(), MobEnchants.POISON)) {
					int i = MobEnchantUtils.getMobEnchantLevelFromHandler(cap.getEnchantCap().getMobEnchants(), MobEnchants.POISON);

					if (amount > 0) {
						if (attacker.getRandom().nextFloat() < i * 0.125F) {
							entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * i, 0), attacker);
						}
					}
				}
			}
		}
		if (entity instanceof IEnchantCap cap) {

			if (source != DamageSource.STARVE && cap.getEnchantCap().hasEnchant() && MobEnchantUtils.findMobEnchantFromHandler(cap.getEnchantCap().getMobEnchants(), MobEnchants.PROTECTION)) {
				amount = getDamageReduction(amount, cap.getEnchantCap());
			}

			if (cap.getEnchantCap().hasEnchant()) {
				amount = getBonusMobEnchantDamageReduction(amount, cap.getEnchantCap());
			}
		}
		return amount;
	}

	public static float getBonusMobEnchantDamageReduction(float damage, MobEnchantCapability cap) {
		int i = cap.getMobEnchants().size();
		if (i > 0) {
			damage -= (double) Mth.floor(damage * (double) ((float) i * 0.05F));
		}
		return damage;
	}
}
