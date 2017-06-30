package com.neusoft.hp.runtime.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import com.baidu.disconf.client.common.update.IDisconfUpdatePipeline;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.neusoft.hp.runtime.factory.HandlerFactory;

/**
 * 类ListenerChain.java的实现描述：FIXME 跟disconf结合起来的一个配置类
 * 
 * @author Administrator 2017年3月15日 下午2:21:24
 */
public class HandlerChain implements IDisconfUpdatePipeline

{

    private static final Logger       LOGGER       = LoggerFactory.getLogger(HandlerChain.class);

    @Autowired
    public HandlerFactory             handlerFactory;

    private Map<String, String>       cacheHandler = new ConcurrentHashMap<>();

    public static Map<String, String> handlerMap   = new HashMap<>();

    public Set<EntityOperatorHandlerAdapter> getEntityOperatorListener(String objId) {
        Set<EntityOperatorHandlerAdapter> sets = new TreeSet<EntityOperatorHandlerAdapter>();
        LOGGER.debug("获得Handler开始");
        if (StringUtils.isNotEmpty(getHandler("global"))) {
            sets.addAll(Stream.of(getHandler("global").split(",")).map(m -> handlerFactory.getHandler(m)).filter(h -> isEntityOperatorHandlerAdapter(h)).map(s -> (EntityOperatorHandlerAdapter) s).collect(Collectors.toList()));
        }
        if (StringUtils.isNotEmpty(getHandler(objId))) {
            sets.addAll(Stream.of(getHandler(objId).split(",")).map(m -> handlerFactory.getHandler(m)).filter(h -> isEntityOperatorHandlerAdapter(h)).map(s -> (EntityOperatorHandlerAdapter) s).collect(Collectors.toList()));
        }
        LOGGER.debug("获得Handler结束");
        return sets;
    }

    private boolean isEntityOperatorHandlerAdapter(BaseHandler handler) {
        if (null != handler && handler instanceof EntityOperatorHandlerAdapter) {
            return true;
        }
        return false;
    }

    private String getHandler(String type) {
        if (cacheHandler.containsKey(type)) {
            return cacheHandler.get(type);
        } else {
            Map<String, Object> maps = DisconfDataGetter.getByFile("myHandler.properties");
            String handler = null;
            if (CollectionUtils.isEmpty(maps)) {
                // 解析本地myHandler.properties
                // String rootPath = getClass().getResource("/").getFile().toString();
                // rootPath = rootPath.substring(1) + "myHandler.properties";
                Object value = null;
                try {
                    value = PropertiesLoaderUtils.loadProperties(new ClassPathResource("myHandler.properties")).get(type);
                } catch (IOException e) {
                    LOGGER.error("invalid myHandler.properties");
                }
                String val = value == null ? null : String.valueOf(value);
                if (StringUtils.isNotBlank(val)) {
                    handler = val;
                } else {
                    handler = "";
                }
            } else {
                handler = null == maps.get(type) ? "" : String.valueOf(maps.get(type));
            }
            cacheHandler.put(type, handler);
            return handler;
        }
    }

    @Override
    public void reloadDisconfFile(String key, String filePath) throws Exception {
        if (StringUtils.equals(key, "myHandler.properties")) {
            cacheHandler = new ConcurrentHashMap<>();
        }
    }

    @Override
    public void reloadDisconfItem(String key, Object content) throws Exception {
    }

    // public static String getPropertiesByKey(String filePath, String key) {
    // if (handlerMap.isEmpty()) {
    // Properties prop = new Properties();
    // try {
    // // 读取属性文件
    // InputStream in = new BufferedInputStream(new FileInputStream(filePath));
    // prop.load(in); /// 加载属性列表
    // Iterator<String> it = prop.stringPropertyNames().iterator();
    // while (it.hasNext()) {
    // String k = it.next();
    // handlerMap.put(k, prop.getProperty(k));
    // }
    // in.close();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // String value = null;
    // if (!handlerMap.isEmpty()) {
    // value = handlerMap.get(key);
    // }
    // return value;
    // }

}
