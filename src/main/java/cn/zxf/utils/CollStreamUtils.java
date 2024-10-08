package cn.zxf.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.zxf.common.ErrCodeConstant;
import cn.zxf.common.BizException;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合流的帮助类
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
public class CollStreamUtils implements ErrCodeConstant {

    /*** 列表分组 */
    public static <K, T> Map<K, List<T>> groupMap(Collection<T> list, Function<T, K> keyFun) {
        if (CollectionUtil.isEmpty(list))
            return new HashMap<>();
        return list.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.groupingBy(keyFun, LinkedHashMap::new, Collectors.toList())
                );
    }

    /*** 列表转换成 Map */
    public static <K, T> Map<K, T> toMap(Collection<T> list, Function<T, K> keyFun) {
        return toMap(list, keyFun, Function.identity());
    }

    /*** 列表转换成 Map */
    public static <T, K, V> Map<K, V> toMap(Collection<T> list, Function<T, K> keyFun, Function<T, V> valueFun) {
        if (CollectionUtil.isEmpty(list))
            return new HashMap<>();
        return list.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.toMap(keyFun, valueFun)
                );
    }

    /*** 列表转换成 Map (重复时只取第一个) */
    public static <K, T> Map<K, T> toDistinctMap(Collection<T> list, Function<T, K> keyFun) {
        return toDistinctMap(list, keyFun, Function.identity());
    }

    /*** 列表转换成 Map (重复时只取第一个) */
    public static <T, K, V> Map<K, V> toDistinctMap(
            Collection<T> list, Function<T, K> keyFun, Function<T, V> valueFun
    ) {
        if (CollectionUtil.isEmpty(list))
            return new HashMap<>();
        return list.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.toMap(keyFun, valueFun, (v1, v2) -> v1)
                );
    }

    /*** 列表类型转换成 Set */
    public static <T, V> Set<V> toSet(Collection<T> list, Function<T, V> fun) {
        if (CollectionUtil.isEmpty(list))
            return new HashSet<>();
        return list.stream()
                .filter(Objects::nonNull)
                .map(fun)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /*** 列表类型转换 */
    public static <T, V> List<V> map(Collection<T> list, Function<T, V> fun) {
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();
        return list.stream()
                .filter(Objects::nonNull)
                .map(fun)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /*** 去重 */
    public static <T, V> List<V> distinct(Collection<T> list, Function<T, V> fun) {
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();
        return list.stream()
                .filter(Objects::nonNull)
                .map(fun)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /*** 列表过滤 */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();
        return list.stream()
                .filter(Objects::nonNull)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /*** 列表判断是否存在 */
    public static <T> boolean exists(List<T> list, Predicate<T> predicate) {
        if (CollectionUtil.isEmpty(list))
            return false;
        return list.stream()
                .filter(Objects::nonNull)
                .anyMatch(predicate);
    }

    /*** 找到独有的一个 (@Nullable) */
    public static <T> T findOne(List<T> list, Predicate<T> predicate) {
        if (CollectionUtil.isEmpty(list))
            return null;
        return list.stream()
                .filter(Objects::nonNull)
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    /*** 查找枚举 */
    public static <T extends Enum> T ofEnum(Predicate<T> predicate, String notExistErr, T... values) {
        return Stream.of(values)
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new BizException(ENUM_ERR_CODE, notExistErr));
    }

}
