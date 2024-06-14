package cn.zxf.spring.bean;

import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Bean 帮助类
 * <br/>
 * Created by ZXFeng on 2022/8/19.
 */
public class BeanUtils {

    /**
     * 根据特定注解获取 bean Map。
     * <br/>
     * 示例：
     * <pre>
     * Map<String, Object> beanMap = BeanUtils.getBeanMap(applicationContext, ComponentScan.class);
     * </pre>
     */
    public static Map<String, Object> getBeanMap(
            ApplicationContext context,
            Class<? extends Annotation> annotationType
    ) {
        return context.getBeansWithAnnotation(annotationType);
    }

}
