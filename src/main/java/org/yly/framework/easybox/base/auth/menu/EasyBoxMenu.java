package org.yly.framework.easybox.base.auth.menu;

import lombok.Data;
import org.yly.framework.easybox.codeGen.EasyBoxCodeGenTab;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/16
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 菜单
 */
@Data
@EasyBoxCodeGenTab(EasyBoxMenuService.tabName)
public class EasyBoxMenu extends EasyBoxBaseBean {
    private String id;

    @EasyBoxParamsTitle("文件夹名字")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 20)
    private String name;

    @EasyBoxParamsTitle("界面显示的名字")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 20)
    private String title;

    @EasyBoxParamsTitle("图标")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 20)
    private String icon;

    @EasyBoxParamsTitle("上级id")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String superId;

}
