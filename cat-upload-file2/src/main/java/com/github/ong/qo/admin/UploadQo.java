package com.github.ong.qo.admin;

import com.github.ong.qo.common.TablePageQo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UploadQo extends TablePageQo {

    private String wechatCode;

    private List<String> fileUrlList;

    private List<String> fileNameList;

    private List<String> checkIdList;
}
