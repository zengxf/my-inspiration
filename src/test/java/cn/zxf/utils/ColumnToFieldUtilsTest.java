package cn.zxf.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/20.
 */
@Slf4j
public class ColumnToFieldUtilsTest {

    @Test
    public void toField() {
        String column;

        column = "abc_def_3_test";
        log.info("{} => {}", column, ColumnToFieldUtils.toField(column));

        column = "abc_def_3_test_";
        log.info("{} => {}", column, ColumnToFieldUtils.toField(column));

        column = "abc_def_3test";
        log.info("{} => {}", column, ColumnToFieldUtils.toField(column));

        column = "abc_def3_test";
        log.info("{} => {}", column, ColumnToFieldUtils.toField(column));
    }

}