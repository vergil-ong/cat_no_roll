package com.github.ong.service;

import com.github.ong.vo.StsInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StsServiceTest {

    @Test
    public void test1() {
        StsService stsService = new StsService();
        for (int i = 0; i < 5; i++) {
            StsInfo stsInfo = stsService.getStsInfoFromCache();
            log.info("stsInfo token is {}", stsInfo.getStsToken());
        }

    }
}
