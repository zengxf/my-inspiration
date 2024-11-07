package cn.zxf.utils.ioc;

import org.junit.Test;

public class IocDemoUtilsTest {

    @Test
    public void mainTest() {
        IocDemoUtilsCfg cfg = new IocDemoUtilsCfg();
        cfg.setEncrypt(true);
        cfg.setSecret("abc-123-000");

        new IocDemoUtils(cfg);

        String msg = "test-0000-中文测试-1111";

        byte[] b1 = msg.getBytes();
        byte[] b2 = IocDemoUtils.encrypt(b1);
        byte[] b3 = IocDemoUtils.decrypt(b2);

        String msg1 = new String(b2);
        System.out.println(msg1);

        String msg2 = new String(b3);
        System.out.println(msg2);
    }

}