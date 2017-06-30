package com.neusoft.core.javac.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.neusoft.core.util.ReflectWrapper;
import com.sun.source.tree.Tree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;

@Deprecated
@SupportedAnnotationTypes({ "com.alibaba.dubbo.config.annotation.Reference",
                            "com.alibaba.dubbo.config.annotation.Service" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(value = DubboTestVersionProcessor.DUBBO_TEST_VERSION)
public class DubboTestVersionProcessor extends AbstractProcessor {

    private Elements           elements;                    // 元素相关的辅助类

    @SuppressWarnings("unused")
    @Deprecated
    private Types              types;

    private Trees              trees;

    private TreeMaker          treeMarker;

    private String             testVersion        = "";

    public static final String DUBBO_TEST_VERSION = "dubbo";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
        trees = Trees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        treeMarker = TreeMaker.instance(context);
        testVersion = processingEnv.getOptions().get(DubboTestVersionProcessor.DUBBO_TEST_VERSION);
    }

    public void handler(Element element) {
        // 查找Reference、Service注解
        element.getAnnotationMirrors().stream().filter(mirror -> mirror.getAnnotationType().toString().equals(Reference.class.getCanonicalName())
                                                                 || mirror.getAnnotationType().toString().equals(Service.class.getCanonicalName())).forEach(mirror -> handler(element,
                                                                                                                                                                              mirror));
    }

    @SuppressWarnings("rawtypes")
    public void handler(Element element, AnnotationMirror mirror) {
        // 如果有注解的化，就不再加注解
        if (mirror.getElementValues().keySet().stream().filter(key -> key.getSimpleName().contentEquals("version")).count() > 0) {
            return;
        }
        Symbol version = (Symbol) elements.getElementValuesWithDefaults(mirror).keySet().stream().filter(key -> key.getSimpleName().contentEquals("version")).findFirst().get();
        JCIdent ident = treeMarker.Ident(version);
        JCLiteral literal = treeMarker.Literal(testVersion);
        JCAssign assign = treeMarker.Assign(ident, literal);
        Tree tree = trees.getTree(element);
        JCModifiers modifier = null;
        if (tree instanceof JCVariableDecl) {
            JCVariableDecl m = (JCVariableDecl) tree;
            modifier = m.getModifiers();
        } else if (tree instanceof JCClassDecl) {
            JCClassDecl c = (JCClassDecl) tree;
            modifier = c.getModifiers();
        }
        modifier.getAnnotations().stream().filter(anno -> anno.annotationType.type.toString().equals(Reference.class.getCanonicalName())
                                                          || anno.annotationType.type.toString().equals(Service.class.getCanonicalName())).forEach(anno -> {
                                                              List args = anno.getArguments();
                                                              if (args.isEmpty()) {
                                                                  ReflectWrapper.setValue(anno, "args",
                                                                                          List.of(assign));
                                                              } else {
                                                                  anno.getArguments().setTail(List.of(assign));
                                                              }
                                                          });
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (StringUtils.isBlank(testVersion) || StringUtils.equals(testVersion, "${env.DUBBO_TEST_VERSION}")) {
            return true;
        }
        annotations.stream().filter(typeElement -> typeElement.toString().equals(Reference.class.getCanonicalName())
                                                   || typeElement.toString().equals(Service.class.getCanonicalName())).forEach(typeElement -> {
                                                       roundEnv.getElementsAnnotatedWith(typeElement).forEach(element -> {
                                                           handler(element);
                                                       });
                                                   });
        return true;
    }

}
