package cn.zxf.utils.ioc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "my.test.ioc-demo")
@Data
public class IocDemoUtilsCfg {

    private boolean encrypt;

    private String secret;

}
