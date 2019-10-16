package org.yly.framework.easybox.base.auth.menu;

import lombok.Data;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 菜单
 */
@Data
public class EasyBoxMenu extends EasyBoxBaseBean {
    private String id;
    private String name;//layui的文件夹名字
    private String title;//显示的名字
    private String icon;//图标
    private String superId;//上级

}
