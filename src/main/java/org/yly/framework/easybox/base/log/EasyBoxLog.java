package org.yly.framework.easybox.base.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.yly.framework.easybox.codeGen.EasyBoxCodeGenTab;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/16 0016
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 简单的日志，只记录状态
 */
@Data
@AllArgsConstructor
@EasyBoxCodeGenTab(EasyBoxLogService.tabName)
public class EasyBoxLog extends EasyBoxBaseBean {

    private String id;

    @EasyBoxParamsTitle("表名")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String tabName;

    @EasyBoxParamsTitle("时间")
    private Long time;

    @EasyBoxParamsTitle("操作人")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String userId;

    @EasyBoxParamsTitle("数据id")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String dateId;

    @EasyBoxParamsTitle("值")
    @EasyBoxCheckJavaBean(notNull = true,maxLength = 64)
    private String value;
}
