package cn.zxf.spring.jdbc;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

/**
 * 数据库密码解密处理器
 * <p/>
 * Created by ZXFeng on 2024/10/21
 */
@Component
@Slf4j
public class DBDecryptPostProcessor implements BeanPostProcessor {

    @Value("${my.db.encrypt:false}")
    private boolean isEncrypt;

    /*** 解密逻辑体 */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof DataSourceProperties dbProp)) {
            return bean;    // 不处理
        }

        if (!this.isEncrypt) {
            return bean;    // 不处理
        }

        log.info("开始解密处理");

        String pwd = dbProp.getPassword();
        dbProp.setPassword(this.decryptPwd(pwd));

        return dbProp;
    }

    private String decryptPwd(String pwd) {
        if (StrUtil.isEmpty(pwd)) {
            return pwd;
        }
        try {
            String dePwd = pwd; // TODO 解密处理
            log.debug("解密密码成功，长度：[{}]", StrUtil.length(dePwd));
            return dePwd;
        } catch (RuntimeException e) {
            throw new ApplicationContextException("解密失败！", e);
        }
    }

}
