package cn.zxf.utils.ioc;

import org.junit.Test;

public class IocDemoUtilsTest {

    @Test
    public void mainTest() {
        IocDemoUtilsCfg cfg = new IocDemoUtilsCfg();
        cfg.setEncrypt(true);
        cfg.setSecret("abc-123-000");

        new IocDemoUtils(cfg);

        IocDemoUtils.use();
    }

}