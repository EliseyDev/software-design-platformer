package ru.mipt.bit.platformer;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.TanksGame;

public class GameDesktopLauncher {
    private static final int WINDOW_HEIGHT = 1024;
    private static final int WINDOW_WIDTH = 1280;


    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
        new Lwjgl3Application(new TanksGame(), config);
    }
}
