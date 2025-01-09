package de.lulkas_.stellarstrikers.sound;

import de.lulkas_.stellarstrikers.GamePanel;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundHandler {
    public static void playSound(String filepath, Float volume, GamePanel gamePanel) {
        if(gamePanel.playerOptionsHandler.isPlayingSound()) {
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
