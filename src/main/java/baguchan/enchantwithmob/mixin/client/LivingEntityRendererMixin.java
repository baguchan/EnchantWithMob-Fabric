package baguchan.enchantwithmob.mixin.client;

import baguchan.enchantwithmob.client.render.layer.EnchantLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
	@Shadow
	@Final
	protected List<RenderLayer<T, M>> layers;

	protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@Shadow
	@Final
	protected boolean addLayer(RenderLayer<T, M> feature) {
		return false;
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void onInit(EntityRendererProvider.Context context, M entityModel, float f, CallbackInfo ci) {
		addLayer(new EnchantLayer<>(this));
	}

}
