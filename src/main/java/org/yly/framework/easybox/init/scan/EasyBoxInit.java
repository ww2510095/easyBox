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
import org.yly.framework.easybox.base.log.config.EasyBoxLogKey;
import org.yly.framework.easybox.base.log.config.EasyBoxLogParam;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.init.EasyBoxScan;
import org.yly.framework.easybox.mybatis.dao.EasyBoxBaseDao;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Slf4j
public class EasyBoxInit implements ImportBeanDefinitionRegistrar {

    private void initEache(){

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        initEache();
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EasyBoxScan.class.getName()));

        List<String> basePackages = new ArrayList<String>();
        List<String> listClass  = new ArrayList<>();
        for (String pkg : annoAttrs.getStringArray("beanUrl")) {
            if (StringUtils.hasText(pkg)) {
                if(pkg.contains("*")){
                    throw new EasyBoxCheckException("只需要指定包名就可以了，也可以是指定类，不需要加*");
                }
                basePackages.add(pkg);
                listClass.addAll(getClassNameA(pkg));
            }
        }
        for(String str:listClass){
            try {
                EasyBoxBaseService mEasyBoxBaseService = (EasyBoxBaseService)Class.forName(str).newInstance();
                EasyBoxLogKey mEasyBoxLogKey = mEasyBoxBaseService.getClass().getAnnotation(EasyBoxLogKey.class);
                if(mEasyBoxLogKey!=null){
                    if(EasyBoxStringUtil.isBlank(mEasyBoxLogKey.value())){
                        throw new EasyBoxCheckException(mEasyBoxBaseService.getClass().getName()
                                        +"错误,EasyBoxLogKey的值不可为空");
                    }
                    EasyBoxLogParam.getLogKey().put(mEasyBoxBaseService.getTableName(),mEasyBoxLogKey.value());
                }
            }catch (Exception e){}

        }

    }





    public static List<String> getClassNameA(String packageName) {
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
        List<String> fileNames = getClassNameB(filePath);
        return fileNames;
    }

    private static List<String> getClassNameB(String filePath) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassNameB(childFile.getPath()));
            } else {
                String childFilePath = childFile.getPath();
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                childFilePath = childFilePath.replace("\\", ".");
                myClassName.add(childFilePath);
            }
        }

        return myClassName;

}

}