package com.github.ong.config.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Bean(name = "outRestTemplate")
    public RestTemplate outRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(15 * 1000);
        factory.setConnectTimeout(15 * 1000);
        return new RestTemplate(factory);
    }
}
