package com.github.ong.qo.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UploadQo {

    private String wechatCode;

    private List<String> fileUrlList;

    private List<String> fileNameList;
}
