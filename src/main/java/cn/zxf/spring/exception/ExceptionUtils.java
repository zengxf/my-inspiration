package cn.zxf.spring.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Created by ZXFeng on 2026/6/9
 */
public class ExceptionUtils {

    public static List<String> getStackTrace(Throwable throwable) {
        List<String> stackTrace = new ArrayList<>();
        getStackTrace(throwable, stackTrace);
        return stackTrace;
    }

    private static void getStackTrace(Throwable throwable, List<String> stackTrace) {
        StackTraceElement[] traceList = throwable.getStackTrace();

        StackTraceElement first = traceList[0];
        stackTrace.add(first.toString());

        for (int i = 1; i < traceList.length; i++) {
            StackTraceElement element = traceList[i];
            if (element.getClassName().startsWith("cn.zxf.")) {
                stackTrace.add(element.toString());
            }
        }

        getCause(throwable, stackTrace);
    }


    private static void getCause(Throwable throwable, List<String> stackTrace) {
        Throwable cause = throwable.getCause();
        if (cause == null) {
            return;
        }
        stackTrace.add("-- --");
        stackTrace.add("Caused by: " + cause);
        getStackTrace(cause, stackTrace);
    }

}
