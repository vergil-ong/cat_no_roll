package com.github.ong.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view/upload")
@Controller
@Slf4j
public class UploadViewController {

    @RequestMapping("/init_wechat")
    public String initCode() {
        return "init_wechat";
    }

    @RequestMapping("/file")
    public String uploadFile(String wechatCode, ModelMap modelMap) {
        log.info("code is {}", wechatCode);
        modelMap.put("code", wechatCode);
        return "upload_file";
    }
}
