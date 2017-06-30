package com.neusoft.core.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import com.neusoft.core.dao.GenDO;
import com.neusoft.core.dao.GenJoinDO;
import com.neusoft.core.exception.BizException;
import com.neusoft.core.service.annotation.GenTable;

/**
 * 类AnnotationBean.java的实现描述： 扫描所有实体，建立实体跟objId的映射
 * 
 * @author Administrator 2017年4月21日 下午2:30:48
 */
public class AnnotationBean implements BeanFactoryPostProcessor, ApplicationContextAware {

    private String                                  annotationPackage = "com.neusoft";

    /**
     * 单实体查询
     */
    private Map<String, Class<? extends GenDO>>     genDO             = new HashMap<>();

    /**
     * 多实体查询
     */
    private Map<String, Class<? extends GenJoinDO>> genJoinDO         = new HashMap<>();

    private ApplicationContext                      applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        scannerGenTable();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void scannerGenTable() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(GenTable.class));
        provider.setResourceLoader(applicationContext);
        Set<BeanDefinition> beans = provider.findCandidateComponents(annotationPackage);
        if (CollectionUtils.isEmpty(beans)) {
            return;
        }
        beans.forEach(b -> {
            try {
                Class clazz = ClassUtils.forName(b.getBeanClassName(), applicationContext.getClassLoader());
                GenTable table = (GenTable) clazz.getAnnotation(GenTable.class);
                if (table.parentClass() == GenDO.class) {
                    // 多实体的query
                    if (GenJoinDO.class.isAssignableFrom(clazz)) {
                        genJoinDO.put(table.objId(), clazz);
                    } else {
                        // 实体的select
                        genDO.put(table.objId(), clazz);
                        // 单实体的query
                        if (table.isSingle()) {
                            genJoinDO.put(table.objId(), clazz);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new BizException(e);
            }
        });
    }

    public String getAnnotationPackage() {
        return annotationPackage;
    }

    public void setAnnotationPackage(String annotationPackage) {
        this.annotationPackage = annotationPackage;
    }

    public Map<String, Class<? extends GenDO>> getGenDO() {
        return genDO;
    }

    public void setGenDO(Map<String, Class<? extends GenDO>> genDO) {
        this.genDO = genDO;
    }

    public Map<String, Class<? extends GenJoinDO>> getGenJoinDO() {
        return genJoinDO;
    }

    public void setGenJoinDO(Map<String, Class<? extends GenJoinDO>> genJoinDO) {
        this.genJoinDO = genJoinDO;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
