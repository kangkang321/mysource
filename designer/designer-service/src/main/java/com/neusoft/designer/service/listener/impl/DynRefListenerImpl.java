package com.neusoft.designer.service.listener.impl;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.io.Bytes;
import com.alibaba.dubbo.remoting.zookeeper.zkclient.ZkclientZookeeperClient;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.annotation.GenColumn;
import com.neusoft.core.util.M2Util;
import com.neusoft.core.util.ReflectionUtils;
import com.neusoft.core.webx.util.FileUtil;
import com.neusoft.designer.service.domain.HpOdAttribute;
import com.neusoft.designer.service.domain.HpOdClass;
import com.neusoft.designer.service.listener.AttributeChangeListener;
import com.neusoft.designer.service.mapper.HpOdClassMapper;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

@Component
public class DynRefListenerImpl implements AttributeChangeListener {

    @Autowired
    private HpOdClassMapper HpOdClassMapper;

    @Override
    public boolean changed(HpOdAttribute before, HpOdAttribute after) {
        if (!isChange(before, after)) {
            return true;
        }
        ZkclientZookeeperClient client = new ZkclientZookeeperClient(new URL("zookeeper",
                                                                             System.getProperty("ext.class.host"),
                                                                             Integer.valueOf(System.getProperty("ext.class.port"))));
        // client.addStateListener(state -> {
        // if (state == StateListener.CONNECTED) {
        // client.createPersistent("/ext/class");
        // }
        // });
        HpOdClass hpOdClass = HpOdClassMapper.selectByPrimaryKey(before.getHpOdClassGid());
        // 修改class
        ClassPool pool = new ClassPool(true);
        pool.appendClassPath(new LoaderClassPath(M2Util.get()));
        CtClass clazz;
        try {
            clazz = pool.get(hpOdClass.getClassName());
        } catch (NotFoundException e1) {
            throw new BizException(e1);
        }
        try {
            CtField field = clazz.getField(before.getFieldName());
            AnnotationsAttribute annos = (AnnotationsAttribute) field.getFieldInfo2().getAttribute(AnnotationsAttribute.visibleTag);
            AnnotationsAttribute newAnnos = null;
            for (Annotation ann : annos.getAnnotations()) {
                if (ann.getTypeName().equals(GenColumn.class.getName())) {
                    ConstPool constPool = (ConstPool) ReflectionUtils.getFieldValue(ann, "pool");
                    ann.addMemberValue("type", new StringMemberValue(String.valueOf(after.getDataType()), constPool));
                    ann.addMemberValue("refObjectId", new StringMemberValue(String.valueOf(""), constPool));
                    ann.addMemberValue("enumId", new StringMemberValue(String.valueOf(""), constPool));
                    if (after.getDataType() == 1) {
                        ann.addMemberValue("enumId", new StringMemberValue(after.getTypeValue(), constPool));
                    } else if (after.getDataType() == 2) {
                        ann.addMemberValue("refObjectId", new StringMemberValue(after.getTypeValue(), constPool));
                    }
                    newAnnos = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                    newAnnos.addAnnotation(ann);
                }
            }
            field.getFieldInfo().removeAttribute(AnnotationsAttribute.visibleTag);
            field.getFieldInfo().addAttribute(newAnnos);
        } catch (NotFoundException e) {
            throw new BizException(e);
        }
        // FIXME 后续上传文件服务器
        String path = StringUtils.replaceChars(StringUtils.substringBeforeLast(hpOdClass.getClassName(), "."), '.',
                                               '/');
        String name = StringUtils.substringAfterLast(hpOdClass.getClassName(), ".");
        try {
            FileUtils.forceMkdir(getExtClass(path));
            FileUtils.writeByteArrayToFile(getExtClass(path + "/" + name + ".class"), clazz.toBytecode());
        } catch (IOException | CannotCompileException e) {
            throw new BizException(e);
        }
        deleteSimple(client, "/ext/class/" + hpOdClass.getClassName(), false);
        // client.delete("/ext/class/"+);
        try {
            client.createPersistent("/ext/class/" + hpOdClass.getClassName() + "/"
                                    + URLEncoder.encode(Bytes.bytes2base64(clazz.toBytecode()), "UTF-8"));
        } catch (IOException | CannotCompileException e) {
            throw new BizException(e);
        }
        return true;

    }

    private boolean isChange(HpOdAttribute before, HpOdAttribute after) {
        return before.getDataType() != after.getDataType();
    }

    private File getExtClass(String path) {
        String conf = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return new File(FileUtil.getAbsolutePathBasedOn(conf, "../ext/class/" + path));
    }

    /**
     * 只能删除有一个节点的
     *
     * @param root
     * @param delRoot
     */
    public void deleteSimple(ZkclientZookeeperClient client, String root, boolean delRoot) {
        Stack<String> stacks = new Stack<>();
        if (delRoot) {
            stacks.push(root);
        }
        while (!CollectionUtils.isEmpty(client.getChildren(root))) {
            root = root + "/" + client.getChildren(root).get(0);
            stacks.push(root);
        }
        stacks.push(root);
        while (!stacks.isEmpty()) {
            client.delete(stacks.pop());
        }
    }

}
