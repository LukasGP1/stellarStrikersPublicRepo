package de.lulkas_.stellarstrikers.util;

public class Random {
    public static boolean randomChance(float chance) {
        if(chance > 1 || chance < 0) {
            System.out.println("Wrong input type");
            return false;
        }

        java.util.Random random = new java.util.Random();

        int randomNumber = Math.abs(random.nextInt());
        int chamceNumber = ((int) (2147483647 * chance));

        return randomNumber <= chamceNumber;
    }

    public static int randomInt(int max, int min) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
