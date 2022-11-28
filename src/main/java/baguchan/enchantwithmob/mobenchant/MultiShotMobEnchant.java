package baguchan.enchantwithmob.mobenchant;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;

public class MultiShotMobEnchant extends MobEnchant {
	private static boolean isAdding = false;

	public MultiShotMobEnchant(Properties properties) {
		super(properties);
	}

	/*@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		Level level = event.getLevel();
		if (entity instanceof Projectile) {
			Projectile projectile = (Projectile) entity;
			if (!shooterIsLiving(projectile) || EnchantConfig.COMMON.DISABLE_MULTISHOT_PROJECTILE.get().contains(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()))
				return;
			LivingEntity owner = (LivingEntity) projectile.getOwner();

			MobEnchantUtils.executeIfPresent(owner, MobEnchants.MULTISHOT.get(), () -> {
				if (!level.isClientSide && projectile.tickCount == 0 && !isAdding) {
					isAdding = true;
					CompoundTag compoundNBT = new CompoundTag();
					compoundNBT = projectile.saveWithoutId(compoundNBT);
					addProjectile(projectile, compoundNBT, level, 15.0F);
					addProjectile(projectile, compoundNBT, level, -15.0F);
					isAdding = false;
				}
			});
		}
	}*/

	/*private static void addProjectile(Projectile projectile, CompoundTag compoundNBT, Level level, float rotation) {
		Projectile newProjectile = (Projectile) projectile.getType().create(level);
		UUID uuid = newProjectile.getUUID();
		newProjectile.load(compoundNBT);
		newProjectile.setUUID(uuid);
		Vec3 vector3d = newProjectile.getDeltaMovement().yRot((float) (Math.PI / rotation));

		newProjectile.setDeltaMovement(vector3d);
		float f = Mth.sqrt((float) vector3d.horizontalDistanceSqr());
		newProjectile.setYRot((float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
		newProjectile.setXRot((float) (Mth.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI)));
		newProjectile.yRotO = newProjectile.getYRot();
		newProjectile.xRotO = newProjectile.getXRot();
		if (newProjectile instanceof Projectile) {
			Projectile newDamagingProjectile = (Projectile) newProjectile;
			Vec3 newPower = new Vec3(newDamagingProjectile.getDeltaMovement().x, newDamagingProjectile.getDeltaMovement().y, newDamagingProjectile.getDeltaMovement().z).yRot((float) (Math.PI / rotation));

			newDamagingProjectile.setDeltaMovement(newPower);
		}

		if (newProjectile instanceof AbstractArrow) {
			((AbstractArrow) newProjectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		}

		newProjectile.getCapability(EnchantWithMob.ITEM_MOB_ENCHANT_CAP).ifPresent(cap ->
		{
			cap.setHasEnchant(true);
		});

		level.addFreshEntity(newProjectile);
	}

	@SubscribeEvent
	public static void onHit(ProjectileImpactEvent event) {
		Projectile projectile = event.getProjectile();

		projectile.getCapability(EnchantWithMob.ITEM_MOB_ENCHANT_CAP).ifPresent(cap -> {
			if (cap.hasEnchant() && !(projectile instanceof AbstractArrow)) {
				projectile.discard();
			}
		});
	}*/

	public static boolean shooterIsLiving(Projectile projectile) {
		return projectile.getOwner() != null && projectile.getOwner() instanceof LivingEntity;
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 10;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 40;
	}

	/*@Override
	public boolean isCompatibleMob(LivingEntity livingEntity) {
		return EnchantConfig.COMMON.WHITELIST_SHOOT_ENTITY.get().contains(ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType()).toString()) || livingEntity instanceof Player;
	}*/
}
