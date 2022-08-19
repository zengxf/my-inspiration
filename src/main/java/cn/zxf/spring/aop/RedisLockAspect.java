package cn.zxf.spring.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁的 AOP 增强
 * <br/>
 * Created by ZXFeng on 2022/8/16.
 */
public class RedisLockAspect extends AbstractPointcutAdvisor implements MethodInterceptor, Ordered {

    private String appName;

    private AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(
            null, RedisLock.class, true
    );


    @Autowired
    public void setAppName(
            @Value("${spring.application.name}") String appName
    ) {
        this.appName = appName;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String lockKey = this.getLockKey(invocation);
        Lock lock = this.getLock(lockKey);
        boolean hasLock = lock.tryLock();
        Object result;
        if (hasLock) {          // 获得锁
            try {
                result = invocation.proceed(); // 执行
            } finally {
                lock.unlock();  // 释放锁
            }
        } else {
            throw new RuntimeException("未获取锁，稍后再试！");
        }
        return result;
    }

    private String getLockKey(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        RedisLock lockAnn = AnnotationUtils.findAnnotation(method, RedisLock.class);
        String lockKey = lockAnn.value();
        if (lockKey != null && !lockKey.isBlank())
            return lockKey;
        IBizService bizService = (IBizService) invocation.getThis();
        return this.appName + ":" + bizService.bizSign();
    }

    private Lock getLock(String lockKey) {
        // TODO: 使用 redisson 返回 RLock
        return new ReentrantLock();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this;
    }


    /*** 使用锁的业务服务接口的定义 */
    public interface IBizService {
        String bizSign();
    }

}
