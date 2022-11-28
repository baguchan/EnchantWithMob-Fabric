package baguchan.enchantwithmob.mobenchant;

import baguchan.enchantwithmob.registry.ModRegistry;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Map;
import java.util.UUID;

public class MobEnchant {
	private final Map<Attribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
	protected final Rarity enchantType;
	private final int level;
	private int minlevel = 1;

	public MobEnchant(Properties properties) {
		this.enchantType = properties.enchantType;
		this.level = properties.level;
	}

	public Rarity getRarity() {
		return enchantType;
	}

	public MobEnchant setMinLevel(int level) {
		this.minlevel = level;

		return this;
	}


	/**
	 * Returns the minimum level that the enchantment can have.
	 */
	public int getMinLevel() {
		return minlevel;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return level;
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 1 + enchantmentLevel * 10;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 5;
	}


	public void tick(LivingEntity entity, int level) {

	}

	public final boolean isCompatibleWith(MobEnchant enchantmentIn) {
		return this.canApplyTogether(enchantmentIn) && enchantmentIn.canApplyTogether(this);
	}

	public boolean isTresureEnchant() {
		return false;
	}

	public boolean isOnlyChest() {
		return false;
	}

	public boolean isCompatibleMob(LivingEntity livingEntity) {
		return true;
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this enchantment.
	 */
	protected boolean canApplyTogether(MobEnchant ench) {
		return this != ench;
	}

	public MobEnchant addAttributesModifier(Attribute attributeIn, String uuid, double amount, AttributeModifier.Operation operation) {
		AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), Util.makeDescriptionId("mobenchant", ModRegistry.MOB_ENCHANT.getKey(this)), amount, operation);
		this.attributeModifierMap.put(attributeIn, attributemodifier);
		return this;
	}

	public Map<Attribute, AttributeModifier> getAttributeModifierMap() {
		return this.attributeModifierMap;
	}

	public void removeAttributesModifiersFromEntity(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn) {
		for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			AttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
			if (modifiableattributeinstance != null) {
				modifiableattributeinstance.removeModifier(entry.getValue());
			}
		}

	}

	public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn, int amplifier) {
		for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
			AttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
			if (modifiableattributeinstance != null) {
				AttributeModifier attributemodifier = entry.getValue();
				modifiableattributeinstance.removeModifier(attributemodifier);
				modifiableattributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), ModRegistry.MOB_ENCHANT.getKey(this).toString() + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
			}
		}
	}

	public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
		return modifier.getAmount() * (double) (amplifier);
	}

	public boolean isDisabled() {
		return false;
	}


	public static class Properties {
		private final Rarity enchantType;
		private final int level;

		public Properties(Rarity enchantType, int level) {
			this.enchantType = enchantType;
			this.level = level;
		}
	}

	public static enum Rarity {
		COMMON(10),
		UNCOMMON(5),
		RARE(2),
		VERY_RARE(1);

		private final int weight;

		private Rarity(int rarityWeight) {
			this.weight = rarityWeight;
		}

		/**
		 * Retrieves the weight of Rarity.
		 */
		public int getWeight() {
			return this.weight;
		}
	}
}