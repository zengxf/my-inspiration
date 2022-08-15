package cn.zxf.sql;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.zxf.utils.ObjectUtils;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 条件 Optional
 * <br/>
 * Created by ZXFeng on  2022/6/28.
 */
@AllArgsConstructor
public class CriteriaOpt {

    public static final CriteriaOpt EMPTY = new CriteriaOpt(null, null, null);

    public final String field;
    public final CriteriaType type;
    public final Object[] args;


    /*** 实例化字段 eq 条件 */
    public static CriteriaOpt ofEq(String field, Object value) {
        if (ObjectUtils.isNullOrBlank(value))
            return EMPTY;
        return new CriteriaOpt(field, CriteriaType.EQ, new Object[]{value});
    }

    /*** 实例化字段 ne 条件 */
    public static CriteriaOpt ofNe(String field, Object value) {
        if (ObjectUtils.isNullOrBlank(value))
            return EMPTY;
        return new CriteriaOpt(field, CriteriaType.NE, new Object[]{value});
    }

    /*** 实例化字段 lte 条件 */
    public static CriteriaOpt ofLte(String field, Object value) {
        if (ObjectUtils.isNullOrBlank(value))
            return EMPTY;
        return new CriteriaOpt(field, CriteriaType.LTE, new Object[]{value});
    }

    /*** 实例化字段 gte 条件 */
    public static CriteriaOpt ofGte(String field, Object value) {
        if (ObjectUtils.isNullOrBlank(value))
            return EMPTY;
        return new CriteriaOpt(field, CriteriaType.GTE, new Object[]{value});
    }

    /*** 实例化字段 gt 条件 */
    public static CriteriaOpt ofGt(String field, Object value) {
        if (ObjectUtils.isNullOrBlank(value))
            return EMPTY;
        return new CriteriaOpt(field, CriteriaType.GT, new Object[]{value});
    }

    /*** 实例化字段 in 条件（逗号分隔） */
    public static CriteriaOpt ofIn(String field, String commaSplitStr) {
        if (StrUtil.isEmpty(commaSplitStr))
            return EMPTY;
        String[] strArr = commaSplitStr.split("\\s*,\\s*");   // 逗号分隔
        return new CriteriaOpt(field, CriteriaType.IN, strArr);     // 打平，直接传数组
    }

    /*** 实例化字段 in 条件 */
    public static CriteriaOpt ofIn(String field, List<? extends Object> values) {
        if (CollectionUtil.isEmpty(values))
            return EMPTY;
        Object[] argArr = values.toArray();
        return new CriteriaOpt(field, CriteriaType.IN, argArr);     // 打平，直接传数组
    }

    /*** 实例化字段 in 条件 */
    public static CriteriaOpt ofIn(String field, Set<? extends Object> values) {
        if (CollectionUtil.isEmpty(values))
            return EMPTY;
        Object[] argArr = values.toArray();
        return new CriteriaOpt(field, CriteriaType.IN, argArr);     // 打平，直接传数组
    }

    /*** 实例化字段 in 条件，空的话就用毒药使查不出 */
    public static CriteriaOpt ofIn(String field, List<? extends Object> values, Object bane) {
        Object[] argArr;
        if (CollectionUtil.isEmpty(values))
            argArr = new Object[]{bane};
        else
            argArr = values.toArray();
        return new CriteriaOpt(field, CriteriaType.IN, argArr);     // 打平，直接传数组
    }

    /*** 实例化字段 is null 条件 */
    public static CriteriaOpt ofIsNull(String field) {
        return new CriteriaOpt(field, CriteriaType.IS_NULL, null);
    }

    // --------

    /*** 是否非空 */
    public boolean isNotEmpty() {
        return this != EMPTY;
    }

    /*** 转换成 SQL */
    public String toSql() {
        if (type != CriteriaType.IN)
            return String.format("%s %s", field, type.operator);
        // ref: https://www.baeldung.com/spring-jdbctemplate-in-list
        String inSql = String.join(",", Collections.nCopies(args.length, "?"));
        return String.format("%s %s (%s)", field, type.operator, inSql);
    }

    /*** 非空则消费 */
    public void ifPresent(Consumer<CriteriaOpt> consumer) {
        if (this != EMPTY)
            consumer.accept(this);
    }

}
