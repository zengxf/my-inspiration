package cn.zxf.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Slf4j
public class TimePageUtilsTest {

    @Test
    public void pageDateTime_fmtErr() {
        List<String> times = CollUtil.newArrayList(
                "2024",
                "2025"
        );
        List<List<String>> page = TimePageUtils.pageDateTime(times);
        log.info("page-size: [{}]", page.size());
        Assert.assertEquals("结果应为空 (empty)", 0, page.size());
    }

    @Test
    public void pageDateTime_fmtCommon_0() {
        List<String> times = CollUtil.newArrayList(
                "2024-08-12 07:00:00",
                "2024-09-12 23:59:00"
        );
        List<List<String>> page = TimePageUtils.pageDateTime(times);
        log.info("page-size: [{}]", page.size());
        Assert.assertEquals("结果应为空 (empty)", 0, page.size());
    }

    @Test
    public void pageDateTime_fmtCommon_2() {
        List<String> times = CollUtil.newArrayList(
                "2024-08-12 07:00:00",
                "2024-10-12 23:59:00"
        );
        List<List<String>> page = TimePageUtils.pageDateTime(times);
        log.info("page-size: [{}]", page.size());

        page.forEach(subPage ->
                log.info("sub: {}", subPage)
        );

        Assert.assertEquals("结果集应为 2", 2, page.size());
    }

    @Test
    public void pageDateTime_fmtCommon_3() {
        List<String> times = CollUtil.newArrayList(
                "2024-07-15 07:00:00",
                "2024-10-12 23:30:00"
        );
        List<List<String>> page = TimePageUtils.pageDateTime(times);
        log.info("page-size: [{}]", page.size());

        page.forEach(subPage ->
                log.info("sub: {}", subPage)
        );

        Assert.assertEquals("结果集应为 3", 3, page.size());
    }

    @Test
    public void pageDateTime_fmtCommon_threshold() {
        List<String> times = CollUtil.newArrayList(
                "2024-07-01 10:30:30",
                "2024-08-31 23:59:59"
        );
        List<List<String>> page = TimePageUtils.pageDateTime(30, times);
        log.info("page-size: [{}]", page.size());

        page.forEach(subPage ->
                log.info("sub: {}", subPage)
        );

        Assert.assertEquals("结果集应为 2", 2, page.size());
    }

}