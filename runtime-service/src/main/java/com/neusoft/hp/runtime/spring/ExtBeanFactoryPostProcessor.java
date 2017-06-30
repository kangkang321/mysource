package com.neusoft.hp.runtime.spring;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.io.Bytes;
import com.alibaba.dubbo.remoting.zookeeper.ChildListener;
import com.alibaba.dubbo.remoting.zookeeper.zkclient.ZkclientZookeeperClient;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.util.M2Util;
import com.neusoft.core.webx.util.FileUtil;

public class ExtBeanFactoryPostProcessor implements ApplicationListener<ContextStartedEvent> {

    private Set<String> classs = new HashSet<>();

    private File getExtClass(String path) {
        String conf = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return new File(FileUtil.getAbsolutePathBasedOn(conf, "../ext/class/" + path));
    }

    private void createFile(ZkclientZookeeperClient client, String s) {
        // FIXME 后续上传文件服务器
        String path = StringUtils.replaceChars(StringUtils.substringBeforeLast(s, "."), '.', '/');
        String name = StringUtils.substringAfterLast(s, ".");
        List<String> datas = client.getChildren("/ext/class/" + s);
        byte[] clazz = null;
        if (!CollectionUtils.isEmpty(datas)) {
            try {
                clazz = Bytes.base642bytes(URLDecoder.decode(datas.get(0), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new BizException(e);
            }
            try {
                FileUtils.forceMkdir(getExtClass(path));
                FileUtils.writeByteArrayToFile(getExtClass(path + "/" + name + ".class"), clazz);
            } catch (IOException e) {
                throw new BizException(e);
            }
        }
        M2Util.refresh();
    }

    private void addListener(ZkclientZookeeperClient client, String s) {
        client.addChildListener("/ext/class/" + s, new ChildListener() {

            @Override
            public void childChanged(String path, List<String> children) {
                if (CollectionUtils.isEmpty(children)) {
                    // 删除
                } else {
                    createFile(client, s);// 更新文件
                }
            }
        });
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        ZkclientZookeeperClient client = new ZkclientZookeeperClient(new URL("zookeeper",
                                                                             System.getProperty("ext.class.host"),
                                                                             Integer.valueOf(System.getProperty("ext.class.port"))));
        List<String> childrens = client.getChildren("/ext/class");
        if (!CollectionUtils.isEmpty(childrens)) {
            childrens.stream().forEach(s -> {
                createFile(client, s);
                addListener(client, s);
                classs.add(s);
            });
        }
        client.addChildListener("/ext/class", new ChildListener() {

            @Override
            public void childChanged(String path, List<String> children) {
                children.stream().filter(s -> !classs.contains(s)).forEach(s -> {
                    createFile(client, s);
                    addListener(client, s);
                    classs.add(s);
                });
            }

        });
    }

}
