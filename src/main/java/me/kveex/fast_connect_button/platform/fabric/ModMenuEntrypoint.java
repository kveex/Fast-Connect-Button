package me.kveex.fast_connect_button.platform.fabric;

//? fabric {

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.kveex.fast_connect_button.config.ConfigScreen;

public class ModMenuEntrypoint implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return ConfigScreen::createConfigScreen;
	}
}

//? }
