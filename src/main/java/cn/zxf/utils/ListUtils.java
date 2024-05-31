package cn.zxf.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合 List 帮助类
 * <p/>
 * Created by ZXFeng on 2024/5/31
 */
public class ListUtils {

    /*** String 毒丸 */
    public static final String STR_POISON = "#_#_#";
    /*** Long 毒丸 */
    public static final Long LONG_POISON = -566889L;


    // --------------------------------

    /*** 分隔成 List */
    public static List<String> split(String value, String delimiter) {
        if (StrUtil.isEmpty(value))
            return CollectionUtil.newArrayList();
        return Stream.of(value.split("\\s*" + delimiter + "\\s*"))
                .filter(StrUtil::isNotEmpty)
                .collect(Collectors.toList());
    }

    /*** 查找第一个 @Nullable */
    public static <T> T getFirst(List<T> list) {
        return getFirst(list, null);
    }

    /*** 查找第一个 */
    public static <T> T getFirst(List<T> list, T def) {
        if (CollectionUtil.isEmpty(list))
            return def;
        return list.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(def);
    }

    /*** 数组转换成 List */
    public static <T> List<T> toList(T... arr) {
        if (arr == null || arr.length == 0) {
            return new ArrayList<>();
        }
        return Stream.of(arr)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /*** 转数组 */
    public static <T, V> V[] toArray(Collection<T> list, Class<V> clazz, Function<T, V> fun) {
        List<V> temp = CollStreamUtils.map(list, fun);
        V[] arr = (V[]) Array.newInstance(clazz, temp.size());
        return temp.toArray(arr);
    }

    /*** 转 Long 数组 */
    public static <T> Long[] toLongArray(Collection<T> list, Function<T, Long> fun) {
        if (CollectionUtil.isEmpty(list))
            return new Long[]{};
        return list.stream()
                .filter(Objects::nonNull)
                .map(fun)
                .distinct()
                .filter(Objects::nonNull)
                .toArray(Long[]::new);
    }

    /*** 填充 Long   毒丸 (用于查询时，保证数据库 IN 查询时找不到数据)  */
    public static void fillLPoison(List<Long> list) {
        list.add(LONG_POISON);
    }

    /*** 填充 String 毒丸 (用于查询时，保证数据库 IN 查询时找不到数据)  */
    public static void fillSPoison(List<String> list) {
        list.add(STR_POISON);
    }

}
