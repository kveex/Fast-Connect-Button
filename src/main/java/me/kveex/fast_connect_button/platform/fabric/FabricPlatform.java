package me.kveex.fast_connect_button.platform.fabric;

//? fabric {

import me.kveex.fast_connect_button.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatform implements Platform {

	@Override
	public Path getConfigFolder() {
		return FabricLoader.getInstance().getConfigDir();
	}
}
//?}
