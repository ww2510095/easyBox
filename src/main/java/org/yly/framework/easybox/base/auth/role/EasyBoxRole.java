package org.yly.framework.easybox.base.auth.role;

import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;
import org.yly.framework.easybox.utils.codeGen.EasyBoxCodeGenTab;

import lombok.Data;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 权限树
 */
@Data
@EasyBoxCodeGenTab(EasyBoxRoleService.tabName)
public class EasyBoxRole extends EasyBoxBaseBean {
    private String id;

    @EasyBoxParamsTitle("角色名字名字")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String name;

    @EasyBoxParamsTitle("菜单id")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String menuId;
}
