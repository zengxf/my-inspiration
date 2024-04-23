package cn.zxf.common;

/**
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
