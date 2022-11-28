package baguchan.enchantwithmob.client.render.layer;

import baguchan.enchantwithmob.EnchantWithMob;
import baguchan.enchantwithmob.api.IEnchantCap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class EnchantLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
	public static final ResourceLocation ANCIENT_GLINT = new ResourceLocation(EnchantWithMob.MODID, "textures/entity/ancient_glint.png");

	public EnchantLayer(RenderLayerParent<T, M> p_i50947_1_) {
		super(p_i50947_1_);
	}

	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		float tick = (float) entitylivingbaseIn.tickCount + partialTicks;
		if (entitylivingbaseIn instanceof IEnchantCap) {
			IEnchantCap cap = (IEnchantCap) entitylivingbaseIn;
			if (cap.getEnchantCap().hasEnchant() && !entitylivingbaseIn.isInvisible()) {
				float f = (float) entitylivingbaseIn.tickCount + partialTicks;
				EntityModel<T> entitymodel = this.getParentModel();
				entitymodel.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
				this.getParentModel().copyPropertiesTo(entitymodel);
				VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityGlint());
				entitymodel.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				entitymodel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
			}
		}
	}
}
