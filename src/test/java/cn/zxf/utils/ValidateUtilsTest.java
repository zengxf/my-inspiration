package cn.zxf.utils;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ValidateUtilsTest {

    @Test
    public void validateOnCreate() {
        EditReq req = new EditReq();

        try {
            ValidateUtils.validate(req, AddSign.class, Default.class);
        } catch (Exception e) {
            log.error("校验失败：[{}]", e.getMessage());
        }
    }

    @Test
    public void validateOnUpdate() {
        EditReq req = new EditReq();
        try {
            ValidateUtils.validate(req, UpSign.class, Default.class);
        } catch (Exception e) {
            log.error("校验失败：[{}]", e.getMessage());
        }
    }

    @Test
    public void validateDef() {
        EditReq req = new EditReq();
        try {
            ValidateUtils.validate(req);    // 默认只校验组为 Default.class 的字段
        } catch (Exception e) {
            log.error("校验失败：[{}]", e.getMessage());
        }
    }


    // -----------------------------

    @Data
    public static class EditReq {
        @NotNull(message = "ID 不能为空！", groups = UpSign.class)
        private Long id;

        // --------------------

        @NotEmpty(message = "姓名不能为空！", groups = {AddSign.class, UpSign.class})
        private String name;

        // @NotEmpty(message = "年龄不能为空！", groups = Default.class)
        @NotEmpty(message = "年龄不能为空！")      // 默认组为 Default.class
        private String age;
        @NotEmpty(message = "备注不能为空！")
        private String remark;

        // --------------------

        @NotEmpty(message = "创建者不能为空", groups = AddSign.class)
        private String creator;

        @NotEmpty(message = "修改者不能为空", groups = UpSign.class)
        private String modifier;
    }

    // 注解或接口都可以
    public interface AddSign {
    }

    public interface UpSign {
    }

}