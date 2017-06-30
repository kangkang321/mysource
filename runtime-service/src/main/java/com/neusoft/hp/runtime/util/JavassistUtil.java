package com.neusoft.hp.runtime.util;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

import com.neusoft.core.exception.BizException;
import com.neusoft.core.util.ReflectionUtils;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.NoSuchClassError;

/**
 * 类JavassistUtil.java的实现描述：注解操作类
 * 
 * @author Administrator 2017年6月14日 下午5:39:54
 */
public class JavassistUtil {

    private static final ClassLoader loader = JavassistUtil.class.getClassLoader();

    private static final ClassPool   pool   = new ClassPool(true);

    static {
        pool.appendClassPath(new LoaderClassPath(loader));
    }

    public static ConstPool getConstPool(javassist.bytecode.annotation.Annotation annotation) {
        return (ConstPool) ReflectionUtils.getFieldValue(annotation, "pool");
    }

    public static Annotation createAnnotation(Class<? extends Annotation> clazz,
                                              Consumer<javassist.bytecode.annotation.Annotation> consumer) {
        javassist.bytecode.annotation.Annotation annotation = new javassist.bytecode.annotation.Annotation(clazz.getName(),
                                                                                                           new ConstPool(clazz.getName()));
        consumer.accept(annotation);
        try {
            return (Annotation) annotation.toAnnotationType(loader, pool);
        } catch (ClassNotFoundException | NoSuchClassError e) {
            throw new BizException(e);
        }
    }

}
