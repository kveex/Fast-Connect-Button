package me.kveex.fast_connect_button.platform.neoforge;
//? neoforge {
/*import me.kveex.fast_connect_button.FastConnectButton;
import me.kveex.fast_connect_button.event.ScreenInitEventHandler;
import net.minecraft.client.gui.components.Button;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.util.Optional;

@EventBusSubscriber(modid = FastConnectButton.MOD_ID)
public class NeoForgeEvents {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onScreenPostInit(ScreenEvent.Init.Post event) {
		Optional<Button> button = ScreenInitEventHandler.onTitleScreenPostInit(event.getScreen(), event.getListenersList());

		if (ScreenInitEventHandler.LISTENER_FOR_REMOVE != null) {
			event.removeListener(ScreenInitEventHandler.LISTENER_FOR_REMOVE);
		}

		button.ifPresent(event::addListener);
	}
}
*///?}
