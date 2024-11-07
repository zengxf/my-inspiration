package cn.zxf.utils.ioc;

import org.springframework.stereotype.Component;

@Component
public class IocDemoUtils {

    private static byte[] state;
    private static IocDemoUtilsCfg cfg;

    public IocDemoUtils(IocDemoUtilsCfg iocCfg) {
        cfg = iocCfg; // 将 Spring IOC 的配置赋值给静态变量
        state = initKey(iocCfg.getSecret()); // 方便静态方法使用
    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] data) {
        return decrypt(data);
    }

    /**
     * 初始化密钥
     */
    private static byte[] initKey(String aKey) {
        byte state[] = new byte[256];
        // ...
        return state;
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] input) {
        if (!cfg.isEncrypt()) {
            return input;
        }
        // ...
        return input;
    }

}
