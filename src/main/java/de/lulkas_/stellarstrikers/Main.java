package de.lulkas_.stellarstrikers;

import de.lulkas_.stellarstrikers.menu.UserFrame;
import de.lulkas_.stellarstrikers.save.CreditsWriter;
import org.gamejolt.GameJoltAPI;
import org.gamejolt.Trophy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Main {
    public static final int GAME_ID = 956256;
    //SecretStuff not in the public repo (it's secret)
    public static final String GAME_PRIVATE_KEY = SecretStuff.GAME_KEY;
    public static final Image icon = importImage("/textures/icon.png");
    public static Game game;

    public static void main(String[] args) {
        CreditsWriter.write();

        GameJoltAPI api = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY);
        api.setVerbose(true);

        UserFrame userFrame = new UserFrame(api);

        while(!userFrame.entered) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        api.sessionOpen();

        Trophy t = api.getTrophy(253091);

        api.achieveTrophy(t);

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("UTC"));

        String time = localDateTime.getMonthValue() + "." + localDateTime.getDayOfMonth() + "." + localDateTime.getYear();

        api.addHighscore(972438, time, ((int) (System.currentTimeMillis() / 1000 - 1735630454)));

        game = new Game(api);
    }

    private static Image importImage(String path) {
        InputStream is = Main.class.getResourceAsStream(path);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}