package cn.zxf.utils.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试 {@link ThreadUtils}
 */
@Slf4j
public class ThreadUtilsTest {

    @Test
    public void execute() {
        // Context ctx = new Context();
        // ctx.setTraceId("test-11");
        // ContextUtils.set(ctx);
        // log.info("trace-id-1: [{}]", ContextUtils.get().getTraceId());

        Runnable run = () -> {
            // log.info("trace-id-2: [{}]", ContextUtils.get().getTraceId());
            log.info("exe ...");
        };
        ThreadUtils.execute(run);
    }

    @Test
    public void delayExecute() throws InterruptedException {
        // Context ctx = new Context();
        //
        // UserInfo user = new UserInfo();
        // user.setId("test-001");
        // ctx.setUser(user);
        //
        // ctx.setTraceId("test-xx-xx-11");
        // ContextUtils.set(ctx);
        //
        // log.info("trace-id-1: [{}]", ContextUtils.get().getTraceId());
        // log.info("user-id-1: [{}]", ContextUtils.get().getUserId());

        Runnable run = () -> {
            // log.info("trace-id-2: [{}]", ContextUtils.get().getTraceId());
            // log.info("user-id-2: [{}]", ContextUtils.get().getUserId());
            log.info("delay exe ...");
        };

        ThreadUtils.delayExecute(1000, run);
        ThreadUtils.delayExecute(1010, run);
        ThreadUtils.delayExecute(1020, run);

        Thread.sleep(2000L);
    }

    @Test
    public void executeEmbed() {
        // Context ctx = new Context();
        //
        // UserInfo user = new UserInfo();
        // user.setId("test-001");
        // ctx.setUser(user);
        //
        // ctx.setTraceId("test-11");
        // ContextUtils.set(ctx);
        //
        // log.info("trace-id-1: [{}]", ContextUtils.get().getTraceId());
        // log.info("user-id-1: [{}]", ContextUtils.get().getUserId());

        Runnable run = () -> {
            // log.info("trace-id-2: [{}]", ContextUtils.get().getTraceId());
            // log.info("user-id-2: [{}]", ContextUtils.get().getUserId());
            log.info("embed exe ...");
        };
        testSubmitOA("test-88-99", run);
    }

    @Test
    public void futureGet() throws ExecutionException, InterruptedException {
        Future<String> strFut = ThreadUtils.submit(() -> {
            Thread.sleep(50);
            return "OK";
        });

        log.info("res: [{}]", strFut.get());
        log.info("res: [{}]", strFut.get());
    }

    private void testSubmitOA(String userId, Runnable run) {
        // Context old = ContextUtils.get();
        // Context ctx = new Context();
        // if (old != null) {
        //     BeanUtil.copyProperties(old, ctx);
        // }
        //
        // UserInfo user = new UserInfo();
        // user.setId(userId);
        // ctx.setUser(user);
        //
        // ContextUtils.set(ctx);

        log.info("user id: [{}]", userId);

        ThreadUtils.execute(run);
    }


}