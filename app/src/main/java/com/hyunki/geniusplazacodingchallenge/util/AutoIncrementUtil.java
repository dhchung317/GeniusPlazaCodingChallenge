package com.hyunki.geniusplazacodingchallenge.util;

public class AutoIncrementUtil {
    private static int autoIncrement = 0;

    public static int getAutoIncrement(int currentMark) {
        autoIncrement = currentMark;
        autoIncrement += 1;
        return autoIncrement;
    }
    public static int getAutoIncrement() {
        autoIncrement += 1;
        return autoIncrement;
    }

}