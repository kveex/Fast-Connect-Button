package me.kveex.fast_connect_button.config.controller;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.string.IStringController;
import me.kveex.fast_connect_button.FastConnectButton;

public record ServerPortController(Option<Integer> option) implements IStringController<Integer> {

	@Override
	public String getString() {
		return option.pendingValue().toString();
	}

	@Override
	public void setFromString(String value) {
		int port;

		try {
			port = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			port = 25565;
		}
		option.requestSet(port);
	}

	@Override
	public boolean isInputValid(String input) {
		return input.matches(FastConnectButton.SERVER_PORT_REGEX) || input.isEmpty();
	}


}
