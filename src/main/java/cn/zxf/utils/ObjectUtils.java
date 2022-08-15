package cn.zxf.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 对象帮助类
 * <br/>
 * Created by ZXFeng on  2022/7/1.
 */
public class ObjectUtils {

    /*** 判断是 null 或为空 */
    public static boolean isNullOrBlank(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof CharSequence)
            return StrUtil.isBlank((CharSequence) obj);
        return false;
    }

}
