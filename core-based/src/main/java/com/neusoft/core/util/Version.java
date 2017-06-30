package com.neusoft.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类Version.java的实现描述：版本有關
 * 
 * @author Administrator 2017年6月14日 下午4:56:27
 */
public final class Version {

    private Version(){
    }

    private static final Logger  LOGGER          = LoggerFactory.getLogger(Version.class);

    private static final Pattern VERSION_PATTERN = Pattern.compile("([0-9][0-9\\.\\-]*)\\.jar");

    private static final String  VERSION         = getVersion(Version.class, "2.5.3");

    public static String getVersion() {
        return VERSION;
    }

    @SuppressWarnings("rawtypes")
    public static String getVersion(Class cls, String defaultVersion) {
        try {
            // 首先查找MANIFEST.MF规范中的版本号
            String version = cls.getPackage().getImplementationVersion();
            if (version == null || version.length() == 0) {
                version = cls.getPackage().getSpecificationVersion();
            }
            if (version == null || version.length() == 0) {
                // 如果MANIFEST.MF规范中没有版本号，基于jar包名获取版本号
                String file = cls.getProtectionDomain().getCodeSource().getLocation().getFile();
                if (file != null && file.length() > 0 && file.endsWith(".jar")) {
                    Matcher matcher = VERSION_PATTERN.matcher(file);
                    while (matcher.find() && matcher.groupCount() > 0) {
                        version = matcher.group(1);
                    }
                }
            }
            // 返回版本号，如果为空返回缺省版本号
            return version == null || version.length() == 0 ? defaultVersion : version;
        } catch (Throwable e) { // 防御性容错
            // 忽略异常，返回缺省版本号
            LOGGER.error(e.getMessage(), e);
            return defaultVersion;
        }
    }

}
