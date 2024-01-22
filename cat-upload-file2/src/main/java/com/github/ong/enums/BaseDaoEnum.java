package com.github.ong.enums;

import java.util.Arrays;
import java.util.Objects;

public interface BaseDaoEnum {

    Integer getCode();

    String getName();

    static <T extends Enum<?> & BaseDaoEnum> T getByCode(Integer code, Class<T> enumClass) {
        if (Objects.isNull(code)) {
            return null;
        }

        T[] enumConstants = enumClass.getEnumConstants();
        if (Objects.isNull(enumConstants)) {
            return null;
        }

        return Arrays.stream(enumConstants)
                .filter(baseDaoEnum -> baseDaoEnum.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
