package com.github.ong.enums.db;

import com.github.ong.enums.BaseDaoEnum;
import lombok.Getter;

@Getter
public enum FileType implements BaseDaoEnum {
    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    ;

    private String name;
    private Integer code;

    FileType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
