package at.elias.chessgame;

import at.elias.chessgame.utility.Data;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setWindowedMode(Data.WIDTH, Data.HEIGHT);
        config.setResizable(false);

        config.setWindowIcon("knight.png");

        config.setTitle("Chessgame");
        new Lwjgl3Application(new Chessgame(), config);
    }
}
