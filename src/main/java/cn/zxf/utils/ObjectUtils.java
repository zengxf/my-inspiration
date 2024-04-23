package cn.zxf.utils;

import cn.hutool.core.util.ObjectUtil;

/**
 * 对象帮助类
 * <br/>
 * Created by ZXFeng on  2022/7/1.
 */
public class ObjectUtils {

    /*** 判断是 null 或为空 */
    @Deprecated(since = "可直接用 HuTool 替换")
    public static boolean isNullOrBlank(Object obj) {
        return ObjectUtil.isEmpty(obj);
    }

}
