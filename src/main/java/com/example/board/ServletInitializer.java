package com.example.board;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.FormContentFilter;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BoardApplication.class);
    }

    @Bean
    public FilterRegistrationBean<FormContentFilter> formContentFilter() {
        FilterRegistrationBean<FormContentFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new FormContentFilter());
        return filterRegistrationBean;
    }

}
