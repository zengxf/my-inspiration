package cn.zxf.spring.model;

/**
 * <p/>
 * Created by ZXFeng on 2026/6/9
 */

public class ResultDataBuilder<T> {

    public static final int FAIL = 0;
    public static final int SUCCESS = 1;

    private int code;
    private String msg;
    public String exceptions;
    private String traceId;
    private T data;

    public boolean isSuccess() {
        return this.code == SUCCESS;
    }

    public boolean isFail() {
        return this.code == FAIL;
    }

    public ResultDataBuilder<T> success() {
        this.code = SUCCESS;
        return this;
    }

    public ResultDataBuilder<T> fail() {
        this.code = FAIL;
        return this;
    }

    public ResultDataBuilder<T> code(int code) {
        this.code = code;
        return this;
    }

    public ResultDataBuilder<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResultDataBuilder<T> exceptions(String exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    public ResultDataBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResultDataBuilder<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public ResultData<T> build() {
        ResultData<T> resp = new ResultData<>();
        resp.code = this.code;
        resp.msg = this.msg;
        resp.exceptions = this.exceptions;
        resp.data = this.data;
        resp.traceId = this.traceId;
        return resp;
    }

    public static <T> ResultData<T> responseFail() {
        ResultData<T> resp = new ResultData<>();
        resp.code = FAIL;
        resp.msg = "失败";
        return resp;
    }

    public static <T> ResultData<T> responseFail(String msg) {
        return resultData(FAIL, msg, null);
    }

    public static <T> ResultData<T> responseFail(int code, String msg) {
        return resultData(code, msg, null);
    }

    public static <T> ResultData<T> responseFail(int code, String msg, String traceId) {
        ResultData<T> resp = new ResultData<>();
        resp.code = code;
        resp.msg = msg;
        resp.traceId = traceId;
        return resp;
    }

    public static <T> ResultData<T> responseFail(int code, String msg, String exceptions, String... args) {
        return resultData(code, msg, exceptions, null, args);
    }

    public static <T> ResultData<T> responseFail(String msg, String exceptions) {
        return resultData(FAIL, msg, exceptions, null, null);
    }

    public static <T> ResultData<T> responseFail(String msg, String exceptions, String... args) {
        return resultData(FAIL, msg, exceptions, null, args);
    }

    public static <T> ResultData<T> responseSuccess() {
        ResultData<T> resp = new ResultData<>();
        resp.code = SUCCESS;
        resp.msg = "成功";
        return resp;
    }

    public static <T> ResultData<T> responseSuccess(T data) {
        ResultData<T> resp = new ResultData<>();
        resp.code = SUCCESS;
        resp.msg = "成功";
        resp.data = data;
        return resp;
    }

    public static <T> ResultData<T> resultData(int code, String msg, String... args) {
        Object[] objects = null;
        if (args != null) {
            objects = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                objects[i] = msg;
            }
        }
        ResultData<T> resp = new ResultData<>();
        resp.code = code;
        resp.msg = msg;
        return resp;
    }

    public static <T> ResultData<T> resultData(int code, String msg, String exceptions, T data, String... args) {
        Object[] objects = null;
        if (args != null) {
            objects = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                objects[i] = args[i];
            }
        }
        ResultData<T> resp = new ResultData<>();
        resp.code = code;
        resp.msg = msg;
        resp.exceptions = exceptions;
        resp.data = data;
        return resp;
    }


}
