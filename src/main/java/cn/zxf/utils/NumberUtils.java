package cn.zxf.utils;

import cn.hutool.core.util.StrUtil;

public class NumberUtils {

    /**
     * 检查整数是否为正数
     * @param number 要检查的整数
     * @return 如果整数大于 0，返回 true；否则返回 false
     */
    public static boolean isPositive(int number) {
        return number > 0;
    }

    /**
     * 检查整数是否为非负数
     * @param number 要检查的整数
     * @return 如果整数大于等于 0，返回 true；否则返回 false
     */
    public static boolean isNonNegative(int number) {
        return number >= 0;
    }

    /**
     * 检查浮点数是否为正数
     * @param number 要检查的浮点数
     * @return 如果浮点数大于 0，返回 true；否则返回 false
     */
    public static boolean isPositive(double number) {
        return number > 0;
    }

    /**
     * 检查浮点数是否为非负数
     * @param number 要检查的浮点数
     * @return 如果浮点数大于等于 0，返回 true；否则返回 false
     */
    public static boolean isNonNegative(double number) {
        return number >= 0;
    }

    /**
     * 将字符串转换为整数
     * @param str 要转换的字符串
     * @param defaultValue 默认值
     * @return 转换后的整数，如果转换失败则返回默认值
     */
    public static int parseInt(String str, int defaultValue) {
        if (StrUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 将字符串转换为浮点数
     * @param str 要转换的字符串
     * @param defaultValue 默认值
     * @return 转换后的浮点数，如果转换失败则返回默认值
     */
    public static double parseDouble(String str, double defaultValue) {
        if (StrUtil.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Long valueLong(String str) {
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
