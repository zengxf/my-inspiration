package cn.zxf.utils.ioc;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取环境变量用于给静态字段设值
 * <p/>
 * Created by ZXFeng on 2024/11/28
 */
@Component
public class ReadEnvForSetupStatic {

    @Value("${test.config.cfg1")
    private String cfg1;

    public static String G_CFG1;

    @PostConstruct
    public void init() {
        G_CFG1 = this.cfg1;
    }

}
