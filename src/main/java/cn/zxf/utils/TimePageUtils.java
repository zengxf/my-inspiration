package cn.zxf.utils;

import cn.hutool.core.collection.CollUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 时间分页帮助类
 * <p/>
 * Created by ZXFeng on 2024/10/19
 */
public class TimePageUtils {

    /**
     * 完整日期时间格式
     */
    private static final String FULL_FMT = "yyyy-MM-dd HH:mm:ss";
    private static final LocalTime TIME_MAX = LocalTime.of(23, 59, 59);
    private static final int ONE = 1;


    /**
     * 指定 31 天分批 (不满足条件就返回空)
     * <br/>
     * 格式参考 {@link #FULL_FMT}
     */
    public static List<List<String>> pageDateTime(List<String> times) {
        return pageDateTime(31, times);
    }


    /**
     * 指定天数分批 (异常或不够天数返回空)
     * <br/>
     * 格式参考 {@link #FULL_FMT}
     */
    public static List<List<String>> pageDateTime(int days, List<String> times) {
        List<List<String>> empty = new ArrayList<>();
        if (CollUtil.size(times) != 2) {
            return empty;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(FULL_FMT);
        LocalDateTime bdt;
        LocalDateTime edt;
        try {
            bdt = LocalDateTime.parse(times.get(0), fmt);
            edt = LocalDateTime.parse(times.get(1), fmt);
        } catch (Exception e) {   // 格式有问题
            return empty;
        }

        int intervalD = (int) ChronoUnit.DAYS.between(bdt, edt);
        if (intervalD <= days) {
            return empty;
        }


        List<List<String>> list = new ArrayList<>();
        LocalDateTime start = bdt;
        boolean sign = true;
        while (sign) {
            LocalDateTime end = LocalDateTime.of(
                    start.toLocalDate().plusDays(days), // + days
                    TIME_MAX // LocalTime.MAX
            );

            if (end.isAfter(edt)) {
                end = edt;
                sign = false;                           // 退出
            }

            list.add(CollUtil.newArrayList(             // 添加结果
                    fmt.format(start),
                    fmt.format(end)
            ));
            start = LocalDateTime.of(
                    start.toLocalDate().plusDays(days)  // + days
                            .plusDays(ONE),             // + 1
                    LocalTime.MIN
            );

            if (start.isAfter(edt)) {
                sign = false;                           // 退出 (临界补充点)
            }
        }

        return list;
    }

}
