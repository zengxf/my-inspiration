package cn.zxf.utils;

import cn.zxf.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ListUtilsTest {

    @Test
    public void split() {
        String s = "ab , dd , ef";
        List<String> list = ListUtils.split(s, ",");
        log.info("list: [{}]", list);

        Assert.assertEquals(3, list.size());
    }

    @Test
    public void getFirst() {
        List<UserVo> list = UserVo.ofList3();
        UserVo first = ListUtils.getFirst(list);
        log.info("first: [{}]", first);
    }

    @Test
    public void toList() {
        List<String> list = ListUtils.toList("b", null, "c", "d");
        log.info("list: [{}]", list);

        Assert.assertEquals(3, list.size());
    }

    @Test
    public void toArray() {
        List<UserVo> list = UserVo.ofList3();
        String[] idArr = ListUtils.toArray(list, String.class, UserVo::getId);
        log.info("idArr: [{}]", Arrays.toString(idArr));
    }

    @Test
    public void toLongArray() {
        List<UserVo> list = UserVo.ofList3();
        Long[] ageLongArr = ListUtils.toLongArray(list, v -> v.getAge().longValue());
        log.info("ageLongArr: [{}]", Arrays.toString(ageLongArr));
    }

    @Test
    public void fillLPoison() {
        List<Long> list = new ArrayList<>();    // 从 DB 取
        ListUtils.fillLPoison(list);            // 填充完，方便 MyBatis xml 或其他查询时对条件的拼接
        log.info("list: [{}]", list);

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void fillSPoison() {
        List<String> list = new ArrayList<>();  // 从 DB 取
        ListUtils.fillSPoison(list);
        log.info("list: [{}]", list);

        Assert.assertEquals(1, list.size());
    }

}