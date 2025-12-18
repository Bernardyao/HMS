package com.his.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 跨域配置
 * 解决前后端分离开发时的跨域请求问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 配置跨域映射规则
     * 
     * @param registry CORS 注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 对所有路径生效
                // 使用 allowedOriginPatterns 而不是 allowedOrigins
                // 因为它支持通配符且与 allowCredentials(true) 兼容
                .allowedOriginPatterns("*")
                // 允许的 HTTP 方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许携带认证信息（Cookie、Authorization 头等）
                .allowCredentials(true)
                // 允许的请求头
                .allowedHeaders("*")
                // 暴露的响应头（前端可以访问的响应头）
                .exposedHeaders("*")
                // 预检请求的缓存时间（秒）
                .maxAge(3600);
    }
}
