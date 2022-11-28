package baguchan.enchantwithmob.loot;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import baguchan.enchantwithmob.registry.ModItems;
import baguchan.enchantwithmob.registry.ModLootItemFunctions;
import baguchan.enchantwithmob.registry.ModRegistry;
import baguchan.enchantwithmob.utils.MobEnchantUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MobEnchantRandomlyFunction extends LootItemConditionalFunction {
	private static final Logger LOGGER = LogUtils.getLogger();
	final List<MobEnchant> enchantments;

	public MobEnchantRandomlyFunction(LootItemCondition[] p_80418_, Collection<MobEnchant> p_80419_) {
		super(p_80418_);
		this.enchantments = ImmutableList.copyOf(p_80419_);
	}

	public LootItemFunctionType getType() {
		return ModLootItemFunctions.MOB_ENCHANT_RANDOMLY_FUNCTION;
	}

	public ItemStack run(ItemStack p_80429_, LootContext p_80430_) {
		RandomSource randomsource = p_80430_.getRandom();
		MobEnchant enchantment;
		if (this.enchantments.isEmpty()) {
			boolean flag = p_80429_.is(Items.BOOK) || p_80429_.is(ModItems.MOB_ENCHANT_BOOK);
			List<MobEnchant> list = ModRegistry.MOB_ENCHANT.freeze().stream().filter(MobEnchant::isOnlyChest).filter((p_80436_) -> {
				return flag;
			}).collect(Collectors.toList());
			if (list.isEmpty()) {
				LOGGER.warn("Couldn't find a compatible enchantment for {}", (Object) p_80429_);
				return p_80429_;
			}

			enchantment = list.get(randomsource.nextInt(list.size()));
		} else {
			enchantment = this.enchantments.get(randomsource.nextInt(this.enchantments.size()));
		}

		return enchantItem(p_80429_, enchantment, randomsource);
	}

	private static ItemStack enchantItem(ItemStack p_230980_, MobEnchant p_230981_, RandomSource p_230982_) {
		int i = Mth.nextInt(p_230982_, p_230981_.getMinLevel(), p_230981_.getMaxLevel());
		if (p_230980_.is(Items.BOOK)) {
			p_230980_ = new ItemStack(ModItems.MOB_ENCHANT_BOOK);
			MobEnchantUtils.addMobEnchantToItemStack(p_230980_, p_230981_, i);
		} else {
			MobEnchantUtils.addMobEnchantToItemStack(p_230980_, p_230981_, i);
		}

		return p_230980_;
	}

	public static Builder randomMobEnchant() {
		return new Builder();
	}

	public static LootItemConditionalFunction.Builder<?> randomApplicableMobEnchant() {
		return simpleBuilder((p_80438_) -> {
			return new MobEnchantRandomlyFunction(p_80438_, ImmutableList.of());
		});
	}

	public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
		private final Set<MobEnchant> enchantments = Sets.newHashSet();

		protected Builder getThis() {
			return this;
		}

		public Builder withMobEnchant(MobEnchant p_80445_) {
			this.enchantments.add(p_80445_);
			return this;
		}

		public LootItemFunction build() {
			return new MobEnchantRandomlyFunction(this.getConditions(), this.enchantments);
		}
	}

	public static class Serializer extends LootItemConditionalFunction.Serializer<MobEnchantRandomlyFunction> {
		public void serialize(JsonObject p_80454_, MobEnchantRandomlyFunction p_80455_, JsonSerializationContext p_80456_) {
			super.serialize(p_80454_, p_80455_, p_80456_);
			if (!p_80455_.enchantments.isEmpty()) {
				JsonArray jsonarray = new JsonArray();

				for (MobEnchant enchantment : p_80455_.enchantments) {
					ResourceLocation resourcelocation = ModRegistry.MOB_ENCHANT.getKey(enchantment);
					if (resourcelocation == null) {
						throw new IllegalArgumentException("Don't know how to serialize enchantment " + enchantment);
					}

					jsonarray.add(new JsonPrimitive(resourcelocation.toString()));
				}

				p_80454_.add("enchantments", jsonarray);
			}

		}

		public MobEnchantRandomlyFunction deserialize(JsonObject p_80450_, JsonDeserializationContext p_80451_, LootItemCondition[] p_80452_) {
			List<MobEnchant> list = Lists.newArrayList();
			if (p_80450_.has("enchantments")) {
				for (JsonElement jsonelement : GsonHelper.getAsJsonArray(p_80450_, "enchantments")) {
					String s = GsonHelper.convertToString(jsonelement, "enchantment");
					MobEnchant enchantment = ModRegistry.MOB_ENCHANT.get(new ResourceLocation(s));
					list.add(enchantment);
				}
			}

			return new MobEnchantRandomlyFunction(p_80452_, list);
		}
	}
}
