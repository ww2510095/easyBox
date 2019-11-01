package org.yly.framework.easybox.security;

import java.lang.reflect.Field;

import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxSecurity {

    /**
     * sql的值是否合法
     */
    public static void sqlValueInspect(String value) {
        sqlValueInspect(value, "想注入？服务器已记录你的行为");
    }
    public static void sqlValueInspect(String value, String e) {
        int a = value.indexOf(" or ");
        if (a > 0) {
        	value=value.replace("\n", "");//规范sql，避免or 1= \n 1 这种
        	value=value.replace("  ", " ");//多个空格替换掉，比较规范的sql
        	String _value = value.substring(a + 4, value.length());
            String[] sp = _value.split("=");
            if (sp.length > 1) {
                String[] sp1 = sp[1].split(" ");
                if(sp1.length>0){
                    if (sp[0].trim().equals(sp1[0].trim())) {
                        throw new EasyBoxCheckException("想注入？");// 在or后面紧跟着1=1这种语法
                    }
                }

            }
        }
    }
    public static void checkJavaBean(Object obj) {
        checkJavaBean(obj,true);
    }
    /**
     * isNull,是否验证空值
     */
    public static void checkJavaBean(Object obj, boolean isNull) {
        if (obj == null) {
            throw new EasyBoxCheckException("Some required items are not filled in, please check");
        }
        Field[] mField = obj.getClass().getDeclaredFields();
        for (Field field : mField) {
            EasyBoxCheckJavaBean mCheckJavaBean = field.getAnnotation(EasyBoxCheckJavaBean.class);
            if (mCheckJavaBean == null) {
                continue;
            }
            String title;
            EasyBoxParamsTitle mEasyBoxParamsTitle =field.getAnnotation(EasyBoxParamsTitle.class);
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (Exception e) {
                continue;
            }
            if(mEasyBoxParamsTitle!=null){
                title=mEasyBoxParamsTitle.value();
            }else{
                title=field.getName();
            }
            if (value == null||EasyBoxStringUtil.isBlank(value.toString())) {
                if (mCheckJavaBean.notNull()) {
                    if (isNull) {
                        throw new EasyBoxCheckException(title + " 不可为空");
                    }
                }

                continue;
            }
            if(mCheckJavaBean.number()) {
                Double d ;
                try {
                    d=Double.valueOf(value.toString());
                } catch (NumberFormatException e) {
                    throw new EasyBoxCheckException(title + "只能输入数字");
                }
                if(d>mCheckJavaBean.numberMax()) {
                    throw new EasyBoxCheckException(title + "最大值为"+mCheckJavaBean.numberMax());
                }
                if(d<mCheckJavaBean.numberMin()) {
                    throw new EasyBoxCheckException(title + "最小值为"+mCheckJavaBean.numberMin());
                }
            }
            if(mCheckJavaBean.maxLength()>0) {
                int length =value.toString().length();
                if(length>mCheckJavaBean.maxLength()||length<mCheckJavaBean.minLength()) {
                    throw new EasyBoxCheckException(title + "长度应在"+mCheckJavaBean.minLength()+"-"+mCheckJavaBean.maxLength()+"之间");
                }
            }


        }

    }
}
