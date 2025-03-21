package cn.zxf.mytemp;

import cn.zxf.utils.JsonUtils;
import cn.zxf.utils.StackTraceUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

/**
 * Mock 静态方法测试
 * <p/>
 * ZXF 创建于 2025/3/21
 */
@Slf4j
public class MockStaticTest {

    @Test
    public void testInt() {
        int testV = 2;

        MockedStatic<StackTraceUtils> mock = Mockito.mockStatic(StackTraceUtils.class);
        // mock.when(() -> StackTraceUtils.stackTrace(anyInt()))   // 没设置会返回 null，不会出错
        //         .thenReturn("mock-test-xx");
        mock.when(() -> StackTraceUtils.stackTrace(anyInt()))   // 参数是 int 只能用 anyInt() 不能用 any()
                .thenReturn("mock-test-xx");
        mock.when(() -> StackTraceUtils.stackTrace(testV))      // 精确匹配
                .thenReturn("mock-test-22");

        String v = StackTraceUtils.stackTrace(33);
        log.info("v: [{}]", v);

        v = StackTraceUtils.stackTrace(testV);
        log.info("v: [{}]", v);
    }

    @Test
    public void testObj() {
        Object testV = new Object();

        MockedStatic<JsonUtils> mock = Mockito.mockStatic(JsonUtils.class);
        // mock.when(() -> JsonUtils.deserializer(any()))   // 没设置会返回 null，不会出错
        //         .thenReturn("mock-test-xx");
        mock.when(() -> JsonUtils.deserializer(any()))      // 没设置会返回 null，不会出错
                .thenReturn("mock-test-{any}");
        mock.when(() -> JsonUtils.deserializer(testV))      // 精确匹配
                .thenReturn("mock-test-22");

        String v = JsonUtils.deserializer(33);
        log.info("v1: [{}]", v);

        v = JsonUtils.deserializer(testV);
        log.info("v2: [{}]", v);
    }

}
