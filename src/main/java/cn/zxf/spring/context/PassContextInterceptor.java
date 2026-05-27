package cn.zxf.spring.context;

import cn.zxf.common.constants.WebHeaderConstant;
import cn.zxf.spring.model.CurContext;
import cn.zxf.spring.model.CurUser;
import cn.zxf.utils.CurContextUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用于透传上下文信息的 OpenFeign 请求拦截器
 * <p/>
 * Created by ZXFeng on 2026/5/26
 */
@Component
@Slf4j
public class PassContextInterceptor implements RequestInterceptor, WebHeaderConstant {

    @PostConstruct
    public void init() {
        log.info("透传上下文请求拦截器 PassContextInterceptor 初始化成功！");
    }

    @Override
    public void apply(RequestTemplate tmp) {
        CurContext ctx = CurContextUtils.get();
        CurUser curUser = ctx.getUserOrInit();

        tmp.header(X_IP, ctx.getClientIp());

        tmp.header(X_TRACE_ID, ctx.getTraceId());


        tmp.header(X_USER_ID, String.valueOf(curUser.getUserId()));
        tmp.header(X_USER_NO, curUser.getUserNo());
        tmp.header(X_USER_NAME, curUser.getUsername());

        tmp.header(X_EMAIL, curUser.getEmail());

        tmp.header(X_EMP_ID, String.valueOf(curUser.getEmpId()));
        tmp.header(X_EMP_NAME, curUser.getEmpName());

        log.debug("Feign 透传请求上下文：[{}]", ctx);
    }

}
