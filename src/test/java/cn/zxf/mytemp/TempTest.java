package cn.zxf.mytemp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.scheduling.support.CronExpression;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

/**
 * <p/>
 * Created by ZXFeng on 2024/5/15
 */
@Slf4j
public class TempTest {
    private String testKey = "abc";
    private static String testStaticKey = "abc8888";

    @Test
    public void testDate() {
        LocalDate now = LocalDate.now();

        String paramData = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .toString();

        LocalDate parse = LocalDate.parse(paramData, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate sDate = parse.minusWeeks(1).plusDays(7 - parse.getDayOfWeek().getValue());
        LocalDate eDate = parse.minusWeeks(2).minusDays(parse.getDayOfWeek().getValue() - 1);
        log.info("{}  =>  {}", sDate, eDate);

        LocalDate date;

        date = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        log.info("previousOrSame-周一: [{}]", date); // 上个
        date = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));
        log.info("previousOrSame-周五: [{}]", date); // 上个
        date = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        log.info("previousOrSame-周日: [{}]", date); // 上个

        date = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        log.info("nextOrSame-周一: [{}]", date); // 下个
        date = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        log.info("nextOrSame-周五: [{}]", date); // 下个
        date = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        log.info("nextOrSame-周日: [{}]", date); // 下个

        log.info("--------------");

        date = now.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        log.info("previous-周一: [{}]", date); // 上个
        date = now.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        log.info("previous-周五: [{}]", date); // 上个
        date = now.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        log.info("previous-周日: [{}]", date); // 上个

        date = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        log.info("next-周一: [{}]", date); // 下个
        date = now.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        log.info("next-周五: [{}]", date); // 下个
        date = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        log.info("next-周日: [{}]", date); // 下个
    }

    @Test
    public void testSqlDate() {
        Date date = new java.sql.Date(System.currentTimeMillis());
        log.info("date: [{}]", date);
    }


    @Test
    public void testWeek() {
        {
            String date = "2023-01-01";
            LocalDate localDate = LocalDate.parse(date);
            WeekFields weekFields = WeekFields.ISO;
            int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
            log.info("date: [{}] => [{}]", date, weekNumber);
        }
        {
            String date = "2022-12-31";
            LocalDate localDate = LocalDate.parse(date);
            WeekFields weekFields = WeekFields.ISO;
            int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
            log.info("date: [{}] => [{}]", date, weekNumber);
        }
        {
            String date = "2025-01-01";
            LocalDate localDate = LocalDate.parse(date);
            WeekFields weekFields = WeekFields.ISO;
            int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
            log.info("date: [{}] => [{}]", date, weekNumber);
        }
        {
            String date = "2024-12-31";
            LocalDate localDate = LocalDate.parse(date);
            WeekFields weekFields = WeekFields.ISO;
            int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
            log.info("date: [{}] => [{}]", date, weekNumber);
        }
    }

    @Test
    public void testDateStr() {
        String day;
        boolean gtLimit;
        {
            day = "2024-03-08";
            gtLimit = day.compareTo("2024-03-15") > 0;
            log.info("day: [{}], gt: [{}]", day, gtLimit);
        }
        {
            day = "2024-03-16";
            gtLimit = day.compareTo("2024-03-15") > 0;
            log.info("day: [{}], gt: [{}]", day, gtLimit);
        }
    }

    @Test
    public void testDateStrList() {
        List<String> list = CollUtil.newArrayList(
                "2024-01-05",
                "2023-06-05",
                null,
                "2025-06-05",
                "2025-04-05"
        );
        String max = list.stream()
                .max(Comparator.nullsFirst(Comparator.naturalOrder()))
                .orElse(null);
        log.info("max: [{}]", max);
    }

    @Test
    public void testField() throws Exception {
        log.info("test: [{}]", this.testKey);
        Field field = TempTest.class.getDeclaredField("testKey");
        log.info("field-type: [{}]", field.getType());
        log.info("field-type is String: [{}]", field.getType() == String.class);
        log.info("field-generic-type: [{}]", field.getGenericType());
        log.info("class-full-name: [{}]", String.class.getName());
    }

    @Test
    public void testMethod() throws Exception {
        Method method = TempTest.class.getMethod("test1");
        log.info("method-name: [{}]", method.getName());
        log.info("method-toString: [{}]", method);
    }

    @Test
    public void test1() {
        String paramData = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString();
        LocalDate parse = LocalDate.parse(paramData, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate start = parse.minusWeeks(1).plusDays(7 - parse.getDayOfWeek().getValue());
        LocalDate end = parse.minusWeeks(2).minusDays(parse.getDayOfWeek().getValue() - 1);
        log.info("paramData: [{}]", paramData);
        log.info("[{} => {}]", start, end);
    }

    @Test
    public void testEnv() {
        String javaHome = System.getenv("java_home");
        log.info("java_home-env: [{}]", javaHome);
    }

    @Test
    public void testMap() {
        Map<String, Boolean> passMap = new ConcurrentHashMap<>();
        String key = "t";

        Boolean v = passMap.putIfAbsent(key, Boolean.TRUE);
        log.info("v => [{}]", v);
        log.info("----------------");

        v = passMap.putIfAbsent(key, Boolean.TRUE);
        log.info("v => [{}]", v);
    }

    @Test
    public void testMonth() {
        String s1 = LocalDate.now().toString();
        LocalDate parse = LocalDate.parse(s1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate sDate = parse.minusWeeks(4).minusDays(parse.getDayOfWeek().getValue() - 1);
        LocalDate eDate = parse.minusWeeks(1).plusDays(7 - parse.getDayOfWeek().getValue());
        log.info("{}   =>   {}", sDate, eDate);

        YearMonth month = YearMonth.from(parse);
        log.info("month: [{}]", month);
    }

    @Test
    public void strToTimestamp() {
        LocalDate deadlineDate = LocalDate.now().plusDays(1);
        String deadlineStr = deadlineDate + " " + "23:30" + ":00";
        Timestamp deadline = Timestamp.valueOf(deadlineStr);
        log.info("deadline: [{}]", deadline);
    }

    @Test // 使用率统计时，计算时间段
    public void statDate() {
        {
            LocalDate parse = LocalDate.parse("2024-03-10");
            LocalDate oneWeekEndTime = parse.minusWeeks(1).plusDays(7 - parse.getDayOfWeek().getValue());
            LocalDate twoWeekStartTime = parse.minusWeeks(2).minusDays(parse.getDayOfWeek().getValue() - 1);
            log.info("paramData: [{}]", parse);
            log.info("{} ~ {}", twoWeekStartTime, oneWeekEndTime);
            log.info("---------------------------");
        }
        LocalDate parse = LocalDate.parse("2023-08-27");
        LocalDate oneWeekEndTime = parse.minusWeeks(1).plusDays(7 - parse.getDayOfWeek().getValue());
        LocalDate twoWeekStartTime = parse.minusWeeks(2).minusDays(parse.getDayOfWeek().getValue() - 1);
        log.info("{} ~ {}", twoWeekStartTime, oneWeekEndTime);
        log.info("---------------------------");

        LocalDate date = twoWeekStartTime;
        while (!date.isAfter(oneWeekEndTime)) {
            WeekFields weekFields = WeekFields.ISO;
            int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
            log.info("{}  =>  {}", date, weekNumber);
            date = date.plusDays(1);
        }
    }

    @Test
    public void testStatMonth() {
        String s1 = LocalDate.now().toString();
        LocalDate parse = LocalDate.parse(s1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate sDate = parse.minusWeeks(4).minusDays(parse.getDayOfWeek().getValue() - 1);
        LocalDate eDate = parse.minusWeeks(1).plusDays(7 - parse.getDayOfWeek().getValue());
        log.info("[{}] month: [{}  =>  {}]", parse, sDate, eDate);

        LocalDate now = LocalDate.parse("2023-08-31");
        // YearMonth month = YearMonth.now();
        YearMonth month = YearMonth.parse("2023-08");
        LocalDate end = month.atEndOfMonth();
        log.info("end: [{}] is-eq: [{}]", end, now.isEqual(end));
    }

    @Test
    public void getWeek() {
        LocalDate date = LocalDate.now();
        DayOfWeek week = date.getDayOfWeek();
        log.info("week: [{} - {}]", week, week.getValue());
        log.info("Cron: [{} - {}]", week.getValue(), week.getValue() % 7 + 1);
        log.info("------------");
        Stream.of(DayOfWeek.values())
                .forEach(wk -> {
                    log.info("Cron: [{} - {} - {}]", wk, wk.getValue(), wk.getValue() % 7 + 1);
                });
    }

    @Test
    public void testStrSplit() {
        String str = "ab,,cd,,f,,";
        String[] arr = str.split("\\s*,\\s*");
        log.info("arr: [{}]", Arrays.toString(arr));
    }

    @Test
    public void getStaticField() throws Exception {
        String fieldName = "testStaticKey";
        Field field = TempTest.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        String str = (String) field.get(null);
        log.info("str: [{}]", str);
    }

    @Test
    public void testUseTime() {
        sleep1();
        sleep2();
    }

    private void sleep1() {
        for (int i = 100; i < 200; i++) {
            ThreadUtil.sleep(i);
        }
    }

    private void sleep2() {
        for (int i = 150; i < 300; i++) {
            ThreadUtil.sleep(i);
        }
    }

    @Test
    public void logRunError() {
        RuntimeException e = new RuntimeException("测试 err");
        log.error("err!", e);
    }

    @Test
    public void testLogErrFmt() {
        try {
            throwNullErr(null); // 故意出错
        } catch (Exception e) {
            String s = "X-X-X-X";
            log.info("errStr: \n{}\n{}\n---------------", e, s);    // e 在里面会调用 e.toString() 格式化
            log.info("===========");
            log.info("errStr: \n{}\n{}\n---------------", s, e);    // e 在最后不会格式化，会输出 {}，IDEA 也会有着色提示
            log.info("===========");
            log.info("errStr: \n{}\n---------------", s, e);        // e 不会格式化，无着色提示
        }
    }

    @Test
    public void testErrorToStr() {
        try {
            throwNullErr(null); // 故意出错
        } catch (Exception e) {
            // log.error("err!", e);
            String errStr = ExceptionUtil.stacktraceToString(e);
            // log.info("errStr: \n{}\n---------------", errStr);
            log.info("errStr: \n{}\n---------------", errStr.substring(0, Math.min(512, errStr.length())));
            // log.info("\n errStr: {} => {}\n---------------", e.getMessage(), errStr.replaceAll("\\s+", "\t"));
        }
    }

    static void throwNullErr(Object obj) {
        // log.info("obj: [{}]", obj.toString()); // err
        if (obj == null)
            throw new NullPointerException("obj 为 null");
    }

    static void transErr() {
        try {
            throwNullErr(null);
        } catch (Exception e) {
            log.info("e: [{}]", e.getMessage());
            throw e;
        }
    }

    @Test // 测试是否会改变异常栈
    public void testTransErr() {
        try {
            transErr();
        } catch (Exception e) {
            log.error("出错！", e);  // 结果：不会改变异常栈
        }
    }

    @Test
    public void testStreamLimit() {
        Stream.of(1, 2, 3, 4)
                .limit(10)
                .forEach(i -> log.info("i: [{}]", i));
    }

    @Test
    public void loopNullReturnTest() {
        List<Integer> list = CollUtil.toList(1, null, 3);
        testList(list);
    }

    private void testList(List<Integer> list) {
        for (Integer v : list) {
            log.info("v: [{}]", v);
            if (v == null) {
                return;
            }
            log.info("-------------------");
        }
    }

    @Test
    public void testCorn() {
        {
            String cron = "1 3 * * * ?";
            CronExpression cronExpression = CronExpression.parse(cron);
            LocalDateTime ldt = LocalDateTime.parse("2024-02-29T13:02:00");
            LocalDateTime next = cronExpression.next(ldt);
            log.info("ldt: [{}], next: [{}]", ldt, next);
        }
        {
            String cron = "55 56 * * * ?";
            CronExpression cronExpression = CronExpression.parse(cron);
            LocalDateTime ldt = LocalDateTime.parse("2024-02-29T14:55:44");
            LocalDateTime next = cronExpression.next(ldt);
            log.info("ldt: [{}], next: [{}]", ldt, next);
        }
    }

    @Test
    public void testMock() {
        MockedStatic<CacheUtils> mock = Mockito.mockStatic(CacheUtils.class);
        mock.when(() -> CacheUtils.get(any()))
                .thenReturn("mock-res-999");
        mock.when(() -> CacheUtils.set(any(), any()))
                .then(invocation -> null);

        Object cache = CacheUtils.get("test");
        log.info("cache: [{}]", cache);

        CacheUtils.set("test", 111);
    }

    @Test
    public void testSort() {
        List<Integer> list = CollUtil.toList(1, 2, 3, 6, 9, 4);
        Integer max = list.stream()
                .sorted(Comparator.comparing(Integer::intValue).reversed())
                .findFirst()
                .orElse(null);
        log.info("max: [{}]", max);
    }

    @Test
    public void testCodeBlock() {
        codeBlock(0);
        codeBlock(1);
        codeBlock(2);
        codeBlock(3);
    }

    private void codeBlock(int i) {
        log.info("i: [{}]", i);
        {
            if (i < 1) {
                log.info("00000000000000000");
                return;
            }
        }
        {
            if (i < 2) {
                log.info("11111111111111111");
                return;
            }
        }
        {
            if (i < 3) {
                log.info("22222222222222222");
                return;
            }
        }
        log.info("----------------------");
    }

    @Test
    public void testSortDR() {
        List<DR> list = CollUtil.newArrayList(
                new DR("test-1", 2, 33.3),
                new DR("test-2", 2, 22.3),
                new DR("test-5", 5, 25.3),
                new DR("test-6", 1, 45.6),
                new DR("test-3", 0, 13.3),
                new DR("test-4", 0, 10.3)
        );
        list.sort((r1, r2) -> {
            int bc1 = r1.getFreeCount();
            int bc2 = r2.getFreeCount();
            if (bc1 > 0 && bc2 > 0) {
                return r1.getDistance() > r2.getDistance() ? 1 : -1;
            }
            if (bc1 == 0 && bc2 == 0) {
                return r1.getDistance() > r2.getDistance() ? 1 : -1;
            }
            return bc1 > bc2 ? -1 : 1;
        });
        list.forEach(dr -> log.info("DR: [{}]", dr));
    }

    @Data
    @AllArgsConstructor
    public static class DR {
        private String sign;
        private int freeCount; // 免费数
        /***  距离（米） */
        private double distance;
    }

    static class CacheUtils {
        public static Object get(String key) {
            throw new RuntimeException("xx");
        }

        public static void set(String key, Object data) {
            throw new RuntimeException("xx");
        }
    }

}
