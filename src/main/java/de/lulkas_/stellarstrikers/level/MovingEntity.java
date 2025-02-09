package de.lulkas_.stellarstrikers.level;

import de.lulkas_.stellarstrikers.GameObjectHandler;

public abstract class MovingEntity extends Entity {
    private final float decayFactor;
    protected float[] velocities = new float[]{0f, 0f};
    public final float mass;

    public MovingEntity(int gameWidth, int gameHeight, float startX, float startY, int health, GameObjectHandler gameObjectHandler, float decayFactor, float mass) {
        super(gameWidth, gameHeight, startX, startY, health, gameObjectHandler);
        this.decayFactor = decayFactor;
        this.mass = mass;
    }

    @Override
    protected void tick() {
        super.tick();
        velocities[0] *= decayFactor;
        velocities[1] *= decayFactor;
        this.gameX += velocities[0];
        this.gameY += velocities[1];
    }

    public float[] getVelocities() {
        return velocities;
    }
}
