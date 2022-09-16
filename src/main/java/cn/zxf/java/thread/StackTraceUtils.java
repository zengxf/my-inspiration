package cn.zxf.java.thread;

/**
 * 获取线程调用栈
 * <br/>
 * Created by ZXFeng on 2022/9/16.
 */
public class StackTraceUtils {

    /*** 获取调用栈 */
    public static String curStackTrace() {
        return stackTrace(3); // 还要加 1 才返回上级
    }

    /*** 获取调用栈 */
    public static String stackTrace(int level) {
        // 1: 是 当前方法 stackTrace 执行堆栈
        // 2: 是 当前方法 stackTrace 的 上一级的方法堆栈（也就是我们要找的）
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        StackTraceElement trace = stackTraces[level];
        String clazz = trace.getClassName();    // 调用的类名
        String method = trace.getMethodName();
        int line = trace.getLineNumber();
        return String.format("%s#%s(%d)", clazz, method, line);
    }

}
