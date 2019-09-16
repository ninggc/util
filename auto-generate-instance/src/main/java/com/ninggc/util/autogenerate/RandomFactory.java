package com.ninggc.util.autogenerate;

import java.util.Random;

/**
 * generate random value
 */
public class RandomFactory {
    Random random = new Random();

    /**
     * get a random string in a specific length
     * @param length
     * @return
     */
    public String getString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int nextInt = getInt(1, 27) + (int) 'A';
            stringBuilder.append((char) nextInt);
        }
        return stringBuilder.toString();
    }

    /**
     *
     * @param low inclusive
     * @param high exclusive
     * @return a int value between low and high
     */
    public int getInt(int low, int high) {
        return random.nextInt(high - low) + low;
    }

    public static void main(String[] args) {
        RandomFactory randomFactory = new RandomFactory();
        String string = randomFactory.getString(20);
        System.out.println(string);
    }
}
