package cn.zxf.utils;

import cn.hutool.core.collection.CollUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.hibernate.validator.HibernateValidator;

import java.util.Set;

/**
 * 校验帮助类
 * <p/>
 * Created by ZXFeng on 2024/10/8
 */
public class ValidateUtils {

    /*** 注解式校验 */
    public static <T> void validate(T t, Class<?>... groups) {
        AssertUtils.notNull(t, "参数主对象不能为空");

        // ----------- 注解式校验 -----------

        ValidatorFactory vf = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty(BaseHibernateValidatorConfiguration.FAIL_FAST, Boolean.TRUE.toString())    // 快速失败返回模式
                .buildValidatorFactory();

        Validator validator = vf.getValidator();
        Set<ConstraintViolation<T>> violSet = validator.validate(t, groups);
        ConstraintViolation<T> violFirst = CollUtil.getFirst(violSet);
        if (violFirst != null) {
            AssertUtils.exeError(violFirst.getMessage());
        }

        vf.close();
    }

}
