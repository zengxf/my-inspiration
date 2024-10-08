package cn.zxf.common;

/**
 * 业务异常
 * <p/>
 * Created by ZXFeng on 2024/4/23
 */
public class BizException extends RuntimeException {

    public final int code;

    public BizException(int code, String message) {
        super(message, null);
        this.code = code;
    }

}
