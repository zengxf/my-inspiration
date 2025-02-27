package cn.zxf.mytemp;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * <p/>
 * ZXF 创建于 2025/2/27
 */
@Slf4j
public class CompletableFutureTest {

    @Test
    public void handle() {
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("进入 --------------------------");
                    ThreadUtil.sleep(500);
                    // if (Math.random() < 0.5) {
                    //     throw new RuntimeException("error 111");
                    // }
                    return "hello";
                })
                .handleAsync((result, err) -> {
                    if (err != null) {
                        log.error("出错.", err);
                        return 0;
                    }
/*
02-27 16:28:30.301 [ForkJoinPool.commonPool-worker-1] INFO  c.z.m.CompletableFutureTest:21 - 进入 --------------------------
02-27 16:28:30.819 [ForkJoinPool.commonPool-worker-2] INFO  c.z.m.CompletableFutureTest:34 - 成功. result: [hello]
*/
                    log.info("成功. result: [{}]", result);   // 有时同一线程，有时是另一个线程
                    return 1;
                });

        ThreadUtil.sleep(1500);
    }

}
