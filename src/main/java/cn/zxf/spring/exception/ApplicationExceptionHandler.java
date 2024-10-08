package cn.zxf.spring.exception;

import cn.zxf.common.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 应用异常处理者
 * <p/>
 * Created by ZXFeng on 2024/4/23
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ApplicationException.class)
    public Map<String, Object> errorHandle(HttpServletRequest req, ApplicationException e) {
        return Map.of(
                "errorCode", e.code,
                "errorMessage", e.getMessage(),
                "path", req.getRequestURL()
        );
    }

}
