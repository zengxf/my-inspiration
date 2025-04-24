package cn.zxf.utils.concurrent;

import cn.hutool.core.thread.ThreadUtil;
import cn.zxf.utils.UseTimeStats;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 可以将此理解为（已优化的）业务类参考
 * <br/>
 * 已优化的业务方法参考：{@link #asyncOptimized()}
 */
@Slf4j
public class AsyncUtilsTest {

    static {
        UseTimeStats.ENABLE = true;  // 开启统计总开关
    }


    // ------------------------------------------

    /**
     * 未优化的业务方法
     */
    @Test
    public void notOptimized() {
        UseTimeStats stats = UseTimeStats.start("未优化的业务方法测试");

        ThreadUtil.sleep(500L); // 耗时逻辑
        System.out.println("N--001");

        ThreadUtil.sleep(500L);
        System.out.println("N--002");

        ThreadUtil.sleep(500L);
        System.out.println("N--003");

        this.notOptimizedOtherOp();

        System.out.println("------------------- End 1 -------------------");
        stats.outLv1("总");
    }

    /*** 未优化的业务方法-其他操作 */
    private void notOptimizedOtherOp() {
        ThreadUtil.sleep(500L);
        System.out.println("N--011");

        ThreadUtil.sleep(500L);
        System.out.println("N--012");

        ThreadUtil.sleep(500L);
        System.out.println("N--013");
    }


    // ------------------------------------------

    /**
     * 异步优化的业务方法
     */
    @Test
    public void asyncOptimized() {
        UseTimeStats stats = UseTimeStats.start("异步优化的业务方法测试");

        AsyncUtils.execute(() -> {
            ThreadUtil.sleep(500L); // 耗时逻辑
            System.out.println("A--A01");
        });

        AsyncUtils.execute(() -> {
            ThreadUtil.sleep(500L);
            System.out.println("A--A02");
        });

        AsyncUtils.execute(() -> {
            ThreadUtil.sleep(500L);
            System.out.println("A--A03");
        });

        this.asyncOptimizedOtherOp();

        AsyncUtils.joinAll();   // 要等待所有的任务完成

        System.out.println("------------------- End 2 -------------------");
        stats.outLv1("总");
    }

    /*** 异步优化的业务方法-其他操作 */
    private void asyncOptimizedOtherOp() {
        AsyncUtils.execute(() -> {
            ThreadUtil.sleep(500L);
            System.out.println("A--A11");
        });

        AsyncUtils.execute(() -> {
            ThreadUtil.sleep(500L);
            System.out.println("A--A12");
        });

        AsyncUtils.execute(() -> {
            ThreadUtil.sleep(500L);
            System.out.println("A--A13");
        });
    }

}