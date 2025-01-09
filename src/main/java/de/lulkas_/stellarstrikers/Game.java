package de.lulkas_.stellarstrikers;

import org.gamejolt.GameJoltAPI;

public class Game implements Runnable {
    private final GameJoltAPI api;
    public GameWindow window;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private int updatesForSessionCheck = 0;

    public Game(GameJoltAPI api) {
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
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1) {
                window.gamePanel.repaint();
                window.shaderPanel.repaint();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastCheck >= 100) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames * 10 + " | UPS: " + updates * 10);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void update() {
        window.tick();
        window.gamePanel.tick();

        updatesForSessionCheck++;
        if(updatesForSessionCheck >= 60000) {
            updatesForSessionCheck = 0;
            (new Thread(() -> api.sessionUpdate(true))).start();
        }
    }
}
