package me.kveex.fast_connect_button;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(value = FastConnectButton.MOD_ID, dist = Dist.CLIENT)
public class FastConnectButton {
    public static final String MOD_ID = "fast_connect_button";

    public FastConnectButton(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
