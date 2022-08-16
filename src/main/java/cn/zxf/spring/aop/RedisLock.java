package cn.zxf.spring.aop;

import java.lang.annotation.*;

/**
 * 方法上锁的注解
 * <br/>
 * Created by ZXFeng on 2022/8/16.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisLock {

    /*** key */
    String value();

}
