package me.kveex.fast_connect_button.mixin;

import com.mojang.realmsclient.RealmsMainScreen;
import me.kveex.fast_connect_button.ButtonBounds;
import me.kveex.fast_connect_button.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Shadow
    @Nullable
    protected abstract Component getMultiplayerDisabledReason();

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "createNormalMenuOptions", at = @At("HEAD"), cancellable = true)
    private void createNormalMenuOptionsInject(int y, int rowHeight, CallbackInfo ci) {
        ci.cancel();
        int buttonHeight = 20;
        ServerAddress serverAddress = Config.getServerAddress();
        ButtonBounds fastConnectButtonBounds = Config.BUTTON_PLACEMENT.get();

        Tooltip fastConnectButtonTooltip = Config.SHOW_ADDRESS_ON_HOVER.getAsBoolean()
                ? Tooltip.create(Component.literal(serverAddress.toString()))
                : null;

        Button fastConnectButton = Button.builder(Component.literal(Config.BUTTON_TEXT.get()), b -> fastconnectbutton$connect(serverAddress))
                .tooltip(fastConnectButtonTooltip)
                .bounds(this.width / 2 + fastConnectButtonBounds.xOffset, y + fastConnectButtonBounds.rowHeight, fastConnectButtonBounds.width, buttonHeight)
                .build();

        Button singlePlayerButton = Button.builder(Component.translatable("menu.singleplayer"), button -> this.getMinecraft().setScreen(new SelectWorldScreen(this)))
                .bounds(this.width / 2 - 100, y, 200, buttonHeight)
                .build();

        Component component = this.getMultiplayerDisabledReason();
        boolean isMultiplayerDisabled = component != null;
        Tooltip tooltip = isMultiplayerDisabled ? Tooltip.create(component) : null;

        Button multiPlayerButton = Button.builder(Component.translatable("menu.multiplayer"), button -> {
            Screen screen = this.getMinecraft().options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
            this.getMinecraft().setScreen(screen);
        }).bounds(this.width / 2 - 100, y + rowHeight, 200, buttonHeight).tooltip(tooltip).build();

        multiPlayerButton.active = !isMultiplayerDisabled;

        Button realmsButton = Button.builder(Component.translatable("menu.online"), p_315821_ -> this.getMinecraft().setScreen(new RealmsMainScreen(this)))
                .bounds(this.width / 2 - 100, y + rowHeight * 2, 200, buttonHeight)
                .tooltip(tooltip)
                .build();

        realmsButton.active = !isMultiplayerDisabled;


        this.addRenderableWidget(fastConnectButton);
        if (fastConnectButtonBounds != ButtonBounds.REPLACE_SINGLEPLAYER) this.addRenderableWidget(singlePlayerButton);
        if (fastConnectButtonBounds != ButtonBounds.REPLACE_MULTIPLAYER) this.addRenderableWidget(multiPlayerButton);
        if (fastConnectButtonBounds != ButtonBounds.REPLACE_REALMS) this.addRenderableWidget(realmsButton);
    }

    @Unique
    private void fastconnectbutton$connect(ServerAddress serverAddress) {
        ServerData serverdata = new ServerData(I18n.get("selectServer.defaultName"), serverAddress.getHost(), ServerData.Type.OTHER);
        ConnectScreen.startConnecting(this, Minecraft.getInstance(), serverAddress, serverdata, false, null);
    }
}
