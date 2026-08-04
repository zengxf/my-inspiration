package cn.zxf.utils;


import cn.hutool.core.util.StrUtil;
import cn.zxf.common.constants.WebHeaderConstant;
import cn.zxf.spring.model.CurContext;
import cn.zxf.spring.model.CurUser;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p/>
 * Created by ZXFeng on 2026/5/26
 */
public class CurContextUtils {

    public static final ThreadLocal<CurContext> LOCAL = new ThreadLocal<>();

    public static CurContext get() {
        CurContext ctx = LOCAL.get();
        AssertUtils.notNull(ctx, "当前上下文没有初始化！");
        return ctx;
    }

    public static CurContext getOrNull() {
        return LOCAL.get();
    }

    public static CurContext getOrDef() {
        CurContext ctx = LOCAL.get();
        return ctx == null ? new CurContext() : ctx;
    }

    public static void set(CurContext ctx) {
        LOCAL.set(ctx);
    }

    public static void clear() {
        LOCAL.remove();
    }

    public static CurContext init(HttpServletRequest req) {
        String clientIp = IpUtils.getClientIp();

        String traceId = req.getHeader(WebHeaderConstant.X_TRACE_ID);
        if (StrUtil.isEmpty(traceId)) {
            traceId = "TC" + IDWork.gen();
        }

        CurUser user = new CurUser()
                .setUserId(NumberUtils.valueLong(req.getHeader(WebHeaderConstant.X_USER_ID)))
                .setUserNo(req.getHeader(WebHeaderConstant.X_USER_NO))
                .setUsername(req.getHeader(WebHeaderConstant.X_USER_NAME))

                .setEmail(req.getHeader(WebHeaderConstant.X_EMAIL))

                .setEmpId(NumberUtils.valueLong(req.getHeader(WebHeaderConstant.X_EMP_ID)))
                .setEmpName(req.getHeader(WebHeaderConstant.X_EMP_NAME));

        CurContext ctx = new CurContext()
                .setClientIp(clientIp)
                .setTraceId(traceId)
                .setUser(user);

        set(ctx);

        return ctx;
    }

}
