package com.github.ong.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/view/download")
@Controller
@Slf4j
public class DownloadViewController {

    @RequestMapping("/file")
    public String downloadFiles(String wechatCode, ModelMap modelMap) {
        log.info("wechatCode is {}", wechatCode);
        modelMap.put("code", wechatCode);
        return "download_file";
    }

    @RequestMapping("/forward")
    public void test(
            String forwardUrl,
            HttpServletRequest httpServletRequest,
            HttpServletResponse response) throws IOException {
        String agent = httpServletRequest.getHeader ("User-Agent");
        if (StringUtils.contains(agent, "MicroMessenger")) {
            log.info("微信跳转");
            response.setStatus(200);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", "57");
        } else {
            response.sendRedirect(forwardUrl);
        }
    }
}
