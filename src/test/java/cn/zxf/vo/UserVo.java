package cn.zxf.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <br/>
 * Created by ZXFeng on 2022/8/15.
 */
@Data
@Accessors(chain = true)
public class UserVo {

    private String id;
    private Integer age;

    public static List<UserVo> ofList3() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new UserVo()
                        .setId("id-" + i)
                        .setAge(i * 11)
                )
                .collect(Collectors.toList());
    }

}
