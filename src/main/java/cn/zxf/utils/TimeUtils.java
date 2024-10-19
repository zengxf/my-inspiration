package cn.zxf.utils;


import jakarta.annotation.Nullable;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;


/**
 * 日期、时间帮助类
 * <p/>
 * Created by ZXFeng on 2024/10/19
 */
public class TimeUtils {

    private static final String FULL_MONTH_FMT = "yyyy-MM";
    private static final String FULL_DATE_FMT = "yyyy-MM-dd";
    private static final String FULL_DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss";
    private static final String YEARS_FMT = "%d年%d个月%d天";
    private static final String MONTHS_FMT = "%d个月%d天";
    private static final String DAYS_FMT = "%d天";


    // --------------------

    /*** LocalDate 转 Date (使用 23:59:59 做时间) */
    public static Date toDateMax(LocalDate localDate) {
        AssertUtils.notNull(localDate, "(toDateMax) 日期不能为空！");
        LocalDateTime maxTime = LocalDateTime.of(localDate, LocalTime.MAX);
        return toDate(maxTime);
    }

    /*** LocalDate 转 Date (使用 00:00:00 做时间) */
    public static Date toDateMin(LocalDate localDate) {
        AssertUtils.notNull(localDate, "(toDateMin) 日期不能为空！");
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /*** Timestamp 转 Date (取日期整数，相当于凌晨 0 点：00:00:00) */
    public static Date toDateMin(Timestamp timestamp) {
        AssertUtils.notNull(timestamp, "(toDateMin) 时间戳不能为空！");
        return toDateMin(timestamp.toLocalDateTime().toLocalDate());
    }

    /*** LocalDateTime 转 Date */
    public static Date toDate(LocalDateTime localDateTime) {
        AssertUtils.notNull(localDateTime, "(toDate) 日期时间不能为空！");
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    // --------------------

    /*** 返回当前时间 */
    public static Timestamp curTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /*** Date 转 Timestamp */
    public static Timestamp toTimestamp(Date date) {
        AssertUtils.notNull(date, "(toTimestamp) 日期时间不能为空！");
        return new Timestamp(date.getTime());
    }

    /*** LocalDate 转 Timestamp (使用 00:00:00 做时间) */
    public static Timestamp toTimestampMin(LocalDate localDate) {
        AssertUtils.notNull(localDate, "(toTimestampMin) 日期不能为空！");
        LocalDateTime dt = LocalDateTime.of(localDate, LocalTime.MIN);
        return Timestamp.valueOf(dt);
    }

    /*** LocalDateTime 转 Timestamp */
    public static Timestamp toTimestamp(LocalDateTime dateTime) {
        AssertUtils.notNull(dateTime, "(toTimestamp) 日期时间不能为空！");
        return Timestamp.valueOf(dateTime);
    }


    // --------------------

    /*** Timestamp 转 LocalDate */
    public static LocalDate toLocalDate(Timestamp timestamp) {
        AssertUtils.notNull(timestamp, "(toLocalDate) 时间戳不能为空");
        LocalDate ld = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ld;
    }

    /*** Date 转 LocalDate */
    public static LocalDate toLocalDate(Date date) {
        AssertUtils.notNull(date, "(toLocalDate) 日期时间不能为空");
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ld;
    }


    // --------------------

    /*** 求最大值 */
    public static Date max(Date d1, Date d2) {
        AssertUtils.notNull(d1, "(max) 日期时间 d1 不能为空");
        AssertUtils.notNull(d2, "(max) 日期时间 d2 不能为空");
        if (d1.compareTo(d2) > 0) {
            return d1;
        }
        return d2;
    }

    /*** 求最小值 */
    public static Date min(Date d1, Date d2) {
        AssertUtils.notNull(d1, "(min) 日期时间 d1 不能为空");
        AssertUtils.notNull(d2, "(min) 日期时间 d2 不能为空");
        if (d1.compareTo(d2) > 0) {
            return d2;
        }
        return d1;
    }

    /*** 获取最小的（最前的）日期 */
    public static LocalDate min(LocalDate d1, LocalDate d2) {
        AssertUtils.notNull(d1, "(min) 日期 d1 不能为空");
        AssertUtils.notNull(d2, "(min) 日期 d2 不能为空");
        return d1.isBefore(d2) ? d1 : d2;
    }


    // --------------------

    /*** 计算月份差 */
    public static int diffMonths(Timestamp start, Timestamp end) {
        AssertUtils.notNull(start, "(diffMonths) 时间戳 start 不能为空");
        AssertUtils.notNull(end, "(diffMonths) 时间戳 end 不能为空");
        LocalDate beginLd = toLocalDate(start);
        LocalDate endLd = toLocalDate(end);
        return (int) ChronoUnit.MONTHS.between(beginLd, endLd);
    }


    // --------------------

    /*** 判断  start <= v <= end  */
    public static boolean isBetween(LocalDate v, LocalDate start, LocalDate end) {
        AssertUtils.notNull(v, "(isBetween) 日期 v 不能为空");
        AssertUtils.notNull(start, "(isBetween) 日期 start 不能为空");
        AssertUtils.notNull(end, "(isBetween) 日期 end 不能为空");
        return v.compareTo(start) >= 0 && v.compareTo(end) <= 0;
    }

    /*** 判断  start <= v <= end  */
    public static boolean isBetween(LocalTime v, LocalTime start, LocalTime end) {
        AssertUtils.notNull(v, "(isBetween) 时间 v 不能为空");
        AssertUtils.notNull(start, "(isBetween) 时间 start 不能为空");
        AssertUtils.notNull(end, "(isBetween) 时间 end 不能为空");
        return v.compareTo(start) >= 0 && v.compareTo(end) <= 0;
    }

    /*** 判断  start <= v <= end  */
    public static boolean isBetween(Date v, Date start, Date end) {
        AssertUtils.notNull(v, "(isBetween) 日期时间 v 不能为空");
        AssertUtils.notNull(start, "(isBetween) 日期时间 start 不能为空");
        AssertUtils.notNull(end, "(isBetween) 日期时间 end 不能为空");
        return v.compareTo(start) >= 0 && v.compareTo(end) <= 0;
    }

    /*** 判断  value < base */
    public static boolean isBefore(Timestamp value, Timestamp base) {
        AssertUtils.notNull(value, "(isBefore) 时间戳 value 不能为空");
        AssertUtils.notNull(base, "(isBefore) 时间戳 base 不能为空");
        return value.before(base);
    }

    /*** 判断参数日期是否小于当前日期 */
    public static boolean ltNow(Date date) {
        AssertUtils.notNull(date, "(ltNow) 日期不能为空");
        Date now = new Date();
        return now.after(date);
    }

    /*** 判断参数日期是否大于当前日期 */
    public static boolean gtNow(Date date) {
        AssertUtils.notNull(date, "(gtNow) 日期不能为空");
        Date now = new Date();
        return date.after(now);
    }

    /*** 判断 v 是否不晚于 base */
    public static boolean le(Date v, Date base) {
        AssertUtils.notNull(v, "(le) 日期时间 v 不能为空");
        AssertUtils.notNull(base, "(le) 日期时间 base 不能为空");
        return v.compareTo(base) <= 0;
    }

    /*** 判断 v 是否晚于(不早于) base */
    public static boolean ge(Date v, Date base) {
        AssertUtils.notNull(v, "(ge) 日期时间 v 不能为空");
        AssertUtils.notNull(base, "(ge) 日期时间 base 不能为空");
        return v.compareTo(base) >= 0;
    }

    /*** 判断 v < base */
    public static boolean lt(Date v, Date base) {
        AssertUtils.notNull(v, "(lt) 日期时间 v 不能为空");
        AssertUtils.notNull(base, "(lt) 日期时间 base 不能为空");
        return v.compareTo(base) < 0;
    }

    /*** 判断 v > base */
    public static boolean gt(Date v, Date base) {
        AssertUtils.notNull(v, "(gt) 日期时间 v 不能为空");
        AssertUtils.notNull(base, "(gt) 日期时间 base 不能为空");
        return v.compareTo(base) > 0;
    }

    /*** 判断 v 是否早于(不晚于) base */
    public static boolean le(LocalDate v, LocalDate base) {
        AssertUtils.notNull(v, "(le) 日期 v 不能为空");
        AssertUtils.notNull(base, "(le) 日期 base 不能为空");
        return v.compareTo(base) <= 0;
    }

    /*** 判断 v 是否晚于(不早于) base */
    public static boolean ge(LocalDate v, LocalDate base) {
        AssertUtils.notNull(v, "(ge) 日期 v 不能为空");
        AssertUtils.notNull(base, "(ge) 日期 base 不能为空");
        return v.compareTo(base) >= 0;
    }


    // --------------------

    /*** 格式化间隔日期 */
    @Nullable
    public static String formatPeriod(Timestamp begin, Timestamp end) {
        if (Objects.isNull(begin) || Objects.isNull(end)) {
            return null;
        }
        if (begin.compareTo(end) >= 0) {
            return String.format(DAYS_FMT, 0);
        }

        Period between = Period.between(begin.toLocalDateTime().toLocalDate(), end.toLocalDateTime().toLocalDate());
        int years = between.getYears(), months = between.getMonths(), days = between.getDays();
        if (years != 0) {
            return String.format(YEARS_FMT, years, months, days);
        } else {
            if (0 != months) {
                return String.format(MONTHS_FMT, months, days);
            } else {
                return String.format(DAYS_FMT, days);
            }
        }
    }

    /*** 格式化月 (null -> "") */
    public static String formatMonth(@Nullable Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_MONTH_FMT);
        return sdf.format(date);
    }

    /*** 格式化月 */
    public static String formatMonth(Timestamp time) {
        AssertUtils.notNull(time, "(formatMonth) 时间戳不能为空！");
        return new SimpleDateFormat(FULL_MONTH_FMT).format(time);
    }

    /*** 格式化月 */
    public static String formatMonth(LocalDate date) {
        AssertUtils.notNull(date, "(formatMonth) 日期不能为空！");
        return DateTimeFormatter.ofPattern(FULL_MONTH_FMT).format(date);
    }

    /*** 格式化日期 (null -> "") */
    public static String formatDate(@Nullable Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_FMT);
        return sdf.format(date);
    }

    /*** 格式化完整日期时间 (null -> "") */
    public static String formatFullDate(@Nullable Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_TIME_FMT);
        return sdf.format(date);
    }


    // --------------------

    /*** 转换到凌晨 0 点。 */
    @Nullable
    public static Date toEarlyMorning(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        LocalDate ld = toLocalDate(date);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.MIN);
        return toDate(ldt);
    }

    /*** 转换到深夜 23 点 59 分。 */
    @Nullable
    public static Date toLateAtNight(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        LocalDate ld = toLocalDate(date);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.MAX);
        return toDate(ldt);
    }

    /*** 转换到凌晨 0 点。 */
    @Nullable
    public static Timestamp toEarlyMorning(@Nullable Timestamp date) {
        if (date == null) {
            return null;
        }
        LocalDate ld = toLocalDate(date);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.MIN);
        return toTimestamp(ldt);
    }

    /*** 转换到深夜 23 点 59 分。 */
    @Nullable
    public static Timestamp toLateAtNight(@Nullable Timestamp date) {
        if (date == null) {
            return null;
        }
        LocalDate ld = toLocalDate(date);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalTime.MAX);
        return toTimestamp(ldt);
    }

}