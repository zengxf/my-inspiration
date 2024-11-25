package cn.zxf.mytemp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 生成版权信息
 */
@Slf4j
public class GenerateCopyright {

    String pathKey = "module-name"; // 模块名
    String authorKey = "ZXFeng";

    @Test
    public void generateCode() throws Exception {

        URL classPath = GenerateCopyright.class.getResource("/"); // IDEA: ../build/classes/java/test/
        // log.info("classPath: [{}]", classPath);

        Path rootPath = Paths.get(classPath.toURI())
                .resolve("../../../../")
                // .resolve(pathKey) // 可设置模块名
                .resolve("./src/main/java/");
        // log.info("rootPath: [{}]", rootPath);

        Files.list(rootPath)
                .forEach(path -> {
                    try {
                        this.generate(path);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void generate(Path path) throws Exception {
        if (Files.isDirectory(path)) {
            Files.list(path)
                    .forEach(subPath -> {
                        try {
                            this.generate(subPath);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        } else {
            if (path.getFileName().toString().endsWith(".java")) {
                // log.info("java: [{}]", path);

                List<String> lines = Files.readAllLines(path);
                String content = String.join("\n", lines);

                if (!content.contains(authorKey)) {
                    return;
                }
                if (content.contains(copyrightKey)) {
                    return;
                }

                String newContent = copyrightAll + content;
                // log.info("newContent: \n\n{}\n\n", newContent);

                // 写入版权
                // Files.write(path, newContent.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);

                log.info("java: [{}] ok!", path);
            }
        }
    }

    private static final String copyrightKey = "Copyright 2002-2022 the original";

    private static final String copyrightAll = "" +
            "/*\n" +
            " * Copyright 2002-2022 the original author or authors.\n" +
            " *\n" +
            " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
            " * you may not use this file except in compliance with the License.\n" +
            " * You may obtain a copy of the License at\n" +
            " *\n" +
            " *      https://www.apache.org/licenses/LICENSE-2.0\n" +
            " *\n" +
            " * Unless required by applicable law or agreed to in writing, software\n" +
            " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
            " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
            " * See the License for the specific language governing permissions and\n" +
            " * limitations under the License.\n" +
            " */\n\n";

}