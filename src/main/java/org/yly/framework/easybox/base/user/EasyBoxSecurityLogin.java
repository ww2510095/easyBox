package org.yly.framework.easybox.base.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yly.framework.easybox.base.RequestType;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.mybatis.Controller.EasyBoxBaseController;
import org.yly.framework.easybox.utils.EasyBoxBeanUtil;
import org.yly.framework.easybox.utils.EasyBoxUserils;
import org.yly.framework.easybox.utils.YesNo;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/17 0017
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 *
 */
public class EasyBoxSecurityLogin {

	/**
	 * 登录方法，保证您可以通过静态的EasyBoxUserils.getUser()获取当前的用户信息
	 * */
    public static void login(EasyBoxUser mEasyBoxUser, HttpServletRequest mHttpServletRequest)throws Exception {
    	mHttpServletRequest.getSession().setAttribute(EasyBoxUserils.easyBoxUserSessionKey,mEasyBoxUser);
    }
    public static void logOut( HttpServletRequest mHttpServletRequest)throws Exception {
        EasyBoxBeanEache.getUserMap().remove(mHttpServletRequest.getSession().getId());
        mHttpServletRequest.getSession().removeAttribute(EasyBoxUserils.easyBoxUserSessionKey);
    }

}