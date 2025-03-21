package cn.zxf.mytemp;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Enum 转 MySQL 语法
 * <p/>
 * ZXF 创建于 2025/3/15
 */
public class EnumToMySQLCase {

    public static void main(String[] args) {
        String columnName = "user_region";
        String cnName = "'用户所在大区'";
        RegionEnum[] values = RegionEnum.values();  /** 改 Enum */

        StringBuilder sb = new StringBuilder();
        sb.append("    ( CASE {column_name}").append("\n");
        Stream.of(values)
                .forEach(e -> {
                    String fmt = "         WHEN '{}' THEN '{}'";
                    String item = StrUtil.format(fmt, e.key, e.desc);
                    sb.append(item).append("\n");
                });
        sb.append("         ELSE CONCAT('无：', {column_name})").append("\n");
        sb.append("      END").append("\n");
        sb.append("    ) AS {cnName}");

        String fmt = sb.toString();
        Map<String, String> map = MapUtil.of(
                Pair.of("column_name", columnName),
                Pair.of("cnName", cnName),
                Pair.of("", "")
        );
        String sql = StrUtil.format(fmt, map);
        System.out.println("\n\n" + sql + "\n\n");
    }


    @AllArgsConstructor
    public enum RegionEnum {
        D_10("10", "华东大区"),
        D_20("20", "华南大区"),
        ;

        public final String key;     // 编码
        public final String desc;    // 编码描述
    }

}
