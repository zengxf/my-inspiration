package cn.zxf.spring.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 适配特殊业务字段（数字前带下划线；_amt 后缀加 _1d）
 * <br/>
 * Created by ZXFeng on  2022/7/4.
 */
@Slf4j
public class SpecialBizRowMapper<T> extends BeanPropertyRowMapper<T> {

    private static final Set<String> NUM_SET = new HashSet<>(Arrays.asList("0123456789".split("")));

    public SpecialBizRowMapper(Class<T> mappedClass) {
        super(mappedClass);
    }

    @Override
    protected void initialize(Class<T> mappedClass) {
        super.initialize(mappedClass); // 先调用父类的，添加普通字段
        // 特殊业务字段处理
        Map<String, PropertyDescriptor> mappedFields;
        try {
            Field mappedFieldsField = BeanPropertyRowMapper.class.getDeclaredField("mappedFields");
            mappedFieldsField.setAccessible(true);
            mappedFields = (Map<String, PropertyDescriptor>) mappedFieldsField.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("初始化设置出错！", e);
            return; // 容错处理，不影响正常流程
        }
        int num = 0;
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                String fieldName = pd.getName();
                String underscoredName = super.underscoreName(fieldName);
                String specialNumName = this.specialNumName(fieldName);
                if (!underscoredName.equals(specialNumName)) {
                    mappedFields.put(specialNumName, pd);
                    num++;
                }
                String specialSuffixName = this.specialSuffixName(specialNumName);
                if (!specialNumName.equals(specialSuffixName)) {
                    mappedFields.put(specialSuffixName, pd);
                    num++;
                }
            }
        }
        log.info("额外追加特殊业务字段数：[{}]", num);
    }

    /*** 特殊业务字段转换：数字前加下划线 */
    protected String specialNumName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(lowerCaseName(name.substring(0, 1)));
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = lowerCaseName(s);
            if (!s.equals(slc) || NUM_SET.contains(s)) {
                result.append("_").append(slc);
            } else {
                result.append(s);
            }
        }
        return result.toString();
    }

    /*** 特殊业务字段转换：_amt、_cnt 后缀加 _1d */
    @Deprecated(forRemoval = true, since = "仅参考")
    protected String specialSuffixName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }
        if (name.endsWith("_amt") || name.endsWith("_cnt"))
            return name + "_1d";
        return name;
    }


    public static <T> SpecialBizRowMapper<T> newInstance(Class<T> mappedClass) {
        return new SpecialBizRowMapper<T>(mappedClass);
    }

}
