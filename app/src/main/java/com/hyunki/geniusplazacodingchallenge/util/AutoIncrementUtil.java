package com.hyunki.geniusplazacodingchallenge.util;

public class AutoIncrementUtil {
    public static int autoIncrement = 0;

    public static int getAutoIncrement(int databaseSize) {
        autoIncrement = databaseSize;
        autoIncrement += 1;
        return autoIncrement;
    }

}