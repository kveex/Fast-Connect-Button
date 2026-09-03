package me.kveex.fast_connect_button;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public enum ConnectButtonPlace implements NameableEnum {
	RIGHT,
	LEFT;

	@Override
	public Component getDisplayName() {
		return Component.translatable(FastConnectButton.MOD_ID + ".button_place." + name().toLowerCase(Locale.ENGLISH));
	}
}
