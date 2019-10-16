package org.yly.framework.easybox.base.user;

import lombok.Data;
import org.yly.framework.easybox.base.log.config.EasyBoxLogKey;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Data
public class EasyBoxUser extends EasyBoxBaseBean {
    private String id;

    @EasyBoxParamsTitle("账号")
    @EasyBoxCheckJavaBean(notNull = true)
    private String uname;

    @EasyBoxParamsTitle("密码")
    @EasyBoxCheckJavaBean(notNull = true)
    private String pwd;

    @EasyBoxParamsTitle("授权令牌")
    private String token;

    @EasyBoxParamsTitle("性别")
    private String sex;

    @EasyBoxParamsTitle("工号")
    private String code;

    @EasyBoxParamsTitle("邮箱")
    private String mail;

    @EasyBoxParamsTitle("手机")
    private String phone;

    @EasyBoxParamsTitle("是否可用")//YN
    private String type;
}
