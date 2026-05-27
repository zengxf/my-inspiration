package cn.zxf.spring.model;

import cn.zxf.common.constants.WebHeaderConstant;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前用户操作的上下文信息
 * <p/>
 * 请求头参考：{@link WebHeaderConstant}
 * <p/>
 * Created by ZXFeng on 2026/5/26
 */
@Data
@Accessors(chain = true)
public class CurContext {

    private String clientIp;    // 客户端 IP

    private String traceId;     // 链路追踪 ID

    private CurUser user;       // 当前用户


    public CurUser getUserOrInit() {
        return this.user == null ? (this.user = new CurUser()) : this.user;
    }

}
