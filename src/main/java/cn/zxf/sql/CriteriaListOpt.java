package cn.zxf.sql;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 条件集合 Optional
 * <br/>
 * Created by ZXFeng on  2022/6/28.
 */
@AllArgsConstructor
public class CriteriaListOpt {

    private final List<CriteriaOpt> list;

    /*** 实例化条件集合 */
    public static CriteriaListOpt of() {
        return new CriteriaListOpt(new ArrayList<>());
    }

    /*** 添加条件 */
    public CriteriaListOpt add(CriteriaOpt criteria) {
        this.list.add(criteria);
        return this;
    }

    /*** 转变成条件，And 关联 */
    public SqlCriteria toAnd() {
        return this.buildSql(" AND ");
    }

    /*** 转变成条件，Or 关联 */
    public SqlCriteria toOr() {
        return this.buildSql(" OR ");
    }

    private SqlCriteria buildSql(String op) {
        if (this.list.isEmpty())
            return SqlCriteria.EMPTY;
        List<Object> args = new ArrayList<>();
        String sql = list.stream()
                .filter(CriteriaOpt::isNotEmpty)
                .map(cri -> {
                    if (cri.args != null) // 添加参数
                        Stream.of(cri.args).forEach(args::add);
                    return cri.toSql();
                })
                .collect(Collectors.joining(op));
        return new SqlCriteria(sql, args);
    }

}
