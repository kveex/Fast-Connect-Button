package me.kveex.fast_connect_button.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionEventListener;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.CyclingListControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import me.kveex.fast_connect_button.ConnectButtonPlace;
import me.kveex.fast_connect_button.ConnectButtonPlaceMode;
import me.kveex.fast_connect_button.ConnectButtonType;
import me.kveex.fast_connect_button.config.controller.ServerAddressController;
import me.kveex.fast_connect_button.config.controller.ServerPortController;
import me.kveex.fast_connect_button.event.ScreenInitEventHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ConfigScreen {
	public static Screen createConfigScreen(Screen parentScreen) {
		ListOption<String> ignoredButtons = ListOption.<String>createBuilder()
				.name(Component.translatable("fast_connect_button.option.ignored_buttons"))
				.description(OptionDescription.createBuilder()
						.text(Component.translatable("fast_connect_button.option.ignored_buttons.description"), Component.empty())
						.text(Component.translatable("fast_connect_button.option.ignored_buttons.description_warning")
								.withStyle(ChatFormatting.RED))
						.build())
				.binding(Config.getInstance().ignoredButtons, () -> Config.getInstance().ignoredButtons, newList -> Config.getInstance().ignoredButtons = newList)
				.controller(StringControllerBuilder::create)
				.initial("")
				.collapsed(true)
				.build();

		ListOption<String> unReplaceableButtons = ListOption.<String>createBuilder()
				.name(Component.translatable("fast_connect_button.option.un_replaceable_buttons"))
				.description(OptionDescription.createBuilder()
						.text(Component.translatable("fast_connect_button.option.un_replaceable_buttons.description"))
						.build())
				.binding(Config.getInstance().unReplaceableButtons, () -> Config.getInstance().unReplaceableButtons, newList -> Config.getInstance().unReplaceableButtons = newList)
				.controller(StringControllerBuilder::create)
				.initial("")
				.collapsed(true)
				.build();

		ListOption<String> unMoveableButtons = ListOption.<String>createBuilder()
				.name(Component.translatable("fast_connect_button.option.un_moveable_buttons"))
				.description(OptionDescription.createBuilder()
						.text(Component.translatable("fast_connect_button.option.un_moveable_buttons.description"))
						.build())
				.binding(Config.getInstance().unMovableButtons, () -> Config.getInstance().unMovableButtons, newList -> Config.getInstance().unMovableButtons = newList)
				.controller(StringControllerBuilder::create)
				.initial("")
				.collapsed(true)
				.build();

		Option<String> serverAddress = Option.<String>createBuilder()
				.name(Component.translatable("fast_connect_button.option.server_address"))
				.binding(Config.getInstance().address, () -> Config.getInstance().address, val -> Config.getInstance().address = val)
				.controller(option -> () -> new ServerAddressController(option))
				.build();

		Option<Integer> serverPort = Option.<Integer>createBuilder()
				.name(Component.translatable("fast_connect_button.option.server_port"))
				.binding(Config.getInstance().port, () -> Config.getInstance().port, val -> Config.getInstance().port = val)
				.controller(option -> () -> new ServerPortController(option))
				.build();

		Option<String> buttonText = Option.<String>createBuilder()
				.name(Component.translatable("fast_connect_button.option.button_text"))
				.binding(Config.getInstance().buttonText, () -> Config.getInstance().buttonText, val -> Config.getInstance().buttonText = val)
				.controller(StringControllerBuilder::create)
				.build();

		Option<Color> buttonTextColor = Option.<Color>createBuilder()
				.name(Component.translatable("fast_connect_button.option.button_text_color"))
				.binding(Config.getInstance().buttonTextColor, () -> Config.getInstance().buttonTextColor, val -> Config.getInstance().buttonTextColor = val)
				.controller(ColorControllerBuilder::create)
				.build();

		Option<String> anchorButton = Option.<String>createBuilder()
				.name(Component.translatable("fast_connect_button.option.anchor_button"))
				.description(OptionDescription.createBuilder()
						.text(Component.translatable("fast_connect_button.option.anchor_button.description"))
						.build())
				.binding(Config.getInstance().anchorKeyTranslationKey, () -> Config.getInstance().anchorKeyTranslationKey, val -> Config.getInstance().anchorKeyTranslationKey = val)
				.controller(opt -> CyclingListControllerBuilder.create(opt)
						.values(ScreenInitEventHandler.TITLE_SCREEN_BUTTON_TRANSLATIONS)
						.formatValue(Component::translatable)
				)
				.build();

		Option<ConnectButtonPlaceMode> buttonPlaceMode = Option.<ConnectButtonPlaceMode>createBuilder()
				.name(Component.translatable("fast_connect_button.option.button_place_mode"))
				.description(OptionDescription.createBuilder()
						.text(Component.translatable("fast_connect_button.option.button_place_mode.description"), Component.empty())
						.text(Component.translatable("fast_connect_button.option.button_place_mode.description.place"), Component.empty())
						.text(Component.translatable("fast_connect_button.option.button_place_mode.description.move"), Component.empty())
						.text(Component.translatable("fast_connect_button.option.button_place_mode.description.replace"))
						.build())
				.binding(Config.getInstance().connectButtonPlaceMode, () -> Config.getInstance().connectButtonPlaceMode, val -> Config.getInstance().connectButtonPlaceMode = val)
				.controller(opt -> EnumControllerBuilder.create(opt).enumClass(ConnectButtonPlaceMode.class))
				.build();

		anchorButton.addEventListener((option, event) -> {
			if (event.equals(OptionEventListener.Event.STATE_CHANGE)) {
				if (Config.getInstance().unReplaceableButtons.contains(option.pendingValue()) && buttonPlaceMode.pendingValue().equals(ConnectButtonPlaceMode.REPLACE)) {
					buttonPlaceMode.requestSet(ConnectButtonPlaceMode.PLACE);
				}
			}
		});

		anchorButton.addEventListener((option, event) -> {
			if (event.equals(OptionEventListener.Event.STATE_CHANGE)) {
				if (Config.getInstance().unMovableButtons.contains(option.pendingValue()) && buttonPlaceMode.pendingValue().equals(ConnectButtonPlaceMode.MOVE)) {
					buttonPlaceMode.requestSet(ConnectButtonPlaceMode.REPLACE);
				}
			}
		});

		Option<ConnectButtonPlace> buttonPlace = Option.<ConnectButtonPlace>createBuilder()
				.name(Component.translatable("fast_connect_button.option.button_place"))
				.description(OptionDescription.createBuilder()
						.text(Component.translatable("fast_connect_button.option.button_place.description"))
						.build())
				.binding(Config.getInstance().connectButtonPlace, () -> Config.getInstance().connectButtonPlace, val -> Config.getInstance().connectButtonPlace = val)
				.controller(opt -> EnumControllerBuilder.create(opt).enumClass(ConnectButtonPlace.class))
				.available(!Config.getInstance().connectButtonPlaceMode.equals(ConnectButtonPlaceMode.REPLACE))
				.build();

		buttonPlaceMode.addEventListener((option, event) -> {
			if (event.equals(OptionEventListener.Event.STATE_CHANGE)) {
				ConnectButtonPlaceMode correctMode;
				boolean anchorButtonUnmovable = Config.getInstance().unMovableButtons.contains(anchorButton.pendingValue());
				if (option.pendingValue() == ConnectButtonPlaceMode.MOVE && anchorButtonUnmovable) {
					correctMode = ConnectButtonPlaceMode.REPLACE;
				} else {
					correctMode = option.pendingValue();
				}

				option.requestSet(correctMode);
			}
		});

		buttonPlaceMode.addEventListener((option, event) -> {
			if (event.equals(OptionEventListener.Event.STATE_CHANGE)) {
				ConnectButtonPlaceMode correctMode;
				boolean anchorButtonUnreplaceable = Config.getInstance().unReplaceableButtons.contains(anchorButton.pendingValue());
				if (option.pendingValue() == ConnectButtonPlaceMode.REPLACE && anchorButtonUnreplaceable) {
					correctMode = ConnectButtonPlaceMode.PLACE;
				} else {
					correctMode = option.pendingValue();
				}

				option.requestSet(correctMode);
			}
		});

		buttonPlaceMode.addEventListener(((option, event) -> {
			if (event.equals(OptionEventListener.Event.STATE_CHANGE)) {
				boolean available = option.pendingValue() != ConnectButtonPlaceMode.REPLACE;
				buttonPlace.setAvailable(available);
			}
		}));


		Option<ConnectButtonType> buttonType = Option.<ConnectButtonType>createBuilder()
				.name(Component.translatable("fast_connect_button.option.button_type"))
				.description(OptionDescription.createBuilder()
						.text(Component.translatable("fast_connect_button.option.button_type.description"), Component.empty())
						.text(Component.translatable("fast_connect_button.option.button_type.description.text"), Component.empty())
						.text(Component.translatable("fast_connect_button.option.button_type.description.icon"), Component.empty())
						.build())
				.binding(Config.getInstance().connectButtonType, () -> Config.getInstance().connectButtonType, val -> Config.getInstance().connectButtonType = val)
				.controller(opt -> EnumControllerBuilder.create(opt).enumClass(ConnectButtonType.class))
				.build();

		return YetAnotherConfigLib.createBuilder()
				.title(Component.translatable("fast_connect_button.category.button_configuration"))
				.category(ConfigCategory.createBuilder()
						.name(Component.translatable("fast_connect_button.category.button_configuration"))
						.group(OptionGroup.createBuilder()
								.name(Component.translatable("fast_connect_button.group.main"))
								.option(serverAddress)
								.option(serverPort)
								.option(buttonText)
								.option(buttonTextColor)
								.option(anchorButton)
								.option(buttonPlaceMode)
								.option(buttonPlace)
								.option(buttonType)
								.build())
						.build())
				.category(ConfigCategory.createBuilder()
						.name(Component.translatable("fast_connect_button.category.lists"))
						.group(unMoveableButtons)
						.group(unReplaceableButtons)
						.group(ignoredButtons)
						.build()).save(() -> Config.HANDLER.save()).build().generateScreen(parentScreen);
	}
}
