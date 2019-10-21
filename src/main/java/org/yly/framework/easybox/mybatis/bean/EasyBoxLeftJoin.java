package org.yly.framework.easybox.mybatis.bean;

import java.util.Arrays;
import java.util.List;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxLeftJoin {
    private String LeftJointabName;//链接表
    private List<String> tabNameOn;//主表链接字段
    private List<String> LeftJointabNameOn;//富表链接字段
    private List<String> LeftJointabField;//链接表要查询的字段
    public String getLeftJointabName() {
        return LeftJointabName;
    }
    public EasyBoxLeftJoin setLeftJointabName(String leftJointabName) {
        LeftJointabName = leftJointabName;
        return this;
    }

    public List<String> getTabNameOn() {
        return tabNameOn;
    }
    public EasyBoxLeftJoin setTabNameOn(String... tabNameOn) {
        this.tabNameOn = Arrays.asList(tabNameOn);
        return this;
    }
    public List<String> getLeftJointabNameOn() {
        return LeftJointabNameOn;
    }
    public EasyBoxLeftJoin setLeftJointabNameOn(String... leftJointabNameOn) {
        LeftJointabNameOn = Arrays.asList(leftJointabNameOn);
        return this;
    }
    public List<String> getLeftJointabField() {
        return LeftJointabField;
    }
    public EasyBoxLeftJoin setLeftJointabField(List<String> leftJointabField) {
        LeftJointabField = leftJointabField;
        return this;
    }

}
