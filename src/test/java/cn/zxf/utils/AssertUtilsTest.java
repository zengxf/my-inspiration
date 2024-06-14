package cn.zxf.utils;

import cn.zxf.common.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * <p/>
 * Created by ZXFeng on 2024/6/14
 */
@Slf4j
public class AssertUtilsTest {

    @Test
    public void notNull() {
        AssertUtils.notNull("x", "参数不能为空");
    }

    @Test
    public void notNullCatchErrAndPrint() {
        boolean sign = false;
        try {
            AssertUtils.notNull(null, "查找实体为空，ID: [{}]", 10);
        } catch (ApplicationException e) {
            sign = true;
            log.info("断言的异常信息：[{}]", e.getMessage());
        }
        if (!sign)
            throw new AssertionError("非期望的方式结束！");
    }

    @Test
    public void notNullCatchErrAndPrint2() {
        boolean sign = false;
        try {
            AssertUtils.notNull(null, "参数不能为空");
        } catch (ApplicationException e) {
            sign = true;
            log.info("断言的异常信息：[{}]", e.getMessage());
        }
        if (!sign)
            throw new AssertionError("非期望的方式结束！");
    }

    @Test(expected = ApplicationException.class)
    public void notNullWithNull() {
        AssertUtils.notNull(null, "参数不能为空");
        throw new AssertionError("未出现期望的异常！");
    }

    @Test
    public void notEmpty() {
        AssertUtils.notEmpty("xf", "姓名不能为空");
    }

    @Test(expected = ApplicationException.class)
    public void notEmptyWithEmpty() {
        AssertUtils.notEmpty("", "姓名不能为空");
        throw new AssertionError("未出现期望的异常！");
    }

    @Test
    public void notAfter() {
        YearMonth v = YearMonth.parse("2023-04");
        YearMonth b = YearMonth.parse("2023-05");
        AssertUtils.notAfter(v, b, "{} 不能晚于 {}", v, b);
    }

    @Test(expected = ApplicationException.class)
    public void notAfterWithAfter() {
        YearMonth v = YearMonth.parse("2023-06");
        YearMonth b = YearMonth.from(LocalDate.parse("2023-05-01"));
        AssertUtils.notAfter(v, b, "{} 已晚于 {}", v, b);
        throw new AssertionError("未出现期望的异常！");
    }

    @Test
    public void mustFalse() {
        boolean condition = false;
        AssertUtils.mustFalse(condition, "必须为 false");
    }

    @Test(expected = ApplicationException.class)
    public void mustFalseWithTrue() {
        boolean condition = true;
        AssertUtils.mustFalse(condition, "必须为 false");
        throw new AssertionError("未出现期望的异常！");
    }

}
