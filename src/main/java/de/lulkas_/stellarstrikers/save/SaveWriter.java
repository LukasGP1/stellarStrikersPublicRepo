package de.lulkas_.stellarstrikers.save;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import org.gamejolt.GameJoltAPI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveWriter {
    private final GameObjectHandler gameObjectHandler;
    private final GameJoltAPI api;
    private Thread saveThread;

    public SaveWriter(GameObjectHandler gameObjectHandler, GameJoltAPI api) {
        this.gameObjectHandler = gameObjectHandler;
        this.api = api;
    }

    public void save() {
        this.saveThread = new Thread(new SaveThread(api, gameObjectHandler));
        saveThread.start();

        try {
            File localDataFile = new File("localData.dat");
            if(!localDataFile.exists()) {
                localDataFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(localDataFile);
            fileWriter.write(gameObjectHandler.playerLocalDataHandler.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Could not save local data:");
            e.printStackTrace();
        }
    }
}
