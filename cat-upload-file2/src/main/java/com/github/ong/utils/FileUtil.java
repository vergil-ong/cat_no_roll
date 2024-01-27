package com.github.ong.utils;


public class FileUtil {

    public static final String DOT = ".";

    public static String getFileSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(DOT) + 1);
    }
}
