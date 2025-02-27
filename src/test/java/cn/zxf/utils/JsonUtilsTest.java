package cn.zxf.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class JsonUtilsTest {

    @Test
    public void toJson() {
        Date date = TimeUtils.toDateMin(LocalDate.parse("2024-06-28"));
        Timestamp dateTime = TimeUtils.toTimestamp(LocalDateTime.parse("2024-06-28T23:30:30"));
        FmtE8DTO fmtE8DTO = new FmtE8DTO().setDate(date).setDateTime(dateTime); // 能对的上
        FmtE0DTO fmtE0DTO = new FmtE0DTO().setDate(date).setDateTime(dateTime); // 偏移：少 8 小时
        NoFmtDTO noFmtDTO = new NoFmtDTO().setDate(date).setDateTime(dateTime); // 时间戳数字
        log.info("fmt-e8-dto: [{}]", JsonUtils.deserializer(fmtE8DTO));
        log.info("fmt-e0-dto: [{}]", JsonUtils.deserializer(fmtE0DTO));
        log.info("no-fmt-dto: [{}]", JsonUtils.deserializer(noFmtDTO));
    }

    @Test
    public void toObjByStr() {
        String json = "{\"date\":\"2024-06-28\", \"checkDate\": \"2024-06-28 23\", \"dateTime\":\"2024-06-28 23:30:30\"}";
        FmtE8DTO fmtE8DTO = JsonUtils.serializable(json, FmtE8DTO.class); // 能对的上
        FmtE0DTO fmtE0DTO = JsonUtils.serializable(json, FmtE0DTO.class); // 偏移：多 8 小时
        NoFmtDTO noFmtDTO = JsonUtils.serializable(json, NoFmtDTO.class); // 出错：null (解析失败)
        log.info("str-fmt-e8-dto: [{}]", fmtE8DTO);
        log.info("str-fmt-e0-dto: [{}]", fmtE0DTO);
        log.info("str-no-fmt-dto: [{}]", noFmtDTO);
    }

    @Test
    public void testDate() {
        String json = "{\"checkDate\": \"2024-06-28 23\"}";
        FmtE8SimpleDTO dto = JsonUtils.serializable(json, FmtE8SimpleDTO.class); // 能对的上
        log.info("fmt-e8-simple-dto: [{}]", dto);
        log.info("date: [{}]", String.format("%tF %<tT", dto.checkDate));
    }

    @Test
    public void toObjByTs() {
        String json = "{\"date\":1719504000000, \"dateTime\":1719588630000}";
        FmtE8DTO fmtE8DTO = JsonUtils.serializable(json, FmtE8DTO.class); // 能对的上
        FmtE0DTO fmtE0DTO = JsonUtils.serializable(json, FmtE0DTO.class); // 能对的上
        NoFmtDTO noFmtDTO = JsonUtils.serializable(json, NoFmtDTO.class); // 能对的上
        log.info("tm-fmt-e8-dto: [{}]", fmtE8DTO);
        log.info("tm-fmt-e0-dto: [{}]", fmtE0DTO);
        log.info("tm-no-fmt-dto: [{}]", noFmtDTO);
    }

    @Test
    public void subMapToJson() {
        MapDTO map = new MapDTO().setType("type-111");
        map.put("key-1", "test-1111");
        map.put("key-2", "test-2222");
        log.info("sub-map: [{}]", map);

        String json = JsonUtils.deserializer(map);
        log.info("sub-map-json: [{}]", json);
    }

    // ---------------------


    @Data
    @Accessors(chain = true)
    static class FmtE8DTO {
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date date;
        @JsonFormat(pattern = "yyyy-MM-dd HH", timezone = "GMT+8")
        private Date checkDate;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        protected Timestamp dateTime;
    }

    @Data
    @Accessors(chain = true)
    static class FmtE8SimpleDTO {
        @JsonFormat(pattern = "yyyy-MM-dd HH", timezone = "GMT+8")
        private Date checkDate;
    }

    @Data
    @Accessors(chain = true)
    static class FmtE0DTO {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date date;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        protected Timestamp dateTime;
    }

    @Data
    @Accessors(chain = true)
    static class NoFmtDTO {
        private Date date;
        protected Timestamp dateTime;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    // @ToString(callSuper = true)
    static class MapDTO extends LinkedHashMap<String, Object> {
        private String type;
    }

    @Data
    @Accessors(chain = true)
    static class TestDto {
        private String name;
        private String type;
        private Integer age;
    }

    @Test
    public void ignoreNull() throws JsonProcessingException {
        TestDto dto = new TestDto().setName("zxf");
        String json = JsonUtils.deserializer(dto);
        log.info("json: [{}]", json);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        json = mapper.writeValueAsString(dto);
        log.info("json: [{}]", json);
    }

}