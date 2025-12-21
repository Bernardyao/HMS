package com.his.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 日志追踪过滤器
 * 为每个请求生成唯一的 traceId，放入 MDC 中，便于日志链路追踪
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 保证最先执行
public class LogTraceFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. 尝试从请求头获取 traceId (用于微服务透传)，如果没有则生成
            String traceId = request.getHeader(TRACE_ID);
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString().replace("-", "");
            }

            // 2. 放入 MDC
            MDC.put(TRACE_ID, traceId);
            
            // 3. 同时放入响应头，方便前端/调用方排查
            response.setHeader(TRACE_ID, traceId);

            filterChain.doFilter(request, response);
        } finally {
            // 4. 清理 MDC，防止内存泄漏和线程污染
            MDC.remove(TRACE_ID);
        }
    }
}
