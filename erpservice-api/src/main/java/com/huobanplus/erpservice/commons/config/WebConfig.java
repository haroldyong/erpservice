/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.commons.config;

import com.huobanplus.erpservice.hotapi.interceptor.AuthorizeInterceptor;
import com.huobanplus.erpservice.platform.interceptor.PlatformInterceptor;
import com.huobanplus.erpservice.proxy.interceptor.UserAuthorizeInterceptor;
import com.huobanplus.erpservice.sandbox.interceptor.SandboxInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

/**
 * Created by allan on 2015/7/10.
 */
@Configuration
@EnableWebMvc
@Import(ApplicationConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;
    @Autowired
    private UserAuthorizeInterceptor userAuthorizeInterceptor;
    @Autowired
    private PlatformInterceptor platformInterceptor;
    @Autowired
    private SandboxInterceptor sandboxInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        converters.add(converter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor).addPathPatterns("/**/hotApi/rest/**");
        registry.addInterceptor(userAuthorizeInterceptor).addPathPatterns("/**/hotProxy/**");
        registry.addInterceptor(platformInterceptor).addPathPatterns("/**/erpService/platform/**");
        registry.addInterceptor(sandboxInterceptor).addPathPatterns("/**/erpService/sandbox/rest/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);

        argumentResolvers.add(new AttributeArgumentResolver());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        registry.viewResolver(resourceViewResolver);
    }

}
