package org.yly.framework.easybox.init.scan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.init.EasyBoxScan;
import org.yly.framework.easybox.mybatis.dao.EasyBoxBaseDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Slf4j
public class EasyBoxInit implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;
    private Environment environment;
    private void initEache(){

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        initEache();
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EasyBoxScan.class.getName()));
        EasyBoxClassPathDgbSecurityScanner scanner = new EasyBoxClassPathDgbSecurityScanner(registry);
        // this check is needed in Spring 3.1
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annoAttrs.getStringArray("beanUrl")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        basePackages.add("org.yly.framework.easybox.mybatis.*");
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}

