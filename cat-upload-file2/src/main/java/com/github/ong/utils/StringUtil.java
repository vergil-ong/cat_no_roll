package com.github.ong.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class StringUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
