package org.yly.framework.easybox.init.scan;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yly.framework.easybox.base.log.config.EasyBoxLogKey;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.init.EasyBoxScan;
import org.yly.framework.easybox.mybatis.Controller.EasyBoxBaseController;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxInit implements ImportBeanDefinitionRegistrar {


    private void initEache(){

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        initEache();
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EasyBoxScan.class.getName()));

        List<String> basePackages = new ArrayList<>();
        List<String> listClass  = new ArrayList<>();
        boolean createTab=annoAttrs.getBoolean("createTab");
        for (String pkg : annoAttrs.getStringArray("beanUrl")) {
            if (StringUtils.hasText(pkg)) {
                if(pkg.contains("*")){
                    throw new EasyBoxCheckException("只需要指定包名就可以了，也可以是指定类，不需要加*");
                }
                basePackages.add(pkg);
                listClass.addAll(getClassNameA(pkg));
            }
        }
        listClass.addAll(getClassNameA("org.yly.framework.easybox.base"));
        for(String str:listClass){
            try{
                EasyBoxBaseController mmController =(EasyBoxBaseController) Class.forName(str).newInstance();
                RequestMapping mRequestMapping =mmController.getClass().getAnnotation(RequestMapping.class);
                if(EasyBoxStringUtil.isBlank(mRequestMapping)||mRequestMapping.value().length==0){
                    throw new EasyBoxCheckException(mmController.getClass().getSimpleName()
                            +" 错误,EasyBoxBaseController的子类必须要指定RequestMapping，并且value不可为空");
                }
            }catch (Exception e){
                if(e instanceof EasyBoxCheckException){
                    throw new EasyBoxCheckException(e.getMessage());
                }
            }
            try {
                EasyBoxBaseService mEasyBoxBaseService = (EasyBoxBaseService)Class.forName(str).newInstance();
                EasyBoxLogKey mEasyBoxLogKey = mEasyBoxBaseService.getClass().getAnnotation(EasyBoxLogKey.class);
                if(mEasyBoxLogKey!=null){
                    if(EasyBoxStringUtil.isBlank(mEasyBoxLogKey.value())){
                        throw new EasyBoxCheckException(mEasyBoxBaseService.getClass().getSimpleName()
                                        +" 错误,EasyBoxLogKey的值不可为空");
                    }
                    EasyBoxBeanEache.getLogKey().put(mEasyBoxBaseService.getTableName(),mEasyBoxLogKey.value());
                }
            }catch (Exception e){
                if(e instanceof EasyBoxCheckException){
                    throw new EasyBoxCheckException(e.getMessage());
                }
            }

            /***
             * ================================================
             * ================================================
             * ================================================
             * ================================================
             * ================================================
             * ================================================
             * ================================================
             * **/
            if(createTab){
                EasyBoxBeanEache.getsSqlMap().put(str,"");
            }


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