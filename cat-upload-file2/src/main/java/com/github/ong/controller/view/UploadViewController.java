package com.github.ong.controller.view;

import com.github.ong.service.UserUploadInfoService;
import com.github.ong.utils.UploadUserInfoUtil;
import com.github.ong.vo.UserUploadInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("/view/upload")
@Controller
@Slf4j
public class UploadViewController {

    @Resource
    private UserUploadInfoService userUploadInfoService;

    @RequestMapping("/init_wechat")
    public String initCode() {
        return "init_wechat";
    }

    @RequestMapping("/file")
    public String uploadFile(String wechatCode, ModelMap modelMap) {
        log.info("code is {}", wechatCode);
        modelMap.put("code", wechatCode);
        UserUploadInfoVo resultVo = userUploadInfoService.getUploadInfo(UploadUserInfoUtil.WECHAT_CODE_ADMIN);
        modelMap.put("systemUploadInfo", resultVo);
        UserUploadInfoVo userUploadInfo = userUploadInfoService.getUploadInfo(wechatCode);
        modelMap.put("userUploadInfo", userUploadInfo);
        return "upload_file";
    }
}
