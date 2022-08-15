package cn.zxf.utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合流的帮助类
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
public class CollStreamUtils {

    public static <K, T> Map<K, T> listToMap(List<T> list, Function<T, K> kFun) {
        if (CollectionUtil.isEmpty(list))
            return new HashMap<>();
        return list.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.toMap(
                                kFun, Function.identity(),
                                mergeFunctionForMap(),
                                LinkedHashMap::new
                        )
                );
    }

    public static <K, V, T> Map<K, V> listToMap(List<T> list, Function<T, K> kFun, Function<T, V> vFun) {
        if (CollectionUtil.isEmpty(list))
            return new HashMap<>();
        return list.stream()
                .filter(item -> item != null && vFun.apply(item) != null)
                .collect(
                        Collectors.toMap(
                                kFun, vFun,
                                mergeFunctionForMap(),
                                LinkedHashMap::new
                        )
                );
    }

    public static <K, T> Map<K, List<T>> groupMap(List<T> list, Function<T, K> kFun) {
        if (CollectionUtil.isEmpty(list))
            return new HashMap<>();
        return list.stream()
                .filter(Objects::nonNull)
                .collect(
                        Collectors.groupingBy(
                                kFun,
                                LinkedHashMap::new,
                                Collectors.toList()
                        )
                );
    }

    public static <V> BinaryOperator<V> mergeFunctionForMap() {
        BinaryOperator<V> mergeFunction = (u, v) -> {
            throw new IllegalStateException(String.format("存在重复记录 [%s]", u));
        };
        return mergeFunction;
    }

    public static <T, I> List<I> listTransform(List<T> list, Function<T, I> fun) {
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();
        return list.stream()
                .filter(Objects::nonNull)
                .map(fun)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <T, S> Set<S> listToSet(List<T> list, Function<T, S> fun) {
        if (CollectionUtil.isEmpty(list))
            return new HashSet<>();
        return list.stream()
                .filter(Objects::nonNull)
                .map(fun)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
