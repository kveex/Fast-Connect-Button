package me.kveex.fast_connect_button;

@SuppressWarnings("unused")
public enum ButtonBounds {
    ON_RIGHT_SINGLEPLAYER(105, 50, 0),
    ON_LEFT_SINGLEPLAYER(-155, 50, 0),
    ON_RIGHT_MULTIPLAYER(105, 50, 24),
    ON_LEFT_MULTIPLAYER(-155, 50, 24),
    REPLACE_SINGLEPLAYER(-100, 200, 0),
    REPLACE_MULTIPLAYER(-100, 200, 24),
    REPLACE_REALMS(-100, 200, 48),
    NOWHERE(0, 0, 0);

    public final int xOffset;
    public final int width;
    public final int rowHeight;

    ButtonBounds(int xOffset, int buttonWidth, int rowHeight) {
        this.xOffset = xOffset;
        this.width = buttonWidth;
        this.rowHeight = rowHeight;
    }
}
