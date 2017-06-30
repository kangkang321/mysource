/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.neusoft.hp.mybatis.plugin.xmlgenerate.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreeMarkerImpl {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private Configuration       config;

    // private String classPath;

    public FreeMarkerImpl(){
        // this.classPath = classPath;
        initConfiguration();
    }

    public void initConfiguration() {
        try {
            config = new Configuration();
            config.setDirectoryForTemplateLoading(new File("templates"));
            config.setObjectWrapper(new DefaultObjectWrapper());

            config.setSetting("classic_compatible", "true");
            config.setSetting("whitespace_stripping", "true");
            config.setSetting("template_update_delay", "1");
            config.setSetting("locale", "zh_CN");
            config.setSetting("default_encoding", DEFAULT_ENCODING);
            config.setSetting("url_escaping_charset", DEFAULT_ENCODING);
            config.setSetting("datetime_format", "yyyy-MM-dd hh:mm:ss");
            config.setSetting("date_format", "yyyy-MM-dd");
            config.setSetting("time_format", "HH:mm:ss");
            config.setSetting("number_format", "0.######;");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void processToFile(Map<String, Object> model) throws TemplateEngineException {
        try {
            System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
            System.out.println(this.getClass().getClassLoader().getResource(""));
            Template template = config.getTemplate("generatorConfig.ftl", "utf-8");
            String targetPath = this.getClass().getResource("").toString();
            // String targetFileName = processToString(model,
            // templateElement.getTargetFileName());
            File file = new File("src/main/generatorConfig.xml");

            File directory = new File(targetPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            template.process(model, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new TemplateEngineException(e.getMessage(), e);
        }
    }

}
