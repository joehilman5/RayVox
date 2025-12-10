package main;

import engine.EngineManager;
import engine.WindowManager;

public class Launcher {

    private static WindowManager window;
    private static RayVox game;

    public static void main(String[] args) {
        window = new WindowManager("RayVox", 1600, 900, false);
        game = new RayVox();
        EngineManager engine = new EngineManager();
        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static RayVox getGame() {
        return game;
    }

}
