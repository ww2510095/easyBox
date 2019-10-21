package org.yly.framework.easybox.base.user;

import lombok.Data;
import org.yly.framework.easybox.base.log.EasyBoxLogService;
import org.yly.framework.easybox.codeGen.EasyBoxCodeGenTab;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxDeCode;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
@Data
@EasyBoxCodeGenTab(EasyBoxUserService.tabName)
public class EasyBoxUser extends EasyBoxBaseBean {
    private String id;

    @EasyBoxParamsTitle("账号")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String userName;

    @EasyBoxParamsTitle("密码")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String pwd;

    @EasyBoxParamsTitle("授权令牌")
    @EasyBoxCheckJavaBean(maxLength = 64)
    private String token;

    @EasyBoxParamsTitle("性别")
    @EasyBoxCheckJavaBean(number = true,numberMax = 1,numberMin = 0)
    @EasyBoxDeCode(dcode = {"0","男","1","女"})
    private String sex;

    @EasyBoxParamsTitle("工号")
    @EasyBoxCheckJavaBean(maxLength = 32)
    private String code;

    @EasyBoxParamsTitle("邮箱")
    @EasyBoxCheckJavaBean(maxLength = 32)
    private String mail;

    @EasyBoxParamsTitle("手机")
    @EasyBoxCheckJavaBean(maxLength = 12,minLength = 6)
    private String phone;

    @EasyBoxParamsTitle("是否可用")//YN
    @EasyBoxCheckJavaBean(maxLength = 1)
    private String type;
}
