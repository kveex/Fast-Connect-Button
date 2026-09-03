package me.kveex.fast_connect_button.platform.neoforge;

//? neoforge {

/*import me.kveex.fast_connect_button.FastConnectButton;
import me.kveex.fast_connect_button.config.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = FastConnectButton.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeClientEntrypoint {
	public NeoForgeClientEntrypoint(ModContainer container) {
		FastConnectButton.onInitializeClient();
		container.registerExtensionPoint(IConfigScreenFactory.class, (gui, parent) -> ConfigScreen.createConfigScreen(parent));
	}
}
*///?}
