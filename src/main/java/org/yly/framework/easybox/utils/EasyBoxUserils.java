package org.yly.framework.easybox.utils;

import org.yly.framework.easybox.base.user.EasyBoxUser;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/18 0018
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxUserils {
    public static final String easyBoxUserSessionKey="_easyBoxUserSessionKey_";

    public static final Object getUser(HttpSession mHttpSession){
        return mHttpSession.getAttribute(easyBoxUserSessionKey);
    }

    /**
     * 获取当前登陆用户
     * */
    public static final EasyBoxUser getUser(){
        Map<String, Map<Long, Object>> mmap = EasyBoxBeanEache.getUserMap();
        Set<String> mSet = mmap.keySet();
        for(String str:mSet){
            Object oUser =mmap.get(str).get(Thread.currentThread().getId());
            if(oUser!=null){
                return (EasyBoxUser)oUser;
            }
        }
        throw new EasyBoxCheckException("登录超时");
    }
    public static final void updateUser(HttpSession mHttpSession){
        Map<String, Map<Long, Object>> mmap = EasyBoxBeanEache.getUserMap();
        Map<Long, Object> mUser = new HashMap<>();
        mUser.put(Thread.currentThread().getId(),getUser(mHttpSession));
        mmap.put(mHttpSession.getId(),mUser);
    }
}
