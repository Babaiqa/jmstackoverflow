package com.javamentor.qa.platform.webapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/ask").setViewName("askQuestion");
        registry.addViewController("/site").setViewName("headerSidebarFooter");
        registry.addViewController("/users").setViewName("fragment/users");
        registry.addViewController("/main").setViewName("fragment/main");

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");//указываем что ресурсы ищем в дереве файлов
    }
}
