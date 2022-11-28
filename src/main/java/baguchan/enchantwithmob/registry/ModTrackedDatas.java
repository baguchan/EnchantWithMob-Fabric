package baguchan.enchantwithmob.registry;

import baguchan.enchantwithmob.capability.MobEnchantCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class ModTrackedDatas {
	public static final EntityDataSerializer<MobEnchantCapability> MOB_ENCHANT_CAPABILITY = new EntityDataSerializer<MobEnchantCapability>() {
		public void write(FriendlyByteBuf packetByteBuf, MobEnchantCapability nbtCompound) {
			packetByteBuf.writeNbt(nbtCompound.serializeNBT());
		}

		public MobEnchantCapability read(FriendlyByteBuf packetByteBuf) {
			MobEnchantCapability mobEnchantCapability = new MobEnchantCapability();
			mobEnchantCapability.deserializeNBT(packetByteBuf.readNbt());
			return mobEnchantCapability;
		}

		public MobEnchantCapability copy(MobEnchantCapability mobEnchantCapability) {
			return mobEnchantCapability;
		}
	};

	public static void init() {
		EntityDataSerializers.registerSerializer(MOB_ENCHANT_CAPABILITY);
	}
}
