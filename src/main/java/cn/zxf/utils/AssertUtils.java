package cn.zxf.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.zxf.common.AppErrCodeConstant;
import cn.zxf.common.ApplicationException;

import java.time.YearMonth;
import java.util.Collection;

/**
 * 断言帮助类
 * <p/>
 * msgFmt 统一使用 HuTool 格式化（其用 {} 做占位符）
 * <p/>
 * Created by ZXFeng on 2024/4/23
 */
public class AssertUtils implements AppErrCodeConstant {

    /*** 对象不能为空 */
    public static void notNull(Object obj, String msgFmt, Object... args) {
        if (obj != null)
            return;
        String msg = fmtMsg(msgFmt, args);
        throw new ApplicationException(ASSERT_ERR_CODE, msg);
    }

    /*** 字符串不能为空 */
    public static void notEmpty(String str, String msgFmt, Object... args) {
        if (StrUtil.isNotEmpty(str))
            return;
        String msg = fmtMsg(msgFmt, args);
        throw new ApplicationException(ASSERT_ERR_CODE, msg);
    }

    /*** 集合不能为空 */
    public static void notEmpty(Collection<?> collection, String msgFmt, Object... args) {
        if (CollectionUtil.isNotEmpty(collection))
            return;
        String msg = fmtMsg(msgFmt, args);
        throw new ApplicationException(ASSERT_ERR_CODE, msg);
    }

    /*** value 不能在 base 后面 */
    public static void notAfter(YearMonth value, YearMonth base, String msgFmt, Object... args) {
        if (value.isAfter(base)) {
            String msg = fmtMsg(msgFmt, args);
            throw new ApplicationException(ASSERT_ERR_CODE, msg);
        }
    }

    /*** cond 必须为 false */
    public static void mustFalse(boolean cond, String msgFmt, Object... args) {
        if (!cond)
            return;
        String msg = fmtMsg(msgFmt, args);
        throw new ApplicationException(ASSERT_ERR_CODE, msg);
    }

    /*** cond 必须为 true */
    public static void mustTrue(boolean cond, String msgFmt, Object... args) {
        if (cond)
            return;
        String msg = fmtMsg(msgFmt, args);
        throw new ApplicationException(ASSERT_ERR_CODE, msg);
    }

    /*** 执行错误（不应执行到此） */
    public static void exeError(String msgFmt, Object... args) {
        String msg = fmtMsg(msgFmt, args);
        throw new ApplicationException(ASSERT_ERR_CODE, msg);
    }

    // 格式化消息
    private static String fmtMsg(String msgFmt, Object... args) {
        if (ArrayUtil.isEmpty(args))
            return msgFmt;
        String msg = StrUtil.format(msgFmt, args);
        return msg;
    }

}
