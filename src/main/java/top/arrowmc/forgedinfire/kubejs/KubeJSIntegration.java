package top.arrowmc.forgedinfire.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.Extra;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class KubeJSIntegration extends KubeJSPlugin {

    public static final EventGroup EVENT_GROUP = EventGroup.of("ForgedInFireEvents");
    public static final EventHandler FORGING_ANVIL_CRAFTED_EVENT = EVENT_GROUP.server("onForgingAnvilCrafted",() -> ForgingAnvilCraftedEventJS.class).extra(Extra.STRING);
    public static final EventHandler FORGING_ANVIL_CRAFTING_EVENT = EVENT_GROUP.server("onForgingAnvilCrafting",() -> ForgingAnvilCraftingEventJS.class).extra(Extra.STRING);

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
    }

    @Override
    public void registerEvents() {
        EVENT_GROUP.register();
    }
}
