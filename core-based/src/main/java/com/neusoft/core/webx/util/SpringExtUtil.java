package com.neusoft.core.webx.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

public class SpringExtUtil {

    public static <T> T autowireAndInitialize(T existingBean, ApplicationContext context, int autowireMode,
                                              String beanName) {
        context.getAutowireCapableBeanFactory().autowireBeanProperties(existingBean, autowireMode, false);
        context.getAutowireCapableBeanFactory().initializeBean(existingBean, beanName);

        return existingBean;
    }

    /**
     * 根据baseName创建不重复的beanName。
     * <p>
     * 有别于 {@link BeanDefinitionReaderUtils.generateBeanName()} 方法，这里使用指定的baseName，而不是使用类名作为baseName。
     * </p>
     */
    public static String generateBeanName(String baseName, BeanDefinitionRegistry registry) {
        return generateBeanName(baseName, registry, null, false);
    }

    /**
     * 根据baseName创建不重复的beanName。
     * <p>
     * 有别于 {@link BeanDefinitionReaderUtils.generateBeanName()} 方法，这里使用指定的baseName，而不是使用类名作为baseName。
     * </p>
     * <p>
     * 如果是innerBean，则需要提供bean definition来生成id。
     * </p>
     */
    public static String generateBeanName(String baseName, BeanDefinitionRegistry registry, BeanDefinition definition,
                                          boolean isInnerBean) {
        baseName = assertNotNull(StringUtils.trimToNull(baseName), "baseName");
        String name;

        if (isInnerBean) {
            name = baseName + BeanDefinitionReaderUtils.GENERATED_BEAN_NAME_SEPARATOR
                   + ObjectUtils.getIdentityHexString(definition);
        } else {
            name = baseName;

            for (int i = 0; registry.containsBeanDefinition(name); i++) {
                name = baseName + BeanDefinitionReaderUtils.GENERATED_BEAN_NAME_SEPARATOR + i;
            }
        }

        return name;
    }

    public static <T> T assertNotNull(T object, String message, Object... args) {
        if (object == null) {

            throw new IllegalArgumentException(getMessage(message, args,
                                                          "[Assertion failed] - the argument is required; it must not be null"));
        }

        return object;
    }

    /** 确保表达式为真，否则抛出<code>IllegalArgumentException</code>。 */
    public static void assertTrue(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(getMessage(message, args,
                                                          "[Assertion failed] - the expression must be true"));
        }
    }

    /** 不支持的操作。 */
    public static <T> T unsupportedOperation(String message, Object... args) {
        throw new UnsupportedOperationException(getMessage(message, args,
                                                           "[Assertion failed] - unsupported operation or unimplemented function"));
    }

    /** 取得带参数的消息。 */
    private static String getMessage(String message, Object[] args, String defaultMessage) {
        if (message == null) {
            message = defaultMessage;
        }

        if (args == null || args.length == 0) {
            return message;
        }

        return String.format(message, args);
    }
}
