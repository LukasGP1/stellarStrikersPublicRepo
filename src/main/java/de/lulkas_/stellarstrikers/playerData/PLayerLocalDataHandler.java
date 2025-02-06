package de.lulkas_.stellarstrikers.playerData;

public class PLayerLocalDataHandler {
    private Integer lastSkinSeen;

    public PLayerLocalDataHandler(int lastSkinSeen) {
        this.lastSkinSeen = lastSkinSeen;
    }

    public void seeSkin(int skin) {
        if(skin > lastSkinSeen) {
            lastSkinSeen = skin;
        }
    }

    public boolean isSkinSeen(int skin) {
        return lastSkinSeen >= skin;
    }

    @Override
    public String toString() {
        return lastSkinSeen.toString();
    }
}
