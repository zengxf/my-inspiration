package cn.zxf.spring.exception;

import cn.zxf.common.BizException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 异常处理者
 * <p/>
 * Created by ZXFeng on 2024/4/23
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public Map<String, Object> errorHandle(HttpServletRequest req, BizException e) {
        return Map.of(
                "errorCode", e.code,
                "errorMessage", e.getMessage(),
                "path", req.getRequestURL()
        );
    }

}
