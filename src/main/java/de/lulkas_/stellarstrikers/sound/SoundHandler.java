package de.lulkas_.stellarstrikers.sound;

import de.lulkas_.stellarstrikers.GameObjectHandler;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundHandler {
    public static void playSound(String filepath, Float volume, GameObjectHandler gameObjectHandler) {
        if(gameObjectHandler.playerOptionsHandler.isPlayingSound()) {
            try {
                InputStream inputStream = SoundHandler.class.getResourceAsStream(filepath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl vol = ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN));
                vol.setValue(volume - 5);
                clip.start();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                System.out.println("Error while reading sound file");
                ex.printStackTrace();
            }
        }
    }
}
