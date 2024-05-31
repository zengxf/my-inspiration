package cn.zxf.utils;

import cn.zxf.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

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
    }

    @Test
    public void toLongArray() {
    }

    @Test
    public void fillLPoison() {
    }

    @Test
    public void fillSPoison() {
    }

}