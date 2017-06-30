package com.neusoft.core.javac.processor;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.neusoft.core.dubbo.LazyAutowiredProxy;
import com.neusoft.core.service.annotation.LazyAutowired;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.ClassSymbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCImport;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

/**
 * 类LazyAutowiredProcessor.java的实现描述：代碼編譯階段把{@link LazyAutowired}的注入推遲到方法調用
 * 
 * @author Administrator 2017年6月14日 下午4:24:32
 */
@SupportedAnnotationTypes({ "com.neusoft.core.service.annotation.LazyAutowired" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class LazyAutowiredProcessor extends AbstractProcessor {

    private Elements   elements;  // 元素相关的辅助类

    @SuppressWarnings("unused")
    @Deprecated
    private Types      types;

    private Trees      trees;

    private TreeMaker  treeMarker;

    private JavacTrees javacTrees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
        trees = Trees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        treeMarker = TreeMaker.instance(context);
        javacTrees = JavacTrees.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.stream().filter(typeElement -> typeElement.toString().equals(LazyAutowired.class.getCanonicalName())).forEach(typeElement -> {
            roundEnv.getElementsAnnotatedWith(typeElement).forEach(element -> {
                handler(element);
            });
        });
        return true;
    }

    public void handler(Element element) {
        // 查找Reference、Service注解
        if (trees.getTree(element) instanceof JCVariableDecl) {
            JCVariableDecl var = (JCVariableDecl) trees.getTree(element);
            JCCompilationUnit compilationUnitTree = (JCCompilationUnit) javacTrees.getPath(((Symbol.VarSymbol) element).owner).getCompilationUnit();
            Name.Table table = var.getName().table;
            StringBuffer name = new StringBuffer();
            Map<? extends ExecutableElement, ? extends AnnotationValue> map = element.getAnnotationMirrors().stream().filter(mirror -> mirror.getAnnotationType().toString().equals(LazyAutowired.class.getCanonicalName())).findFirst().get().getElementValues();
            map.keySet().forEach(key -> {
                if (key.getSimpleName().contentEquals("value")) {
                    name.append(String.valueOf(map.get(key).getValue()));
                }
            });
            JCFieldAccess clazz = treeMarker.Select((JCIdent) var.getType(), table.fromString("class"));
            JCImport im = treeMarker.Import(((JCFieldAccess) treeMarker.ClassLiteral((Symbol.ClassSymbol) elements.getTypeElement(LazyAutowiredProxy.class.getCanonicalName()))).selected,
                                            false);
            compilationUnitTree.defs = compilationUnitTree.defs.prepend(im);
            var.init = treeMarker.Apply(List.nil(),
                                        treeMarker.Select(treeMarker.Ident((ClassSymbol) elements.getTypeElement(LazyAutowiredProxy.class.getCanonicalName())),
                                                          table.fromString("getProxy")),
                                        List.of(clazz, treeMarker.Literal(name.toString())));
        }
    }

}
