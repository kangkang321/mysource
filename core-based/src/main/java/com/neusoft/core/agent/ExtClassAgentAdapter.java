package com.neusoft.core.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neusoft.core.util.ReflectionUtils;
import com.neusoft.core.webx.util.FileUtil;

@Deprecated
public class ExtClassAgentAdapter implements ClassFileTransformer {

    private static Logger LOGGER = LoggerFactory.getLogger(ExtClassAgentAdapter.class);

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        if (protectionDomain == null) {
        } else {
            try {
                CodeSource codeSource = protectionDomain.getCodeSource();
                if (codeSource == null || codeSource.getLocation() == null) {
                } else {
                    if (className.startsWith("com/neusoft")) {
                        String conf = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                        String file = FileUtil.getAbsolutePathBasedOn(conf, "../ext/class/" + className + ".class");
                        ReflectionUtils.setFieldValue(codeSource.getLocation(), "file", file);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("设置监听失败", e);
            }
        }
        return classfileBuffer;

    }

}
