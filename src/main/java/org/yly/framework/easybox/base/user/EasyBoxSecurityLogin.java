package org.yly.framework.easybox.base.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yly.framework.easybox.base.RequestType;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.mybatis.Controller.EasyBoxBaseController;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;
import org.yly.framework.easybox.utils.EasyBoxBeanUtil;
import org.yly.framework.easybox.utils.EasyBoxUserils;
import org.yly.framework.easybox.utils.YesNo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/17 0017
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 *
 */
@RestController
@RequestMapping("a")
public class EasyBoxSecurityLogin extends EasyBoxBaseController {



    @Autowired
    private EasyBoxUserService cEasyBoxUserService;

    @Override
    protected EasyBoxBaseService<? extends EasyBoxBaseBean> getService() {
        return cEasyBoxUserService;
    }

    @RequestMapping("easyBoxLogin")
    public RequestType login(EasyBoxUser mEasyBoxUser, HttpServletRequest mHttpServletRequest)throws Exception {
        List<Map> listMap = cEasyBoxUserService.getByParam("userName",mEasyBoxUser.getUserName());
            if(listMap.size()!=1){
                return sendFalse("用户名不存在");
            }
        String password =  mEasyBoxUser.getPwd();
        mEasyBoxUser = EasyBoxBeanUtil.map2JavaBean(listMap.get(0),EasyBoxUser.class);
        if(mEasyBoxUser.getPwd().equals(password)){
            if(mEasyBoxUser.getType().equals(YesNo.N.toString())){
                return sendFalse("用户名被封禁");
            }
            mHttpServletRequest.getSession().setAttribute(EasyBoxUserils.easyBoxUserSessionKey,mEasyBoxUser);
            EasyBoxUserils.updateUser(mHttpServletRequest.getSession());
            return sendTrueData(mEasyBoxUser);
        }else{
            return sendFalse("密码错误");
        }
    }
    @RequestMapping("easyBoxLogOut")
    public RequestType easyBoxLogOut( HttpServletRequest mHttpServletRequest)throws Exception {
        EasyBoxBeanEache.getUserMap().remove(mHttpServletRequest.getSession().getId());
        mHttpServletRequest.getSession().removeAttribute(EasyBoxUserils.easyBoxUserSessionKey);
        return sendTrueMsg("退出成功");
    }

}