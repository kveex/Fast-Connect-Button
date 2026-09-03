package me.kveex.fast_connect_button;

import me.kveex.fast_connect_button.config.Config;
import me.kveex.fast_connect_button.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import me.kveex.fast_connect_button.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.kveex.fast_connect_button.platform.neoforge.NeoForgePlatform;
*///?}

public class FastConnectButton {
	public static boolean FAST_CONNECT_BUTTON_USED = false;
	public static final String MOD_ID = /*$ mod_id*/ "fast_connect_button";
	public static final Logger log = LoggerFactory.getLogger("FastConnectButton");
	public static final String SERVER_ADDRESS_REGEX = "^[\\S.]+";
	public static final String SERVER_PORT_REGEX = "\\d{1,5}";
	private static final int FAST_CONNECT_TEXT_BUTTON_WIDTH = 50;
	public static final int FAST_CONNECT_SPRITE_BUTTON_WIDTH = 20;
	private static final int FAST_CONNECT_BUTTON_HEIGHT = 20;

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitializeClient() {
		Config.HANDLER.load();
	}

	public static Platform platform() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoForgePlatform();
		*///?}
	}

	public static Button createButton(Screen screen) {
		return Config.getInstance().connectButtonType.equals(ConnectButtonType.TEXT)
				? createTextButton(screen)
				: createIconButton(screen);
	}

	private static Button createTextButton(Screen screen) {
		Config config = Config.getInstance();
		return Button.builder(Component.literal(config.buttonText).withColor(config.buttonTextColor.getRGB()), b -> join(screen))
				.size(FAST_CONNECT_TEXT_BUTTON_WIDTH, FAST_CONNECT_BUTTON_HEIGHT)
				.build();
	}

	private static SpriteIconButton createIconButton(Screen screen) {
		Config config = Config.getInstance();
		return SpriteIconButton.builder(Component.literal(config.buttonText).withColor(config.buttonTextColor.getRGB()), b -> join(screen), true)
				.sprite(ResourceLocation.fromNamespaceAndPath(MOD_ID, "icon/connect"), 15, 15)
				.size(FAST_CONNECT_SPRITE_BUTTON_WIDTH, FAST_CONNECT_BUTTON_HEIGHT)
				.build();
	}

	private static void join(Screen screen) {
		FAST_CONNECT_BUTTON_USED = true;
		ServerAddress serverAddress = Config.getInstance().getServerAddress();
		ServerData serverData = new ServerData(I18n.get("selectServer.defaultName"), serverAddress.getHost(), ServerData.Type.OTHER);
		ConnectScreen.startConnecting(screen, Minecraft.getInstance(), serverAddress, serverData, false, null);
	}
}
