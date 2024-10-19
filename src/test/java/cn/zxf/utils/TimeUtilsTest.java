package cn.zxf.utils;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;


@Slf4j
public class TimeUtilsTest {

    @Test
    public void toDateMax() {
        Date date = TimeUtils.toDateMax(LocalDate.now());
        log.info(TimeUtils.formatFullDate(date));
    }

    @Test
    public void toDateMin() {
        Date date = TimeUtils.toDateMin(LocalDate.now());
        log.info(TimeUtils.formatFullDate(date));
    }

    @Test
    public void toDateMin2() {
        Timestamp now = TimeUtils.curTime();
        Date date = TimeUtils.toDateMin(now);
        log.info(TimeUtils.formatFullDate(date));
    }

    @Test
    public void curTime() {
        Timestamp now = TimeUtils.curTime();
        log.info(TimeUtils.formatFullDate(now));
    }

    @Test
    public void toTimestamp() {
        Date now = new Date();
        Timestamp ts = TimeUtils.toTimestamp(now);
        log.info(TimeUtils.formatFullDate(ts));
    }

    @Test
    public void toTimestampMin() {
        LocalDate now = LocalDate.now();
        Timestamp ts = TimeUtils.toTimestampMin(now);
        log.info(TimeUtils.formatFullDate(ts));
    }

    @Test
    public void toLocalDate() {
        Timestamp now = TimeUtils.curTime();
        log.info("{}", TimeUtils.toLocalDate(now));
    }

    @Test
    public void max() {
        Date now = new Date();
        Date dt2 = new Date(System.currentTimeMillis() + 600000);
        Date max = TimeUtils.max(now, dt2);
        log.info(TimeUtils.formatFullDate(max));
    }

    @Test
    public void min() {
        Date now = new Date();
        Date dt2 = new Date(System.currentTimeMillis() - 600000);
        Date min = TimeUtils.min(now, dt2);
        log.info(TimeUtils.formatFullDate(min));
    }

    @Test
    public void min2() {
        LocalDate now = LocalDate.now();
        LocalDate dt2 = LocalDate.now().minusDays(3);
        log.info("{}", TimeUtils.min(now, dt2));
    }

    @Test
    public void diffMonths() {
        Timestamp start = TimeUtils.toTimestampMin(LocalDate.now().minusMonths(1));
        Timestamp end = TimeUtils.toTimestampMin(LocalDate.now().plusMonths(1));
        log.info("diffMonths: [{}]", TimeUtils.diffMonths(start, end));
    }

    @Test
    public void formatPeriod() {
        {
            Timestamp start = TimeUtils.toTimestampMin(LocalDate.now().minusMonths(1));
            Timestamp end = TimeUtils.toTimestampMin(LocalDate.now().plusDays(515));
            log.info("Period: [{}]", TimeUtils.formatPeriod(start, end));
        }
        {
            Timestamp start = TimeUtils.toTimestampMin(LocalDate.now().minusMonths(1));
            Timestamp end = TimeUtils.toTimestampMin(LocalDate.now().plusDays(56));
            log.info("Period: [{}]", TimeUtils.formatPeriod(start, end));
        }
        {
            Timestamp start = TimeUtils.toTimestampMin(LocalDate.now());
            Timestamp end = TimeUtils.toTimestampMin(LocalDate.now().plusDays(15));
            log.info("Period: [{}]", TimeUtils.formatPeriod(start, end));
        }
    }

}