package cn.zxf.common;

/**
 * 应用异常封装
 * <p/>
 * Created by ZXFeng on 2024/4/23
 */
public class ApplicationException extends RuntimeException {

    public final int code;

    public ApplicationException(int code, String message) {
        super(message, null);
        this.code = code;
    }

}
