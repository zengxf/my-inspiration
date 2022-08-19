package cn.zxf.spring.aop;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * AOP 帮助类
 * <br/>
 * Created by ZXFeng on 2022/8/19.
 */
public class AopUtils {

    /*** 通过代理对象，获取原始的类 */
    public static Class getOriginClass(Object proxyBean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(proxyBean);
        return clazz;
    }

    /**
     * 判断是不是目标 bean 名
     * <pre>
     * // 获取所有 bean 名称
     * String[] beanNames = beanFactory.getBeanNamesForType(Object.class);
     * beanNames.forEach( bn -> isScopedTarget(bn) );
     * </pre>
     */
    public static boolean isScopedTarget(String beanName) {
        return ScopedProxyUtils.isScopedTarget(beanName);
    }

    /*** 获取最终的原始类 */
    public static Class<?> getOriginClass(ConfigurableListableBeanFactory beanFactory, String beanName) {
        Class<?> type = null;
        try {
            type = AutoProxyUtils.determineTargetClass(beanFactory, beanName);
        } catch (Throwable ignored) {
        }
        if (ScopedObject.class.isAssignableFrom(type)) {
            try {
                String targetBeanName = ScopedProxyUtils.getTargetBeanName(beanName);
                Class<?> targetClass = AutoProxyUtils.determineTargetClass(beanFactory, targetBeanName);
                if (targetClass != null)
                    type = targetClass;
            } catch (Throwable ignored) {
            }
        }
        return type;
    }

}
