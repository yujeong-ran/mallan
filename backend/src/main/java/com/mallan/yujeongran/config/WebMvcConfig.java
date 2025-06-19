package com.mallan.yujeongran.config;

import com.mallan.yujeongran.icebreaking.admin.interceptor.AdminSessionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AdminSessionInterceptor adminSessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(adminSessionInterceptor)
                // Protected Path By Session
                .addPathPatterns(
                        "/admin/**",
                        "/management/**",
                        "/reviews/**"
                )
                .excludePathPatterns(
                        "/admin/login",
                        "/admin/create",
                        "/admin/delete",
                        "/admin/logout",
//                        "/reviews/all",
//                        "/reviews/delete",
//                        "/reviews/all/**",
//                        "/reviews/delete/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/common/profile_image/**")
                .addResourceLocations("classpath:/static/common/profile_image/");
    }

}
