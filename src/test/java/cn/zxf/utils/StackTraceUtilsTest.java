package cn.zxf.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/16.
 */
@Slf4j
public class StackTraceUtilsTest {

    @Test
    public void stackTrace() {
        log.info("0000000000000000");
        mLevel1();
    }

    private void mLevel1() {
        log.info("1111111111111111");
        mLevel2();
    }

    private void mLevel2() {
        log.info("22222222222222222");
        mLevel3();
    }

    private void mLevel3() {
        log.info("33333333333333333");

        String cur = StackTraceUtils.curStackTrace();
        log.info("cur: [{}]", cur);

        String lv0 = StackTraceUtils.stackTrace(0);
        log.info("lv0: [{}]", lv0);

        String lv1 = StackTraceUtils.stackTrace(1);
        log.info("lv1: [{}]", lv1);

        String lv2 = StackTraceUtils.stackTrace(2);
        log.info("lv2: [{}]", lv2);

        String lv3 = StackTraceUtils.stackTrace(3);
        log.info("lv3: [{}]", lv3);
    }

}