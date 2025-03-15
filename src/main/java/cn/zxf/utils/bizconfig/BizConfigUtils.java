package cn.zxf.utils.bizconfig;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务配置文件工具类
 * <p/>
 * ZXF 创建于 2025/3/15
 */
@Slf4j
public abstract class BizConfigUtils {

    private static final String
            BIZ_CFG_FOLDER = "/biz-config/",
            SIGN = "obj =",
            COMMENT = "//";


    /*** 读取 JSON */
    public static String readJson(String fileName) {
        List<String> list = readContent(fileName);
        return list.stream()
                .filter(StrUtil::isNotBlank)
                .map(line -> {
                    if (line.contains(SIGN))
                        return null;
                    if (!line.contains(COMMENT))
                        return line;
                    int index = line.indexOf(COMMENT);
                    return line.substring(0, index); // 去掉注释
                })
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.joining("\n"));
    }

    /*** 读取文件内容 */
    private static List<String> readContent(String fileName) {
        try {
            InputStream in = BizConfigUtils.class.getResourceAsStream(BIZ_CFG_FOLDER + fileName);
            List<String> lines = new ArrayList<>();
            IoUtil.readLines(in, StandardCharsets.UTF_8, lines);
            return lines;
        } catch (Exception e) {
            throw new RuntimeException("读取配置文件（" + fileName + "）出错", e);
        }
    }


    // -----------------------------------

    /*** 读取文件内容 */
    @Deprecated // Spring 打包后，URI 有点怪，使用 Files 工具类会报错
    protected static List<String> readContent_ErrRef(String fileName) {
        try {
            URL url = BizConfigUtils.class.getResource(BIZ_CFG_FOLDER + fileName);
            log.info("file-name: [{}] => url: [{}]", fileName, url);
            URI uri = url.toURI();
            log.info("file-name: [{}] => uri: [{}]", fileName, uri);
            Path path = Paths.get(uri);
            log.info("file-name: [{}] => path: [{}]", fileName, path);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("读取配置文件（" + fileName + "）出错", e);
        }
    }

}
