package top.arrowmc.forgedinfire.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import top.arrowmc.forgedinfire.block.entity.DisplayStandBlockEntity;

public class DisplayStandBlockEntityRender implements BlockEntityRenderer<DisplayStandBlockEntity> {

    public DisplayStandBlockEntityRender(BlockEntityRendererProvider.Context context) {
    }


    @Override
    public void render(DisplayStandBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getDisplayStack();
        if (!itemStack.isEmpty()) {

            pPoseStack.pushPose();
            CompoundTag compoundTag = itemStack.getOrCreateTag();
            int age = compoundTag.contains("age") ? compoundTag.getInt("age") : 0;
            double y = Mth.sin((float)(age) * 0.318319274F / 5.0F) / 20.0f;

            pPoseStack.translate(0.5D, 1.3625D + y, 0.5D);
            pPoseStack.mulPose(Axis.YP.rotation((float)age / 900.0F * Mth.TWO_PI));

            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
            pPoseStack.popPose();

        }

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
