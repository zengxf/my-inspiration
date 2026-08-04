package cn.zxf.spring.context;

import cn.zxf.utils.concurrent.ThreadUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 线程池处理者
 * <p/>
 * Created by ZXFeng on 2026/8/4
 */
@Component
@Slf4j
public class ThreadPoolHandler {

    @PostConstruct
    public void init() {
        log.info("线程池处理者 - 初始化。。。");
        ThreadUtils.execAsync(() -> log.info("App 初始化时，异步执行测试！"));
    }

    @PreDestroy
    public void destroy() {
        log.info("App 销毁时，线程关闭！");
        ThreadUtils.shutdown();
    }

}
