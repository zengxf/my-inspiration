package cn.zxf.utils.ioc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IocDemoUtils {

    private static IocDemoUtilsCfg cfg;

    public IocDemoUtils(IocDemoUtilsCfg iocCfg) {
        cfg = iocCfg; // 将 Spring IOC 的配置赋值给静态变量
    }

    public static void use() {
        log.info("cfg: [{}]", cfg);
    }

}
