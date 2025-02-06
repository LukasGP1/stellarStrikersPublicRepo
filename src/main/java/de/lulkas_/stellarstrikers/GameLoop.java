package de.lulkas_.stellarstrikers;

import org.gamejolt.GameJoltAPI;

public class GameLoop implements Runnable {
    private final GameJoltAPI api;
    public GameWindow window;
    private Thread gameThread;
    public final int UPS_SET = 200;
    private int updatesForSessionCheck = 0;

    public GameLoop(GameJoltAPI api) {
        this.api = api;
        window = new GameWindow(api);
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int updates = 0;

        double deltaU = 0;

        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;

            if(deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
        }
    }

    public void update() {
        window.tick();
        window.gameObjectHandler.tick();

        updatesForSessionCheck++;
        if(updatesForSessionCheck >= 60000) {
            updatesForSessionCheck = 0;
            (new Thread(() -> api.sessionUpdate(true))).start();
        }
    }
}
