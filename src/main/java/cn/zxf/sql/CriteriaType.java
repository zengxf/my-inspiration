package cn.zxf.sql;

import lombok.AllArgsConstructor;

/**
 * 条件类型
 * <br/>
 * Created by ZXFeng on  2022/6/28.
 */
@AllArgsConstructor
public enum CriteriaType {

    LT("< ?"),
    LTE("<= ?"),
    GT("> ?"),
    GTE(">= ?"),
    EQ("= ?"),
    NE("!= ?"),
    IN("IN"),
    LIKE("LIKE CONCAT('%', ?, '%')"),   // 左右模糊查询
    RLIKE("LIKE CONCAT(?, '%')"),       // 右边模糊查询
    BETWEEN("BETWEEN ? AND ?", 2),
    IS_NULL("IS NULL", 0),
    IS_NOT_NULL("IS NOT NULL", 0),
    ;

    public final String operator;
    public final int params;

    CriteriaType(String operator) {
        this(operator, 1);
    }

}