package me.kveex.fast_connect_button;

import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final int DEFAULT_PORT = 25565;
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> SERVER_ADDRESS = BUILDER
            .define("serverAddress", "0", Config::validateAddress);

    public static final ModConfigSpec.ConfigValue<String> BUTTON_TEXT = BUILDER
            .define("buttonText", "Connect");

    public static final ModConfigSpec.BooleanValue SHOW_ADDRESS_ON_HOVER = BUILDER
            .define("showAddressOnHover", true);

    public static final ModConfigSpec.EnumValue<ButtonBounds> BUTTON_PLACEMENT = BUILDER
            .defineEnum("buttonPlacement", ButtonBounds.REPLACE_REALMS);

    private static boolean validateAddress(final Object value) {
        if (!(value instanceof String address)) return false;

        return address.matches("^[^\\s:]+(:\\d{1,5})?$");
    }

    public static ServerAddress getServerAddress() {
        String[] addressSplit = Config.SERVER_ADDRESS.get().split(":");
        String address = addressSplit[0];

        int port = addressSplit.length != 2
                ? DEFAULT_PORT
                : Integer.parseInt(addressSplit[1]);

        return new ServerAddress(address, port);
    }

    static final ModConfigSpec SPEC = BUILDER.build();
}
