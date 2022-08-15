package cn.zxf.sql;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * SQL 条件
 * <br/>
 * Created by ZXFeng on  2022/6/28.
 */
@AllArgsConstructor
public class SqlCriteria {

    public static SqlCriteria EMPTY = new SqlCriteria(null, null);


    public final String sql;
    public final List<Object> args;


    /*** 判断是否为空 */
    public boolean isNotEmpty() {
        return this != EMPTY;
    }

    /*** 返回带 WHERE 拼接的 SQL */
    public String whereSql() {
        if (isNotEmpty())
            return "  WHERE " + sql + "\n";
        return StrUtil.EMPTY;
    }

}
