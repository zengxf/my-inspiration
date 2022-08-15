package cn.zxf.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
@Slf4j
public class ObjectUtilsTest {

    @Test
    public void isNullOrBlank() {
        String str = "";
        log.info("[{}] isNullOrBlank: [{}]", str, ObjectUtils.isNullOrBlank(str));
    }

}