package com.github.ong.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/admin")
@Slf4j
public class AdminViewController {

    @RequestMapping("/upload")
    public String initUpload() {
        return "admin/upload";
    }

    @RequestMapping("/download")
    public String initDownload() {
        return "admin/download";
    }
}
