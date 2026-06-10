package cn.zxf.spring.exception;

import cn.hutool.core.util.StrUtil;
import cn.zxf.common.BizException;
import cn.zxf.spring.model.CurContext;
import cn.zxf.spring.model.ResultData;
import cn.zxf.spring.model.ResultDataBuilder;
import cn.zxf.utils.CurContextUtils;
import cn.zxf.utils.JsonUtils;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 异常处理者
 * <p/>
 * Created by ZXFeng on 2024/4/23
 */
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<Object> handleBizException(BizException e) {
        log.error("业务校验异常：[{}]", e.getMessage());

        CurContext ctx = CurContextUtils.get();

        return ResultDataBuilder.responseFail(e.code, e.getMessage())
                .setTraceId(ctx.getTraceId());
    }

    @ResponseBody
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData<Object> handleFeignException(HttpServletRequest req, FeignException e) {
        log.error("Feign 请求异常！", e);

        String url = e.request().url();
        String resStr = e.responseBody().map(buffer -> {
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            return new String(bytes);
        }).orElse("{}");
        Map<String, Object> resObj = JsonUtils.toMap(resStr);

        String errMsg = "系统繁忙，请稍后再试！";
        if (resObj != null) {
            String msg = StrUtil.toStringOrEmpty(resObj.get(ResultData.MSG_KEY));
            if (StrUtil.isNotEmpty(msg)) {
                errMsg = msg;
            }
        }

        Map<String, Object> data = Map.of(
                "debug-err-msg", "请求下游服务出错",
                "debug-err-target-url", url,
                "debug-err-feign-res", resObj == null ? resStr : resObj,
                "debug-err-stack-trace", ExceptionUtils.getStackTrace(e),
                "path", req.getRequestURL()
        );

        CurContext ctx = CurContextUtils.get();

        return ResultDataBuilder.responseFail(ResultDataBuilder.FAIL, errMsg)
                .setData(data)
                .setExceptions(e.getMessage())
                .setTraceId(ctx.getTraceId());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<Object> handleException(HttpServletRequest req, Exception e) {
        log.error("内部服务异常！", e);

        Map<String, Object> data = Map.of(
                "debug-err-class-name", e.getClass().getName(),
                "debug-err-stack-trace", ExceptionUtils.getStackTrace(e),
                "path", req.getRequestURL()
        );

        CurContext ctx = CurContextUtils.get();

        return ResultDataBuilder.responseFail(ResultDataBuilder.FAIL, "系统繁忙，请稍后再试！")
                .setData(data)
                .setExceptions(e.getMessage())
                .setTraceId(ctx.getTraceId());
    }

}
