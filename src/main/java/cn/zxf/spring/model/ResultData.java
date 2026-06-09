package cn.zxf.spring.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 服务接口返回格式
 * <p/>
 * Created by ZXFeng on 2026/6/9
 */
@Data
@Accessors(chain = true)
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String MSG_KEY = "msg";

    public int code;
    public String msg;
    public String exceptions;
    public String traceId;

    public T data;

    public ResultData() {
    }

    public boolean isSuccess() {
        if (ResultDataBuilder.SUCCESS == this.getCode()) {
            return true;
        }
        return false;
    }

}
