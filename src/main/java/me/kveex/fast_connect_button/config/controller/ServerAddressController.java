package me.kveex.fast_connect_button.config.controller;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.string.IStringController;
import me.kveex.fast_connect_button.FastConnectButton;

public record ServerAddressController(Option<String> option) implements IStringController<String> {

	@Override
	public String getString() {
		return option.pendingValue();
	}

	@Override
	public void setFromString(String value) {
		option.requestSet(value);
	}

	@Override
	public boolean isInputValid(String input) {
		return input.matches(FastConnectButton.SERVER_ADDRESS_REGEX) || input.isEmpty();
	}


}
