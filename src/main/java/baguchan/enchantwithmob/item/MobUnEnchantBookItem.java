package baguchan.enchantwithmob.item;

import baguchan.enchantwithmob.api.IEnchantCap;
import baguchan.enchantwithmob.utils.MobEnchantUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MobUnEnchantBookItem extends Item {
	public MobUnEnchantBookItem(Properties group) {
		super(group);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		if (playerIn instanceof IEnchantCap) {
			MobEnchantUtils.removeMobEnchantToEntity(playerIn, ((IEnchantCap) playerIn).getEnchantCap());
		}
		playerIn.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F, 1.0F);

		stack.hurtAndBreak(1, playerIn, (entity) -> entity.broadcastBreakEvent(handIn));

		playerIn.getCooldowns().addCooldown(stack.getItem(), 80);

		return InteractionResultHolder.success(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
		super.appendHoverText(stack, level, tooltip, p_41424_);
		ChatFormatting[] textformatting2 = new ChatFormatting[]{ChatFormatting.DARK_PURPLE};

		tooltip.add(Component.translatable("mobenchant.enchantwithmob.mob_unenchant_book.tooltip").withStyle(textformatting2));
	}

	@Override
	public boolean isFoil(ItemStack p_77636_1_) {
		return true;
	}
}
