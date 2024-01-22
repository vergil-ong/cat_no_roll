package com.github.ong.enums.db;

import com.github.ong.enums.BaseDaoEnum;
import lombok.Getter;

@Getter
public enum WholeAddr implements BaseDaoEnum {
    LOCAL(1, "本地"),
    SSO(2, "阿里SSO"),
    ;

    private Integer code;

    private String name;

    WholeAddr(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
