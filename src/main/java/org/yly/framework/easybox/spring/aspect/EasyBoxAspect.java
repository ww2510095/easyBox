package org.yly.framework.easybox.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.security.EasyBoxSecurity;
import org.yly.framework.easybox.spring.bean.EasyBoxAspectException;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/10 0010
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Aspect
@Component
public class EasyBoxAspect {

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint pjp)throws  Throwable{
       Object[] args = pjp.getArgs();
        List<String> listErr = new ArrayList<>();
        int index = -1;
       for(int i=0;i<args.length;i++){
           if(args[i]!=null&&args[i] instanceof EasyBoxAspectException){
               if(i!=-1){
                   throw new EasyBoxCheckException("出现错误，一个方法只能有一个EasyBoxAspectException参数");
               }
               index=i;
           }
           if(args[i]!=null&&args[i] instanceof EasyBoxBaseBean){
               try{
                   EasyBoxSecurity.checkJavaBean(args[i]);
               }catch (EasyBoxCheckException e){
                   listErr.add(e.getMessage());
               }
           }
           if(listErr.size()!=0){
                if(i==-1){
                    throw new  EasyBoxCheckException(listErr.get(0));
                }else{
                    EasyBoxAspectException mEasyBoxAspectException = (EasyBoxAspectException) args[index];
                    mEasyBoxAspectException.setEasyBoxException(listErr);
                    args[index]=mEasyBoxAspectException;
                }
           }
       }
        Object obj=pjp.proceed(args);
        return obj;
    }
}
