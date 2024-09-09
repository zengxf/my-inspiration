package cn.zxf.utils;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.Test;

public class UseTimeStatsTest {

    static {
        UseTimeStats.ENABLE = true;  // 开启统计总开关
    }

    @Test
    public void start() {
        UseTimeStats stats = UseTimeStats.start("单元测试");

        ThreadUtil.sleep(600L); // 模拟耗时方法
        stats.outLv2("A-A");

        ThreadUtil.sleep(500L); // 模拟耗时方法
        stats.outLv2("A-B");

        ThreadUtil.sleep(400L); // 模拟耗时方法
        stats.outLv2("A-C");

        stats.outLv1("B");

        ThreadUtil.sleep(200L); // 模拟耗时方法
        stats.outLv2("B-A");

        ThreadUtil.sleep(300L); // 模拟耗时方法
        stats.outLv2("B-B");

        ThreadUtil.sleep(100L); // 模拟耗时方法
        stats.outLv1("B-结束");
    }

}