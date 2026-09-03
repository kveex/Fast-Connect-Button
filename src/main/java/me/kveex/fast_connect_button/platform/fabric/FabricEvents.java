package me.kveex.fast_connect_button.platform.fabric;


//? if fabric {
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.resources.ResourceLocation;
import me.kveex.fast_connect_button.FastConnectButton;
import me.kveex.fast_connect_button.event.ScreenInitEventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;

import java.util.List;
import java.util.Optional;

public class FabricEvents {
	public static void init() {
		ResourceLocation lowestPhase = ResourceLocation.fromNamespaceAndPath(FastConnectButton.MOD_ID, "lowest");
		ScreenEvents.AFTER_INIT.addPhaseOrdering(Event.DEFAULT_PHASE, lowestPhase);

		ScreenEvents.AFTER_INIT.register(lowestPhase, (client, screen, width, height) -> {
			//? if < 26.1 {
			List<AbstractWidget> buttons = Screens.getButtons(screen);
			//? } else {
			/*List<AbstractWidget> buttons = Screens.getWidgets(screen);
			*///? }
			Optional<Button> button = ScreenInitEventHandler.onTitleScreenPostInit(screen, buttons);

			if (ScreenInitEventHandler.LISTENER_FOR_REMOVE != null) {
				buttons.remove((AbstractWidget) ScreenInitEventHandler.LISTENER_FOR_REMOVE);
			}

			button.ifPresent(buttons::add);
		});
	}
}
//? }
