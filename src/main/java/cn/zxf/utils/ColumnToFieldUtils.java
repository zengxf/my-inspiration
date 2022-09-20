package cn.zxf.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 表列名转类字段名
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
public class ColumnToFieldUtils {

    public static String toField(String column) {
        if (StrUtil.isBlank(column)) {
            return StrUtil.EMPTY;
        }
        // ref: https://blog.csdn.net/wjx_jasin/article/details/95056639
        char[] chars = column.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char sign = chars[i];
            if (sign == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    char head = chars[j];
                    sb.append(Character.toUpperCase(head));
                    i++;
                }
            } else {
                sb.append(sign);
            }
        }
        return sb.toString();
    }

}
