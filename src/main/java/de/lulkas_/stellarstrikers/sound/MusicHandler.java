package de.lulkas_.stellarstrikers.sound;

import de.lulkas_.stellarstrikers.GamePanel;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicHandler {
    private Clip clip = null;
    private final int length;
    private boolean shouldPlay = false;
    private boolean playing = false;
    private int ticksPlaying = 0;
    private AudioInputStream audioInputStream;
    private final String path;
    private final float volume;
    private final GamePanel gamePanel;

    public MusicHandler(String path, int length, float volume, GamePanel gamePanel) {
        this.length = length;
        this.path = path;
        this.volume = volume;
        this.gamePanel = gamePanel;
        InputStream inputStream = SoundHandler.class.getResourceAsStream(path);
        try {
            assert inputStream != null;
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("error while trying to play music");
            e.printStackTrace();
        }
        FloatControl vol = ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN));
        vol.setValue(volume);
    }

    public void start() {
        if(gamePanel.playerOptionsHandler.isPlayingMusic()) {
            shouldPlay = true;
        }
    }

    public void stop() {
        shouldPlay = false;
    }

    public boolean isPlaying() {
        return shouldPlay;
    }

    public void tick() {
        if(shouldPlay && gamePanel.playerOptionsHandler.isPlayingMusic()) {
            if(!playing) {
                clip.start();
                playing = true;
                System.out.println("start");
            } else {
                ticksPlaying++;
                if(ticksPlaying >= length) {
                    restart();
                    ticksPlaying = 0;
                    System.out.println("restart");
                }
            }
        } else {
            if(playing) {
                clip.stop();
                playing = false;
                System.out.println("stop");
            }
        }
    }

    private void restart() {
        InputStream inputStream = SoundHandler.class.getResourceAsStream(path);
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.out.println("error while trying to play music");
            e.printStackTrace();
        }
        FloatControl vol = ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN));
        vol.setValue(volume);
        clip.start();
    }
}
