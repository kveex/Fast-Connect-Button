package me.kveex.fast_connect_button.platform.fabric;

//? fabric {
import me.kveex.fast_connect_button.FastConnectButton;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

import java.util.Map;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		FastConnectButton.onInitializeClient();
		FabricEvents.init();
	}
}
//?}
