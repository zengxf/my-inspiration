package cn.zxf.spring.context;

import cn.zxf.spring.model.CurContext;
import cn.zxf.utils.CurContextUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 初始化上下文过滤器
 * <p/>
 * web 层引入 sso (WebSecurityConfig) 需要单独配置，remote 层不需要
 * <p/>
 * Created by ZXFeng on 2026/5/26
 */
@Component
@Slf4j
@Order(190) // 在 AuthTokenFilter 之前执行
public class InitContextFilter extends OncePerRequestFilter {

    @PostConstruct
    public void init() {
        log.info("初始上下文过滤器 InitContextFilter 初始化成功！");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            CurContext ctx = CurContextUtils.init(request);

            MDC.put("traceId", ctx.getTraceId());       // 日志链路

            filterChain.doFilter(request, response);
        } finally {
            CurContextUtils.clear();   // 防止线程池复用串数据
            MDC.clear();            // 防止 MDC 串日志
        }
    }

}
