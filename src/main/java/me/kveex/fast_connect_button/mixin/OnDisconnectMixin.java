package me.kveex.fast_connect_button.mixin;

import me.kveex.fast_connect_button.FastConnectButton;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;

@SuppressWarnings("unused")
//? if < 1.21.10 {
@Mixin(PauseScreen.class)
//? } else {
/*@Mixin(Minecraft.class)
*///? }
public abstract class OnDisconnectMixin {
	//? if < 1.21.8 {
	@Inject(method = "onDisconnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"), cancellable = true)
	 //? } elif < 26.2 {
	/*@Inject(method = "disconnectFromWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"), cancellable = true)
	*///? } else {
	/*@Inject(method = "disconnectFromWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"), cancellable = true)
	*///? }
	private static void onDisconnectInject(CallbackInfo ci) {
		TitleScreen titleScreen = new TitleScreen(false);
		if (FastConnectButton.FAST_CONNECT_BUTTON_USED) {
			//? if < 26.2 {
			Minecraft.getInstance().setScreen(titleScreen);
			//? } else {
			/*Minecraft.getInstance().gui.setScreen(titleScreen);
			*///? }
			FastConnectButton.FAST_CONNECT_BUTTON_USED = false;
			ci.cancel();
		}
	}
}



