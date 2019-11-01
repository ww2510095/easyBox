package org.yly.framework.easybox.init.scan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yly.framework.easybox.base.log.config.EasyBoxLogKey;
import org.yly.framework.easybox.base.user.EasyBoxSecurityLogin;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.init.EasyBoxScan;
import org.yly.framework.easybox.mybatis.Controller.EasyBoxBaseController;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;
import org.yly.framework.easybox.mybatis.service.EasyBoxServiceInterface;
import org.yly.framework.easybox.utils.EasyBoxClazzUtils;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxInit implements ImportBeanDefinitionRegistrar {
	private List<Class<?>> listSystemClazzs;


    private void initEache(){
		listSystemClazzs = new ArrayList<>();
		listSystemClazzs.add(EasyBoxSecurityLogin.class);
    }
    

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        initEache();
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EasyBoxScan.class.getName()));

        List<String> basePackages = new ArrayList<>();
        List<String> listClass  = new ArrayList<>();
        boolean	 createTab=annoAttrs.getBoolean("createTab");
        for (String pkg : annoAttrs.getStringArray("beanUrl")) {
            if (StringUtils.hasText(pkg)) {
                if(pkg.contains("*")){
                    throw new EasyBoxCheckException("只需要指定包名就可以了，也可以是指定类，不需要加*");
                }
                basePackages.add(pkg);
                listClass.addAll(EasyBoxClazzUtils.getClazzName(pkg));
            }
        }
        List<String> listBasePackage = EasyBoxClazzUtils.getClazzName("org.yly.framework.easybox.base");
        if(createTab) {
        	for(String str:listBasePackage){
           	 EasyBoxBeanEache.getsSqlMap().put(str,"");
           }
        }
       
        listClass.addAll(listBasePackage);
        for(String str:listClass){
            try{
            	Class<?> clazz = Class.forName(str);
            	if(listSystemClazzs.contains(clazz)) {
            		continue;
            	}
            	Object obj =clazz.newInstance();
            	if(obj instanceof EasyBoxBaseController) {
            		 RequestMapping mRequestMapping =clazz.getAnnotation(RequestMapping.class);
                     if(EasyBoxStringUtil.isBlank(mRequestMapping)||mRequestMapping.value().length==0){
                         throw new EasyBoxCheckException(clazz.getSimpleName()
                                 +" 错误,EasyBoxBaseController的子类必须要指定RequestMapping，并且value不可为空");
                     }
            	}else if(obj instanceof EasyBoxBaseService) {
            		 EasyBoxLogKey mEasyBoxLogKey = clazz.getAnnotation(EasyBoxLogKey.class);
                     if(mEasyBoxLogKey!=null){
                         if(EasyBoxStringUtil.isBlank(mEasyBoxLogKey.value())){
                             throw new EasyBoxCheckException(clazz.getSimpleName()
                                             +" 错误,EasyBoxLogKey的值不可为空");
                         }
                         EasyBoxBeanEache.getLogKey().put(((EasyBoxServiceInterface) obj).getTableName(),mEasyBoxLogKey.value());
                     }
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


}