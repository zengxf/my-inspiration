package cn.zxf.sql;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
@Slf4j
public class CriteriaListOptTest {

    @Test
    public void toAnd() {
        CriteriaListOpt list = CriteriaListOpt.of();
        CriteriaOpt.ofIn("id", "22, 33, 44")
                .ifPresent(list::add);
        CriteriaOpt.ofEq("status", 1)
                .ifPresent(list::add);
        CriteriaOpt.ofLte("age", 32)
                .ifPresent(list::add);
        CriteriaOpt.ofGte("age", 66)
                .ifPresent(list::add);
        CriteriaOpt.ofIsNull("name")
                .ifPresent(list::add);
        SqlCriteria criteria = list.toAnd();
        log.info("sql: {}", criteria.sql);
        log.info("args: {}", criteria.args);
    }

}