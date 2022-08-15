package cn.zxf.utils;

import cn.zxf.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
@Slf4j
public class CollStreamUtilsTest {

    @Test
    public void listToMap() {
        List<UserVo> list = UserVo.ofList3();
        Map<String, UserVo> map = CollStreamUtils.listToMap(list, UserVo::getId);
        map.forEach((k, v) -> log.info("k: [{}] -> v: [{}]", k, v));
    }

}