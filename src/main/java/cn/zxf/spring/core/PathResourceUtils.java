package cn.zxf.spring.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源查找帮助类
 * <br/>
 * Created by ZXFeng on 2022/9/13.
 */
@Slf4j
public class PathResourceUtils {

    /*** 根据通配符，读取所有的 JSON 文件 */
    public static List<String> getAllJson() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
            // 获取多个文件
            resources = resolver.getResources("classpath:biz1/*.json");
        } catch (IOException e) {
            throw new RuntimeException("查找资源出错！", e);
        }
        return Arrays.stream(resources)
                .map(item -> {
                    try {
                        return Files.readString(Paths.get(item.getURI()));
                    } catch (IOException e) {
                        throw new RuntimeException("读取 JSON 出错！", e);
                    }
                })
                .collect(Collectors.toList());
    }

}
