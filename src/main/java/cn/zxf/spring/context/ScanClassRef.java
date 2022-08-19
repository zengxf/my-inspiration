package cn.zxf.spring.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.asm.ClassReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * 扫描类的参考
 * <br/>
 * Created by ZXFeng on 2022/8/19.
 */
@Slf4j
public class ScanClassRef {

    public static void scanClass(ApplicationContext applicationContext) {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(ComponentScan.class); // ref
        HashSet<String> basePackages = new HashSet<>();
        String annotationClassName = ComponentScan.class.getName();
        beanMap.forEach((name, bean) -> {
            Class<?> originalBeanClass = Proxy.isProxyClass(bean.getClass()) ? // ref
                    bean.getClass() : bean.getClass().getSuperclass();
            String declaringClass = originalBeanClass.getName();
            AnnotationMetadata metadata = AnnotationMetadata.introspect(originalBeanClass); // ref
            Set<AnnotationAttributes> annotationAttributes = new LinkedHashSet<>();
            Map<String, Object> annMap = metadata.getAnnotationAttributes(annotationClassName, false);
            annotationAttributes.add(AnnotationAttributes.fromMap(annMap));
            Map<String, Object> container = annMap;
            if (container != null && container.containsKey("value")) {
                for (Map<String, Object> containedAttributes : (Map<String, Object>[]) container.get("value")) {
                    annotationAttributes.add(AnnotationAttributes.fromMap(containedAttributes));
                }
            }
            for (AnnotationAttributes componentScan : annotationAttributes) {
                String[] basePackagesArray = componentScan.getStringArray("basePackages");
                for (String pkg : basePackagesArray) {
                    String[] tokenized = StringUtils.tokenizeToStringArray(
                            applicationContext.getEnvironment().resolvePlaceholders(pkg),
                            ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS
                    );
                    Collections.addAll(basePackages, tokenized);
                }
                for (Class<?> clazz : componentScan.getClassArray("basePackageClasses"))
                    basePackages.add(ClassUtils.getPackageName(clazz)); // ref
                if (basePackages.isEmpty())
                    basePackages.add(ClassUtils.getPackageName(declaringClass));
            }
        });
        // ----------
        basePackages.forEach(path -> {
            String basePackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(path) + "/**/*.class";
            try {
                Resource[] resources = applicationContext.getResources(basePackage);
                for (Resource resource : resources) {
                    String className = ClassUtils.convertResourcePathToClassName(
                            new ClassReader(resource.getInputStream()).getClassName()
                    );
                    try {
                        Class<?> clazz = ClassUtils.forName(className, ScanClassRef.class.getClassLoader());
                        log.info("目标类：[{}]", clazz);
                    } catch (Throwable e) {
                    }
                }
            } catch (IOException e) {
            }
        });
    }

}
