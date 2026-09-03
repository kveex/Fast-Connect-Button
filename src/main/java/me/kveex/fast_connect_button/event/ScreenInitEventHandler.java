package me.kveex.fast_connect_button.event;

import me.kveex.fast_connect_button.FastConnectButton;
import me.kveex.fast_connect_button.config.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScreenInitEventHandler {
	public static GuiEventListener LISTENER_FOR_REMOVE = null;
	public static final List<Button> TITLE_SCREEN_BUTTONS = new ArrayList<>();
	public static final List<String> TITLE_SCREEN_BUTTON_TRANSLATIONS = new ArrayList<>();
	private static final int BUTTON_MARGIN = 4;

	public static Optional<Button> onTitleScreenPostInit(Screen screen, List<? extends GuiEventListener> listeners) {
		if (!(screen instanceof TitleScreen)) return Optional.empty();
		TITLE_SCREEN_BUTTONS.clear();
		TITLE_SCREEN_BUTTON_TRANSLATIONS.clear();

		Button fastConnectButton = FastConnectButton.createButton(screen);

		Config config = Config.getInstance();
		for (GuiEventListener listener : listeners) {
			if (!(listener instanceof Button buttonListener)) continue;
			if (buttonListener.getMessage().getContents() instanceof TranslatableContents contents && !config.ignoredButtons.contains(contents.getKey())) {
				TITLE_SCREEN_BUTTONS.add(buttonListener);
				TITLE_SCREEN_BUTTON_TRANSLATIONS.add(contents.getKey());
			}

			switch (config.connectButtonPlaceMode) {
				case PLACE -> place(fastConnectButton, buttonListener);
				case MOVE -> move(fastConnectButton, buttonListener);
				case REPLACE -> replace(fastConnectButton, buttonListener);
			}
		}

		return Optional.of(fastConnectButton);
	}

	private static void place(Button fastConnectButton, Button anchorButton) {
		Config config = Config.getInstance();
		if (!Component.translatable(config.anchorKeyTranslationKey).equals(anchorButton.getMessage())) return;

		switch (config.connectButtonPlace) {
			case LEFT -> fastConnectButton.setPosition(anchorButton.getX() - (fastConnectButton.getWidth() + BUTTON_MARGIN), anchorButton.getY());
			case RIGHT -> fastConnectButton.setPosition(anchorButton.getX() + anchorButton.getWidth() + BUTTON_MARGIN, anchorButton.getY());
		}
	}

	private static void move(Button fastConnectButton, Button movedButton) {
		Config config = Config.getInstance();
		if (!Component.translatable(config.anchorKeyTranslationKey).equals(movedButton.getMessage())) return;

		int movedButtonWidth = switch (config.connectButtonType) {
			case TEXT -> {
				int width = movedButton.getWidth() / 2 - BUTTON_MARGIN / 2;
				fastConnectButton.setSize(width, movedButton.getHeight());
				yield width;
			}
			case ICON -> movedButton.getWidth() - FastConnectButton.FAST_CONNECT_SPRITE_BUTTON_WIDTH - BUTTON_MARGIN;
		};

		movedButton.setWidth(movedButtonWidth);

		switch (config.connectButtonPlace) {
			case LEFT -> {
				fastConnectButton.setPosition(movedButton.getX(), movedButton.getY());
				movedButton.setPosition(fastConnectButton.getX() + fastConnectButton.getWidth() + BUTTON_MARGIN, movedButton.getY());
			}
			case RIGHT -> fastConnectButton.setPosition(movedButton.getX() + movedButton.getWidth() + BUTTON_MARGIN, movedButton.getY());
		}

	}

	private static void replace(Button fastConnectButton, Button replacedButton) {
		if (!Component.translatable(Config.getInstance().anchorKeyTranslationKey).equals(replacedButton.getMessage())) return;
		LISTENER_FOR_REMOVE = replacedButton;

		fastConnectButton.setPosition(replacedButton.getX(), replacedButton.getY());
		fastConnectButton.setSize(replacedButton.getWidth(), replacedButton.getHeight());
	}
}
