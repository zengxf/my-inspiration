package cn.zxf.utils.bizconfig;

import cn.zxf.utils.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 这个可以理解为具体的某个业务配置帮助类
 * <p/>
 * ZXF 创建于 2025/3/15
 */
@Slf4j
public class TestConfigUtils extends BizConfigUtils {

    private static final String FILE = "test.json.js";


    @Test
    public void test() {
        String json = readJson(FILE);
        log.info("json: \n\n{}\n\n", json);

        User user = JsonUtils.serializable(json, User.class);
        log.info("user: \n\n{}\n\n", user);
    }


    /*** 可以理解为某个业务配置项 */
    @Data
    static class User {
        String name;
        Integer age;
    }

}
