package cn.zxf.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class RequestContextHolderUtils {

    public RequestContextHolderUtils() {
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attr = getRequestAttributes();
        if (attr == null) {
            return null;
        }
        return attr.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attr = getRequestAttributes();
        if (attr == null) {
            return null;
        }
        return attr.getResponse();
    }

    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static ServletContext getServletContext() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        if (context == null) {
            return null;
        }
        return context.getServletContext();
    }

}
