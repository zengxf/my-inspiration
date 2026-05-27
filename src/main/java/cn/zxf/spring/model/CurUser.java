package cn.zxf.spring.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前用户
 * <p/>
 * Created by ZXFeng on 2026/5/26
 */
@Data
@Accessors(chain = true)
public class CurUser {

    private Long userId;
    private String userNo;
    private String username;

    private String email;

    private Long empId;
    private String empName;

}
