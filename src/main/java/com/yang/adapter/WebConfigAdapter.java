package com.yang.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * explain：请求配置
 *
 * @author yang
 * @date 2021/1/1
 */
@Configuration
public class WebConfigAdapter implements WebMvcConfigurer {

    @Autowired
    private ApiHandlerInterceptor apiHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加对用户未登录的拦截器，并添加排除项
        registry.addInterceptor(apiHandlerInterceptor)
                .addPathPatterns("/**")
                // 排除样式、脚本、图片等资源文件
                .excludePathPatterns("/static/**", "/login/index");
    }
}
