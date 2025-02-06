package de.lulkas_.stellarstrikers.playerData;

import de.lulkas_.stellarstrikers.GameObjectHandler;

public class PlayerSkillHandler {
    private int damage;
    private int health;
    private int income;
    private PlayerPowerUpHandler playerPowerUpHandler;

    public PlayerSkillHandler(int damage, int health, int income, GameObjectHandler gameObjectHandler) {
        this.playerPowerUpHandler = gameObjectHandler.playerPowerUpHandler;
        this.damage = damage;
        this.health = health;
        this.income = income;
    }

    public int getDamage() {
        return damage + playerPowerUpHandler.getDamage();
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getIncome() {
        return income + playerPowerUpHandler.getIncome();
    }

    public void upgradeDamage(int amount) {
        damage += amount;
    }

    public void upgradeHealth(int amount) {
        health += amount;
    }

    public void upgradeIncome(int amount) {
        income += amount;
    }

    public Integer getRealDamage() {
        return damage;
    }

    public Integer getRealIncome() {
        return income;
    }

    public void resetSkills() {
        damage = 1;
        health = 3;
        income = 1;
    }
}
