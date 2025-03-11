package com.ftt.config;

import com.ftt.utils.GetThumbUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetThumbUtilConfig {
    @Bean
    @ConditionalOnMissingBean
    public GetThumbUtil GetUtil()
    {
        return new GetThumbUtil();
    }

}
