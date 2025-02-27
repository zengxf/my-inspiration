package cn.zxf.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Json 工具类 (基于 Jackson)
 * <p/>
 * ZXF 创建于 2025/2/27
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);  // no error
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);     // ignore null
    }

    public static <T> T serializable(String json, Class<T> clazz) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static <T> T serializable(String json, TypeReference<T> reference) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, reference);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static String deserializer(Object json) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
