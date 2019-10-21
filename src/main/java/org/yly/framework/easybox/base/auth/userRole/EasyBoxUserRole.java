package org.yly.framework.easybox.base.auth.userRole;

import lombok.Data;
import org.yly.framework.easybox.codeGen.EasyBoxCodeGenTab;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 权限树
 */
@Data
@EasyBoxCodeGenTab(EasyBoxUserRoleService.tabName)
public class EasyBoxUserRole extends EasyBoxBaseBean {
    private String id;
    @EasyBoxParamsTitle("用户id")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String userId;
    @EasyBoxParamsTitle("角色id")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String roleId;
}
