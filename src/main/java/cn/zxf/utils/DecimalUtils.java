package cn.zxf.utils;

import cn.hutool.core.collection.CollUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.math.BigDecimal.ZERO;

/**
 * BigDecimal 帮助类
 * <p/>
 * Created by ZXFeng on 2024/6/14
 */
public class DecimalUtils {

    public static final int
            DEF_SCALE = 2,          // 默认保留的小数位
            RATE_SCALE = 2,         // 比率保留的小数位
            SCALE4 = 4,
            SCALE2 = 2,
            RATE_DIVIDE_SCALE = 4;  // 比率除数用的小数位（不设置会出现 0 的情况）
    public static final RoundingMode MODE = RoundingMode.HALF_UP;
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100L); // 百分比扩展


    // ---------------------

    /*** 返回（不为空且不为 0）：value != null && value != 0 */
    public static boolean neZero(BigDecimal value) {
        return value != null && value.compareTo(ZERO) != 0;
    }

    /*** 返回（不为空且不为 0）：value != null && value < 0 */
    public static boolean ltZero(BigDecimal value) {
        return value != null && value.compareTo(ZERO) < 0;
    }

    /*** 返回（不为空且大于 0）：value != null && value > 0 */
    public static boolean gtZero(BigDecimal value) {
        return value != null && value.compareTo(ZERO) > 0;
    }

    /*** 返回（为空或为 0）：value == null || value == 0 */
    public static boolean isNullOrZero(BigDecimal value) {
        return value == null || value.compareTo(ZERO) == 0;
    }

    /*** 是否相等 */
    public static boolean eq(BigDecimal v1, BigDecimal v2) {
        if (v1 == null || v2 == null)
            return v1 == v2;
        return v1.compareTo(v2) == 0;
    }

    /*** 是否小于或等于：v1 <= v2 */
    public static boolean le(BigDecimal v1, BigDecimal v2) {
        AssertUtils.notNull(v1, "V1 不能为空");
        AssertUtils.notNull(v2, "V2 不能为空");
        return v1.compareTo(v2) <= 0;
    }

    /*** 是否小于：v1 < v2 */
    public static boolean lt(BigDecimal v1, BigDecimal v2) {
        AssertUtils.notNull(v1, "V1 不能为空");
        AssertUtils.notNull(v2, "V2 不能为空");
        return v1.compareTo(v2) < 0;
    }

    /*** 是否大于或等于：v1 >= v2 */
    public static boolean ge(BigDecimal v1, BigDecimal v2) {
        AssertUtils.notNull(v1, "V1 不能为空");
        AssertUtils.notNull(v2, "V2 不能为空");
        return v1.compareTo(v2) >= 0;
    }

    /*** 是否大于：v1 > v2 */
    public static boolean gt(BigDecimal v1, BigDecimal v2) {
        AssertUtils.notNull(v1, "V1 不能为空");
        AssertUtils.notNull(v2, "V2 不能为空");
        return v1.compareTo(v2) > 0;
    }

    /*** 是否小于或等于：v1 <= v2 */
    public static boolean leNullable(BigDecimal v1, BigDecimal v2) {
        if (v1 == null || v2 == null)
            return false;
        return v1.compareTo(v2) <= 0;
    }

    /*** 是否在区间内(包含关系)：min <= v <= max */
    public static boolean between(BigDecimal v, BigDecimal min, BigDecimal max) {
        return ge(v, min) && le(v, max);
    }

    // --------------------------

    /*** 转换成 double，空值返回 0 */
    public static double toDouble(BigDecimal value) {
        if (value == null)
            return 0;
        return value.doubleValue();
    }

    /*** 求和 */
    public static <T> BigDecimal sum(List<T> list, Function<T, BigDecimal> fun) {
        if (CollUtil.isEmpty(list))
            return ZERO;
        return list.stream()
                .filter(Objects::nonNull)
                .map(fun)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /*** 字符串转换 */
    public static BigDecimal valueOf(String value) {
        AssertUtils.notEmpty(value, "要转换的值不能为空");
        return new BigDecimal(value);
    }

    /*** 从 Double 转换  */
    public static BigDecimal valueOf(Double value) {
        AssertUtils.notNull(value, "要转换的值不能为空");
        return BigDecimal.valueOf(value);
    }

    /*** 从 Double 转换 */
    public static BigDecimal valueOf(Double value, BigDecimal def) {
        if (value == null)
            return def;
        return BigDecimal.valueOf(value);
    }

    /*** 计算比差率，保留 2 位小数。公式：(value - ref) / ref * 100 */
    public static BigDecimal calcDiffRate(BigDecimal value, BigDecimal ref) {
        AssertUtils.notNull(value, "计算比率的值不能为空");
        AssertUtils.notNull(ref, "计算比率的参考不能为空");
        BigDecimal diff = value.subtract(ref);
        return diff.divide(ref, RATE_DIVIDE_SCALE, RoundingMode.HALF_UP)
                .multiply(HUNDRED)
                .setScale(RATE_SCALE, RoundingMode.HALF_UP);
    }

    /*** 除法：v1 / v2，默认只返回 2 位小数 */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        AssertUtils.notNull(v1, "V1 不能为空");
        AssertUtils.notNull(v2, "V2 不能为空");
        return v1.divide(v2, DEF_SCALE, RoundingMode.HALF_UP);
    }

    /*** 改成 2 位小数 */
    public static BigDecimal resetScale2(BigDecimal value) {
        AssertUtils.notNull(value, "要转换的值不能为空");
        return value.setScale(SCALE2, MODE);
    }

    /*** 改成 4 位小数 */
    public static BigDecimal resetScale4(BigDecimal value) {
        AssertUtils.notNull(value, "要转换的值不能为空");
        return value.setScale(SCALE4, MODE);
    }

}
