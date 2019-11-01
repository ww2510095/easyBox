package org.yly.framework.easybox.spring.util;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.yly.framework.easybox.base.user.EasyBoxSecurityLogin;
import org.yly.framework.easybox.base.user.EasyBoxUser;
 
@Component
public class SpringContextUtil implements ApplicationContextAware {
 
 
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;
 
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	SpringContextUtil.applicationContext = applicationContext;
//    	getMappint();
//    	RequestMappingHandlerMapping requestMappingHandlerMapping=applicationContext.getBean(RequestMappingHandlerMapping.class);
//    	requestMappingHandlerMapping.registerMapping(mapping, handler, method);
    }
    void getMappint(){

    	 RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
         Method targetMethod = ReflectionUtils.findMethod(EasyBoxSecurityLogin.class, "login",EasyBoxUser.class,HttpServletRequest.class); // 找到处理该路由的方法
  
         PatternsRequestCondition patternsRequestCondition = new PatternsRequestCondition("/easyUser/easyBoxLogin");
         RequestMethodsRequestCondition requestMethodsRequestCondition = new RequestMethodsRequestCondition(RequestMethod.GET);
  
         RequestMappingInfo mapping_info = new RequestMappingInfo(patternsRequestCondition, requestMethodsRequestCondition, null, null, null, null, null);
         requestMappingHandlerMapping.registerMapping(mapping_info, "EasyBoxSecurityLogin", targetMethod); // 注册映射处理


    }
 
    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    /**
     * 通过name获取 Bean.
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
 
    /**
     * 通过class获取Bean.
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
 
    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}