package cn.zxf.spring.jdbc;

import cn.zxf.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/16.
 */
@Slf4j
public class SpecialBizRowMapperTest {

    @Test
    public void test() {
        SpecialBizRowMapper<UserVo> mapper = SpecialBizRowMapper.newInstance(UserVo.class);
        log.info("mapper: [{}]", mapper);
    }

}