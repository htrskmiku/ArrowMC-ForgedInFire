package top.arrowmc.forgedinfire.ponder;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import top.arrowmc.forgedinfire.ForgedInFire;
import top.arrowmc.forgedinfire.ModRegister;

public class ForgingAnvilPonder {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(ModRegister.FORGING_ANVIL_ITEM.getId())
                .addStoryBoard(ResourceLocation.fromNamespaceAndPath(ForgedInFire.MODID,"forging_anvil"),ForgingAnvilPonder::forgingAnvil);
    }

    public static void forgingAnvil(SceneBuilder sceneBuilder, SceneBuildingUtil sceneBuildingUtil) {
        sceneBuilder.title("forging_anvil_direction", "Forged In Fire");
        sceneBuilder.configureBasePlate(0,0,3);
        sceneBuilder.world().showSection(sceneBuildingUtil.select().everywhere(), Direction.UP);
        sceneBuilder.idleSeconds(1);
        BlockPos anvilPos = sceneBuildingUtil.grid().at(1,1,1);
        sceneBuilder.overlay().showText(80)
                .text("text1")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(90);

        sceneBuilder.overlay().showText(80)
                .text("text2")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(90);

        sceneBuilder.overlay()
                .showControls(sceneBuildingUtil.vector().blockSurface(sceneBuildingUtil.grid().at(1, 1, 1), Direction.NORTH), Pointing.RIGHT, 40)
                .rightClick().withItem(ModRegister.FORGING_BASE_MATERIAL.get().getDefaultInstance());
        sceneBuilder.idle(70);

        sceneBuilder.overlay().showText(60)
                .text("text3")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(70);

        sceneBuilder.overlay().showText(130)
                .text("text4")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));

        sceneBuilder.overlay()
                .showControls(sceneBuildingUtil.vector().blockSurface(sceneBuildingUtil.grid().at(1, 1, 1), Direction.NORTH), Pointing.RIGHT, 20)
                .rightClick().withItem(Items.IRON_INGOT.getDefaultInstance());
        sceneBuilder.idle(30);
        sceneBuilder.overlay()
                .showControls(sceneBuildingUtil.vector().blockSurface(sceneBuildingUtil.grid().at(1, 1, 1), Direction.NORTH), Pointing.RIGHT, 20)
                .rightClick().withItem(Items.DIAMOND.getDefaultInstance());
        sceneBuilder.idle(30);
        sceneBuilder.overlay()
                .showControls(sceneBuildingUtil.vector().blockSurface(sceneBuildingUtil.grid().at(1, 1, 1), Direction.NORTH), Pointing.RIGHT, 20)
                .rightClick().withItem(Items.GOLD_INGOT.getDefaultInstance());
        sceneBuilder.idle(30);
        sceneBuilder.overlay()
                .showControls(sceneBuildingUtil.vector().blockSurface(sceneBuildingUtil.grid().at(1, 1, 1), Direction.NORTH), Pointing.RIGHT, 20)
                .rightClick().withItem(Items.NETHERITE_INGOT.getDefaultInstance());

        sceneBuilder.idle(90);

        sceneBuilder.overlay().showText(80)
                .text("text5")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(90);


        sceneBuilder.overlay()
                .showControls(sceneBuildingUtil.vector().blockSurface(sceneBuildingUtil.grid().at(1, 1, 1), Direction.NORTH), Pointing.RIGHT, 40)
                .rightClick().withItem(ModRegister.FORGING_HAMMER.get().getDefaultInstance());
        sceneBuilder.idle(90);

        sceneBuilder.overlay().showText(80)
                .text("text6")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(90);

        sceneBuilder.overlay().showText(110)
                .text("text7")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(130);

        sceneBuilder.overlay()
                .showControls(sceneBuildingUtil.vector().blockSurface(sceneBuildingUtil.grid().at(1, 1, 1), Direction.NORTH), Pointing.RIGHT, 80)
                .withItem(ModRegister.FORGING_SCRAP.get().getDefaultInstance());

        sceneBuilder.overlay().showText(80)
                .text("text8")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(100);


        sceneBuilder.overlay().showText(80)
                .text("text9")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sceneBuildingUtil.vector().topOf(anvilPos));
        sceneBuilder.idle(90);

        sceneBuilder.markAsFinished();
    }
}
