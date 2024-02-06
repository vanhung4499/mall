package com.hnv99.mall;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static void main(String[] args) throws Exception {
        // Warnings during the MBG execution process
        List<String> warnings = new ArrayList<String>();
        // Overwrite the original code when the generated code is duplicated
        boolean overwrite = true;
        // Read our MBG configuration file
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // Create MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        // Execute code generation
        myBatisGenerator.generate(null);
        // Output warning information
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
