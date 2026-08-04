package cn.zxf.utils.concurrent;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.zxf.common.constants.CommonConstant;
import cn.zxf.spring.model.CurContext;
import cn.zxf.utils.AssertUtils;
import cn.zxf.utils.CurContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.concurrent.*;

/**
 * 线程帮助类
 * <pre>
 * HuTool 的 {@link ThreadUtil} 类的方法，没有传递上下文信息，
 *   下游调用时，traceId 等链路信息丢失。
 * --------------------------------------------------
 * 注：默认使用 {@link #IO_POOL} 线程池。
 * </pre>
 * ZXF 创建于 2025/4/24
 */
@Slf4j
public class ThreadUtils {

    /**
     * 执行异步任务
     * <br>
     * 仅用于适配已有的代码：
     * <code> ThreadUtil.execAsync(run); </code>
     * <br>
     * 新代码应使用 {@link #execute} 方法
     */
    public static Future<?> execAsync(Runnable runnable) {
        AssertUtils.notNull(runnable, "要执行的任务不能为空");
        CurContext passCtx = getContextOrNew();
        Runnable wrap = () -> { // 封装下，将上下文传递进去
            CurContextUtils.set(passCtx);
            MDC.put(CommonConstant.TRACE_ID_KEY, passCtx.getTraceId());
            runnable.run();
        };
        return defPool().submit(wrap);
    }

    /*** 异步执行任务 */
    public static void execute(Runnable runnable) {
        execute(defPool(), runnable);
    }

    /*** 异步执行任务（给定线程池） */
    public static void execute(ThreadPoolExecutor pool, Runnable runnable) {
        AssertUtils.notNull(pool, "用于执行任务的线程池不能为空");
        AssertUtils.notNull(runnable, "要执行的任务不能为空");
        CurContext passCtx = getContextOrNew();
        Runnable wrap = () -> { // 封装下，将上下文传递进去
            CurContextUtils.set(passCtx);
            MDC.put(CommonConstant.TRACE_ID_KEY, passCtx.getTraceId());
            runnable.run();
        };
        pool.execute(wrap);
    }

    /*** 异步执行回调任务，返回未来结果句柄 */
    public static <V> Future<V> submit(Callable<V> callable) {
        return submit(defPool(), callable);
    }

    /*** 异步执行回调任务，返回未来结果句柄（给定线程池） */
    public static <V> Future<V> submit(ThreadPoolExecutor pool, Callable<V> callable) {
        AssertUtils.notNull(pool, "用于执行回调任务的线程池不能为空");
        AssertUtils.notNull(callable, "要执行的回调任务不能为空");
        CurContext passCtx = getContextOrNew();
        Callable<V> wrap = () -> { // 封装下，将上下文传递进去
            CurContextUtils.set(passCtx);
            MDC.put(CommonConstant.TRACE_ID_KEY, passCtx.getTraceId());
            return callable.call();
        };
        return pool.submit(wrap);
    }


    // ------------ 延迟执行 ------------

    /*** 异步延迟执行任务 (2s) */
    public static ScheduledFuture<?> delayExecute(Runnable runnable) {
        return delayExecute(DELAY_POOL, DELAY_MS, runnable);
    }

    /*** 异步延迟执行任务 */
    public static ScheduledFuture<?> delayExecute(int delayMs, Runnable runnable) {
        return delayExecute(DELAY_POOL, delayMs, runnable);
    }

    /*** 异步延迟执行任务 */
    public static ScheduledFuture<?> delayExecute(ScheduledThreadPoolExecutor pool, int delayMs, Runnable runnable) {
        AssertUtils.notNull(pool, "用于执行任务的线程池不能为空");
        AssertUtils.notNull(runnable, "要执行的任务不能为空");
        CurContext passCtx = getContextOrNew();
        Runnable wrap = () -> { // 封装下，将上下文传递进去
            CurContextUtils.set(passCtx);
            MDC.put(CommonConstant.TRACE_ID_KEY, passCtx.getTraceId());
            runnable.run();
        };
        return pool.schedule(wrap, delayMs, TimeUnit.MILLISECONDS);
    }


    // ------------ 安全退出 ------------

    /*** 关闭线程池 */
    public static void shutdown() {
        shutdownPool(IO_POOL, "默认 IO 类型线程池");
        shutdownPool(DELAY_POOL, "默认延迟任务线程池");
    }


    // ------------ 辅助方法 ------------

    // 默认线程池
    private static ThreadPoolExecutor defPool() {
        return IO_POOL;
    }

    // 获取上下文，没有则创建个新的
    private static CurContext getContextOrNew() {
        CurContext ctx = CurContextUtils.getOrNull();
        if (ctx != null)
            return ctx;
        // 单元测试时，可能会为空
        log.warn("Context 为空");
        return new CurContext();
    }

    private static void shutdownPool(ExecutorService pool, String poolName) {
        if (pool == null || pool.isShutdown()) {
            return;
        }

        int timeout = 30;
        if (pool instanceof ScheduledThreadPoolExecutor) {
            timeout = 60;
        }

        pool.shutdown();
        try {
            if (!pool.awaitTermination(timeout, TimeUnit.SECONDS)) {
                log.warn("{} 未能在 {} 内正常关闭，强制中断", poolName, 5);
                pool.shutdownNow();
                if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.error("{} 强制关闭失败", poolName);
                }
            } else {
                log.info("{} 已正常关闭", poolName);
            }
        } catch (InterruptedException e) {
            log.warn("{} 关闭被中断，强制中断", poolName);
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // ------------ 常量定义 ------------

    private static final int DELAY_MS = 2000;           // 默认延迟时间
    private static final int CPU_CORE_SIZE = Runtime.getRuntime().availableProcessors();    // CPU 型任务核心线程数
    private static final int IO_CORE_SIZE = Runtime.getRuntime().availableProcessors() * 4; // IO  型任务核心线程数
    private static final long KEEP_ALIVE_TIME = 60L;    // 线程最大空闲时间 (单位：秒)
    private static final int QUEUE_SIZE = 2000;         // 任务队列大小
    /*** IO 类型线程池 */
    private static ThreadPoolExecutor IO_POOL = new ThreadPoolExecutor(
            IO_CORE_SIZE,
            IO_CORE_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            new ThreadFactoryBuilder().setNamePrefix("zxf-io-pool-thread-").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );
    /*** 延迟型线程池 */
    private static ScheduledThreadPoolExecutor DELAY_POOL = new ScheduledThreadPoolExecutor(
            CPU_CORE_SIZE,
            new ThreadFactoryBuilder().setNamePrefix("zxf-delay-pool-thread-").build()
    );


}
