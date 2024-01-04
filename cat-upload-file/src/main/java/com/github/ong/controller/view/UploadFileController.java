package com.github.ong.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/view/upload")
@Controller
@Slf4j
public class UploadFileController {

    @RequestMapping("/initCode")
    public String initCode() {
        return "init_code";
    }

    @RequestMapping("/file")
    public String uploadFile(String code, ModelMap modelMap) {
        log.info("code is {}", code);
        modelMap.put("code", code);
        return "upload_file";
    }
}
