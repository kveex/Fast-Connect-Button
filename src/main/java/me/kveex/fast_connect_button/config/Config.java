package me.kveex.fast_connect_button.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.kveex.fast_connect_button.ConnectButtonPlace;
import me.kveex.fast_connect_button.ConnectButtonPlaceMode;
import me.kveex.fast_connect_button.ConnectButtonType;
import me.kveex.fast_connect_button.FastConnectButton;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.List;

public class Config {
	public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
			.id(ResourceLocation.fromNamespaceAndPath(FastConnectButton.MOD_ID, "config"))
			.serializer(config -> GsonConfigSerializerBuilder.create(config)
					.setPath(FastConnectButton.platform().getConfigFolder().resolve(FastConnectButton.MOD_ID + ".json5"))
					.setJson5(true)
					.build())
			.build();

	public static Config getInstance() {
		return Config.HANDLER.instance();
	}

	@SerialEntry(comment = "Fast connect button placement relative to anchor button")
	public ConnectButtonPlace connectButtonPlace = ConnectButtonPlace.RIGHT;

	@SerialEntry(comment = "Action done by fast connect button with anchor button")
	public ConnectButtonPlaceMode connectButtonPlaceMode = ConnectButtonPlaceMode.PLACE;

	@SerialEntry(comment = "Fast connect button look")
	public ConnectButtonType connectButtonType = ConnectButtonType.TEXT;

	@SerialEntry(comment = "Translation key if anchor button")
	public String anchorKeyTranslationKey = "menu.singleplayer";

	@SerialEntry(comment = "Server address to which button should try to connect")
	public String address = "0";

	@SerialEntry(comment = "Server port")
	public int port = 25565;

	@SerialEntry(comment = "Text written on fast connect button")
	public String buttonText = "Connect!";

	@SerialEntry(comment = "Color of a button text")
	public Color buttonTextColor = Color.WHITE;

	@SerialEntry(comment = "List of translation keys of buttons which will be ignored during place picking")
	public List<String> ignoredButtons = List.of("menu.options", "menu.quit", "title.credits");

	@SerialEntry(comment = "List of translation keys of buttons which can't be replaced by fast connect button")
	public List<String> unReplaceableButtons = List.of("fml.menu.mods", "modmenu.title");

	@SerialEntry(comment = "List of translation keys of buttons which can't be moved by fast connect button")
	public List<String> unMovableButtons = List.of("options.language", "options.accessibility", "gui.friends.open");

	public ServerAddress getServerAddress() {
		return new ServerAddress(address, port);
	}
}
