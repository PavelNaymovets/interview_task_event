package com.interviewTask.event.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class AppConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/message/exception/ex_authentication",
                "classpath:/message/exception/ex_has_deleted_status",
                "classpath:/message/exception/ex_resource_not_found",
                "classpath:/message/exception/ex_validation",
                "classpath:/message/log/log");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }
}
