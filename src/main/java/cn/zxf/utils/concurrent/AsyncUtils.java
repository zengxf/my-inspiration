package cn.zxf.utils.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 异步帮助类 (优化接口性能)
 *
 * <p>
 * 注：<br/>
 * 1. 优化的代码片段没有上下文逻辑关联； <br/>
 * 2. 需要调用 {@link #joinAll()} 等待所有子任务完成。
 * </p>
 * <br/>
 * ZXF 创建于 2025/4/24
 */
@Slf4j
public class AsyncUtils {

    /**
     * 用于记录当前线程提交的所有任务执行句柄
     */
    private static final ThreadLocal<List<Future<?>>> futuresTL = ThreadLocal.withInitial(ArrayList::new);


    // --------------------------------------------------

    /**
     * 异步执行代码段
     */
    public static void execute(Runnable runnable) {
        futuresTL.get().add(
                ThreadUtils.execAsync(runnable)
        );
    }

    /**
     * 等待所有子任务完成
     */
    public static void joinAll() {
        futuresTL.get()
                .forEach(taskFuture -> {
                            try {
                                taskFuture.get();
                            } catch (InterruptedException | ExecutionException e) {
                                throw new RuntimeException("异步处理出错", e);
                            }
                        }
                );
    }

}
