package cn.zxf.sql;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL 分页
 * <br/>
 * Created by ZXFeng on  2022/6/28.
 */
@AllArgsConstructor
public class SqlLimiter {

    public final String sql;
    public final List<Object> args;

    /*** 分页，从 1 开始 */
    public static SqlLimiter of(long pageIndex, long pageSize) {
        if (pageIndex < 1)
            pageIndex = 1;
        pageIndex = pageIndex - 1;
        long skip = pageIndex * pageSize;
        return ofSkip(skip, pageSize);
    }

    /*** 分页，从 1 开始（有些特殊导出用到了 skip） */
    public static SqlLimiter of(Integer skip, int pageIndex, int pageSize) {
        int actualSkip = skip == null ? (pageIndex - 1) * pageSize : skip;
        return ofSkip(actualSkip, pageSize);
    }

    /*** 分页，指定跳过条数 */
    public static SqlLimiter ofSkip(long skip, long pageSize) {
        String sql = "     LIMIT ?, ?";
        List<Object> args = new ArrayList<>();
        args.add(skip);
        args.add(pageSize);
        return new SqlLimiter(sql, args);
    }

}
