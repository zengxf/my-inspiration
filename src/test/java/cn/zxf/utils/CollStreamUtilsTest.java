package cn.zxf.utils;

import cn.zxf.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
@Slf4j
public class CollStreamUtilsTest {

    @Test
    public void groupMap() {
        List<UserVo> list = UserVo.ofList3();
        list.addAll(UserVo.ofList3());
        Map<String, List<UserVo>> map = CollStreamUtils.groupMap(list, UserVo::getId);
        map.forEach((k, v) -> log.info("k: [{}] -> v: [{}]", k, v));
    }

    @Test
    public void toMap() {
        List<UserVo> list = UserVo.ofList3();
        Map<String, UserVo> map = CollStreamUtils.toMap(list, UserVo::getId);
        map.forEach((k, v) -> log.info("k: [{}] -> v: [{}]", k, v));
    }

    @Test
    public void toDistinctMap() {
        List<UserVo> list = UserVo.ofList3();
        list.addAll(UserVo.ofList3());
        list.forEach(v -> log.info("v: [{}]", v));
        log.info("---------------------------");

        Map<String, UserVo> map = CollStreamUtils.toDistinctMap(list, UserVo::getId);
        map.forEach((k, v) -> log.info("k: [{}] -> v: [{}]", k, v));
    }

    @Test
    public void toSet() {
        List<UserVo> list = UserVo.ofList3();
        list.addAll(UserVo.ofList3());
        Set<String> ids = CollStreamUtils.toSet(list, UserVo::getId);
        log.info("ids: [{}]", ids);
    }

    @Test
    public void map() {
        List<UserVo> list = UserVo.ofList3();
        list.addAll(UserVo.ofList3());
        List<String> ids = CollStreamUtils.map(list, UserVo::getId);
        log.info("ids: [{}]", ids);
    }

    @Test
    public void distinct() {
        List<UserVo> list = UserVo.ofList3();
        list.addAll(UserVo.ofList3());
        List<String> ids = CollStreamUtils.distinct(list, UserVo::getId);
        log.info("ids: [{}]", ids);
    }

    @Test
    public void filter() {
        List<UserVo> list = UserVo.ofList3();
        List<UserVo> targetList = CollStreamUtils.filter(list, v -> v.getAge() > 20);
        targetList.forEach(v -> log.info("v: [{}]", v));
    }

    @Test
    public void exists() {
        List<UserVo> list = UserVo.ofList3();
        boolean exists = CollStreamUtils.exists(list, v -> v.getAge() > 20);
        log.info("exists: [{}]", exists);
    }

    @Test
    public void findOne() {
        List<UserVo> list = UserVo.ofList3();
        UserVo one = CollStreamUtils.findOne(list, v -> v.getAge() > 20);
        log.info("one: [{}]", one);
    }

    @Test
    public void ofEnum() {
        String target = "10";
        Type one = CollStreamUtils.ofEnum(v -> v.key.equals(target), "未找到", Type.values());
        log.info("one: [{}]", one);
    }

    @AllArgsConstructor
    enum Type {
        MAN("10", "男"),
        WOMAN("20", "女"),
        ;

        public final String key;
        public final String desc;
    }

}