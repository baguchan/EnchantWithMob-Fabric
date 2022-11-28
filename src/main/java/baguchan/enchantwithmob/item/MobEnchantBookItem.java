package baguchan.enchantwithmob.item;

import baguchan.enchantwithmob.api.IEnchantCap;
import baguchan.enchantwithmob.mobenchant.MobEnchant;
import baguchan.enchantwithmob.registry.ModRegistry;
import baguchan.enchantwithmob.utils.MobEnchantUtils;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class MobEnchantBookItem extends Item {
	public MobEnchantBookItem(Properties group) {
		super(group);
	}


	/*
	 * Implemented onRightClick (method) inside CommonEventHandler instead of this method
	 */
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
		if (MobEnchantUtils.hasMobEnchant(stack)) {
			final boolean[] flag = {false};
			if (entity instanceof IEnchantCap) {
				flag[0] = MobEnchantUtils.addItemMobEnchantToEntity(stack, entity, ((IEnchantCap) entity).getEnchantCap());
			}

			//When flag is true, enchanting is success.
			if (flag[0]) {
				user.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F, 1.0F);

				stack.hurtAndBreak(1, user, (entity2) -> entity2.broadcastBreakEvent(hand));

				user.getCooldowns().addCooldown(stack.getItem(), 40);

				return InteractionResult.SUCCESS;
			} else {
				user.displayClientMessage(Component.translatable("enchantwithmob.cannot.enchant"), true);

				user.getCooldowns().addCooldown(stack.getItem(), 20);

				return InteractionResult.FAIL;
			}
		}
		return super.interactLivingEntity(stack, user, entity, hand);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		if (MobEnchantUtils.hasMobEnchant(stack)) {
			final boolean[] flag = {false};
			if (playerIn instanceof IEnchantCap) {
				flag[0] = MobEnchantUtils.addItemMobEnchantToEntity(stack, playerIn, ((IEnchantCap) playerIn).getEnchantCap());
			}

			//When flag is true, enchanting is success.
			if (flag[0]) {
				playerIn.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F, 1.0F);

				stack.hurtAndBreak(1, playerIn, (entity) -> entity.broadcastBreakEvent(handIn));

				playerIn.getCooldowns().addCooldown(stack.getItem(), 40);

				return InteractionResultHolder.success(stack);
			} else {
				playerIn.displayClientMessage(Component.translatable("enchantwithmob.cannot.enchant_yourself"), true);

				playerIn.getCooldowns().addCooldown(stack.getItem(), 20);

				return InteractionResultHolder.fail(stack);
			}
		}
		return super.use(level, playerIn, handIn);
	}

	@Override
	public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
		if (this.allowedIn(p_41391_)) {
			for (MobEnchant enchant : ModRegistry.MOB_ENCHANT.stream().toList()) {
				ItemStack stack = new ItemStack(this);
				MobEnchantUtils.addMobEnchantToItemStack(stack, enchant, enchant.getMaxLevel());
				p_41392_.add(stack);
			}
		}
	}

	public static ListTag getEnchantmentList(ItemStack stack) {
		CompoundTag compoundnbt = stack.getTag();
		return compoundnbt != null ? compoundnbt.getList(MobEnchantUtils.TAG_STORED_MOBENCHANTS, 10) : new ListTag();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
		super.appendHoverText(stack, level, tooltip, p_41424_);
		ChatFormatting[] textformatting2 = new ChatFormatting[]{ChatFormatting.DARK_PURPLE};

		tooltip.add(Component.translatable("mobenchant.enchantwithmob.mob_enchant_book.tooltip").withStyle(textformatting2));
		if (MobEnchantUtils.hasMobEnchant(stack)) {
			ListTag listnbt = MobEnchantUtils.getEnchantmentListForNBT(stack.getTag());

			for (int i = 0; i < listnbt.size(); ++i) {
				CompoundTag compoundnbt = listnbt.getCompound(i);

				MobEnchant mobEnchant = MobEnchantUtils.getEnchantFromNBT(compoundnbt);
				int enchantmentLevel = MobEnchantUtils.getEnchantLevelFromNBT(compoundnbt);

				if (mobEnchant != null) {
					ChatFormatting[] textformatting = new ChatFormatting[]{ChatFormatting.AQUA};

					ResourceLocation resourceLocation = ModRegistry.MOB_ENCHANT.getKey(mobEnchant);


					tooltip.add(Component.translatable("mobenchant." + resourceLocation.getNamespace() + "." + resourceLocation.getPath()).withStyle(textformatting).append(" ").append(Component.translatable("enchantment.level." + enchantmentLevel).withStyle(textformatting)));
				}
			}

			List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();

			for (int i = 0; i < listnbt.size(); ++i) {
				CompoundTag compoundnbt = listnbt.getCompound(i);

				MobEnchant mobEnchant = MobEnchantUtils.getEnchantFromNBT(compoundnbt);
				int mobEnchantLevel = MobEnchantUtils.getEnchantLevelFromNBT(compoundnbt);

				if (mobEnchant != null) {
					Map<Attribute, AttributeModifier> map = mobEnchant.getAttributeModifierMap();
					if (!map.isEmpty()) {
						for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
							AttributeModifier attributemodifier = entry.getValue();
							AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), mobEnchant.getAttributeModifierAmount(mobEnchantLevel, attributemodifier), attributemodifier.getOperation());
							list1.add(new Pair<>(entry.getKey(), attributemodifier1));
						}
					}
				}
			}


			if (!list1.isEmpty()) {
				//tooltip.add(StringTextComponent.EMPTY);
				tooltip.add((Component.translatable("mobenchant.enchantwithmob.when_ehcnanted")).withStyle(ChatFormatting.DARK_PURPLE));

				for (Pair<Attribute, AttributeModifier> pair : list1) {
					AttributeModifier attributemodifier2 = pair.getSecond();
					double d0 = attributemodifier2.getAmount();
					double d1;
					if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
						d1 = attributemodifier2.getAmount();
					} else {
						d1 = attributemodifier2.getAmount() * 100.0D;
					}

					if (d0 > 0.0D) {
						tooltip.add((Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
					} else if (d0 < 0.0D) {
						d1 = d1 * -1.0D;
						tooltip.add((Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
					}
				}
			}
		}
	}

	@Override
	public boolean isFoil(ItemStack p_77636_1_) {
		return true;
	}
}
