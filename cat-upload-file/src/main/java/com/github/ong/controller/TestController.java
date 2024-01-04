package com.github.ong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/test1")
    public String test1(String hello) {
        log.info("hello param is {}", hello);
        return "hello " + hello;
    }
}
