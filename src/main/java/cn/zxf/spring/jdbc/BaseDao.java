package cn.zxf.spring.jdbc;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import java.util.List;

/**
 * 抽象通用 Dao
 * <br/>
 * Created by ZXFeng on  2022/6/28.
 */
@Slf4j
public abstract class BaseDao {

    @Autowired
    protected JdbcTemplate jdbc;


    /*** 查询总记录数 */
    protected long findTotal(String sql, List<Object> argList) {
        this.logDebug("findTotal", sql, argList);
        Object[] args = {};
        if (CollUtil.isNotEmpty(argList)) {
            args = argList.toArray();
        }
        return jdbc.queryForObject(sql, Long.class, args);
    }

    /*** 查询结果集 */
    protected <T> List<T> findList(String sql, List<Object> argList, Class<T> clazz) {
        return this.findList(sql, argList, BeanPropertyRowMapper.newInstance(clazz));
    }

    /**
     * 查询结果集（有特殊业务字段）。
     * <p/>
     * 转换器：{@link SpecialBizRowMapper}
     */
    protected <T> List<T> findSpecialList(String sql, List<Object> argList, Class<T> clazz) {
        return this.findList(sql, argList, SpecialBizRowMapper.newInstance(clazz));
    }

    /*** 查询结果集 */
    private <T> List<T> findList(String sql, List<Object> argList, RowMapper<T> rowMapper) {
        this.logDebug("findList", sql, argList);
        Object[] args = {};
        if (CollUtil.isNotEmpty(argList)) {
            args = argList.toArray();
        }
        return jdbc.query(sql, args, rowMapper);
    }

    /*** 获取单条记录 */
    protected <T> T findOne(String sql, List<Object> argList, Class<T> clazz) {
        return this.findOne(sql, argList, BeanPropertyRowMapper.newInstance(clazz));
    }

    /*** 获取单条记录 */
    private <T> T findOne(String sql, List<Object> argList, RowMapper<T> rowMapper) {
        this.logDebug("findOne", sql, argList);
        Object[] args = {};
        if (CollUtil.isNotEmpty(argList)) {
            args = argList.toArray();
        }
        // 优化：指定 List 容量，避免内存占用
        RowMapperResultSetExtractor<T> extractor = new RowMapperResultSetExtractor<>(rowMapper, 1);
        List<T> list = jdbc.query(sql, extractor, args);
        return CollUtil.getFirst(list);
    }

    // 打印 SQL 和参数
    private void logDebug(String sign, String sql, List<Object> argList) {
        log.debug("{}-sql: \n     {} \n---------\n->   args: {} \n---------\n", sign, sql, argList);
    }

}
