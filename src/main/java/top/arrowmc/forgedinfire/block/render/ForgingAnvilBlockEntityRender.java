package top.arrowmc.forgedinfire.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import top.arrowmc.forgedinfire.block.entity.ForgingAnvilBlockEntity;

public class ForgingAnvilBlockEntityRender implements BlockEntityRenderer<ForgingAnvilBlockEntity> {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ForgingAnvilBlockEntityRender(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(ForgingAnvilBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        NonNullList<ItemStack> list = pBlockEntity.getItemStacks();
        int bonus = 3;
        switch (pBlockEntity.getBlockState().getValue(FACING)){
            case EAST -> bonus = 0;
            case SOUTH -> bonus = 1;
            case WEST -> bonus = 2;
        }
        for (int i = 0; i < 2; ++i) {
            ItemStack itemStack = list.get(i);
            if (itemStack.isEmpty()) {
                continue;
            }
            pPoseStack.pushPose();
            switch (bonus){
                case 0 ->  pPoseStack.translate(0.5f, 0.95f + 0.02f*i, 0.7f);//East
                case 1 -> pPoseStack.translate(0.3f, 0.95f + 0.02f*i, 0.5f);//South
                case 2 -> pPoseStack.translate(0.5f, 0.95f + 0.02f*i, 0.3f);//West
                case 3 -> pPoseStack.translate(0.7f, 0.95f + 0.02f*i, 0.5f);//North
            }//bonus与前面FACING有关
            pPoseStack.scale(0.4f, 0.4f, 0.4f);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

            pPoseStack.mulPose(Axis.ZN.rotationDegrees(90 * bonus));

            itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
            pPoseStack.popPose();
        }
        ItemStack itemStack = list.get(2);
        if (itemStack.isEmpty()) {
            return;
        }
        pPoseStack.pushPose();
        switch (bonus){
            case 0 ->  pPoseStack.translate(0.7f, 0.95f, 0.3f);//East
            case 1 -> pPoseStack.translate(0.7f, 0.95f, 0.7f);//South
            case 2 -> pPoseStack.translate(0.3f, 0.95f, 0.7f);//West
            case 3 -> pPoseStack.translate(0.3f, 0.95f, 0.3f);//North
        }//bonus与前面FACING有关
        //pPoseStack.translate(0.7f, 0.95f, 0.2f);
        pPoseStack.scale(0.4f, 0.4f, 0.4f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));
        pPoseStack.mulPose(Axis.ZN.rotationDegrees(90 * bonus));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

}
