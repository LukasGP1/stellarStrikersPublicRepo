package de.lulkas_.stellarstrikers.playerData;

public class PlayerPowerUpHandler {
    private int damage = 0;
    private int income = 0;
    private int maxBulletCooldown = 50;
    private int damagePowerUpTicks = 0;
    private int incomePowerUpTicks = 0;
    private int maxBulletCooldownPowerUpTicks = 0;

    public int getDamage() {
        return damage;
    }

    public int getIncome() {
        return income;
    }

    public int getMaxBulletCooldown() {
        return maxBulletCooldown;
    }

    public void tick() {
        if(damagePowerUpTicks >= 0) {
            damagePowerUpTicks--;
        } else {
            if(damage != 0) {
                damage = 0;
            }
        }

        if(incomePowerUpTicks >= 0) {
            incomePowerUpTicks--;
        } else {
            if(income != 0) {
                income = 0;
            }
        }

        if(maxBulletCooldownPowerUpTicks >= 0) {
            maxBulletCooldownPowerUpTicks--;
        } else {
            if(maxBulletCooldown != 50) {
                maxBulletCooldown = 50;
            }
        }
    }

    public void getDamagePowerUp(int amount, int ticks) {
        if(damage < amount) {
            damage = amount;
        }
        damagePowerUpTicks =+ ticks;
    }

    public void getIncomePowerUp(int amount, int ticks) {
        if(income < amount) {
            income = amount;
        }
        incomePowerUpTicks =+ ticks;
    }

    public void getMaxBulletCooldownPowerUp(int amount, int ticks) {
        if(maxBulletCooldown > 50 - amount) {
            maxBulletCooldown = 50 - amount;
        }
        maxBulletCooldownPowerUpTicks =+ ticks;
    }

    public void resetPowerUps() {
        damage = 0;
        income = 0;
        maxBulletCooldown = 50;
        damagePowerUpTicks = 0;
        incomePowerUpTicks = 0;
        maxBulletCooldownPowerUpTicks = 0;
    }
}
