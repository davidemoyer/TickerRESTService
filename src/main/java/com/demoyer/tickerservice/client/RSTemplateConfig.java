package com.demoyer.tickerservice.client;

import com.demoyer.tickerservice.model.RestServiceInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RSTemplateConfig {

    @Bean(name = "alphaVantageRSInfo")
    @ConfigurationProperties("alpha-vantage")
    public RestServiceInfo configureAlphaVantageRestServiceInfo() {
        return new RestServiceInfo();
    }
}
