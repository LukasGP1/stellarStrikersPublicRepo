package de.lulkas_.stellarstrikers.menu;

import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.level.Entity;

import java.util.List;
import java.util.function.Supplier;

public class ExclamationMark extends Entity {
    private final Supplier<Boolean> getVisible;

    public ExclamationMark(float startX, float startY, int width, int height, Supplier<Boolean> getVisible, GameObjectHandler gameObjectHandler) {
        super(width, height, startX, startY, 1, gameObjectHandler);
        this.getVisible = getVisible;
    }

    public List<?> getPosData() {
        return List.of(0f, gameX / 1000f, gameY / 1000f, gameWidth / 1000f, gameHeight / 1000f);
    }

    public List<?> getMiscData() {
        return List.of(0, getVisible.get() ? 1 : 0);
    }

    @Override
    public List<Entity> getCollideWith() {
        return List.of();
    }

    @Override
    public void collideWith(Entity entity) {

    }
}
