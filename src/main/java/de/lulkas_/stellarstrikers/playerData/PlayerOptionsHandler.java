package de.lulkas_.stellarstrikers.playerData;

import java.awt.*;

public class PlayerOptionsHandler {
    private boolean playingSound;
    private boolean playingMusic;
    private Color playerBulletColor;
    private Color enemyBulletColor;
    private Color bombColor;
    private Color detonatedBombColor;

    public PlayerOptionsHandler(boolean playingSound, boolean playingMusic, Color playerBulletColor, Color enemyBulletColor, Color bombColor, Color detonatedBombColor) {
        this.playingSound = playingSound;
        this.playingMusic = playingMusic;
        this.playerBulletColor = playerBulletColor;
        this.enemyBulletColor = enemyBulletColor;
        this.bombColor = bombColor;
        this.detonatedBombColor = detonatedBombColor;
    }

    public void changePlayingSound() {
        this.playingSound = !this.playingSound;
    }
    public void changePlayingMusic() {
        this.playingMusic = !this.playingMusic;
    }
    public void setPlayerBulletColor(Color playerBulletColor) {
        this.playerBulletColor = playerBulletColor;
    }
    public void setEnemyBulletColor(Color enemyBulletColor) {
        this.enemyBulletColor = enemyBulletColor;
    }
    public void setBombColor(Color bombColor) {
        this.bombColor = bombColor;
    }
    public void setDetonatedBombColor(Color detonatedBombColor) {
        this.detonatedBombColor = detonatedBombColor;
    }

    public Color getPlayerBulletColor() {
        return playerBulletColor;
    }
    public Boolean isPlayingSound() {
        return playingSound;
    }
    public Boolean isPlayingMusic() {
        return playingMusic;
    }
    public Color getEnemyBulletColor() {
        return enemyBulletColor;
    }
    public Color getBombColor() {
        return bombColor;
    }
    public Color getDetonatedBombColor() {
        return detonatedBombColor;
    }
}
