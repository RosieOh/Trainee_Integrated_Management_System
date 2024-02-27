package com.kdt.landing.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/resources/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/resources/css/");
//        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
//        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
//        registry.addResourceHandler("/clEditor/**").addResourceLocations("classpath:/static/clEditor/");
//        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");

    }

}