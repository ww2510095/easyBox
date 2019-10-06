package org.yly.framework.easybox.utils.sqlUtil.sqlInterface;



/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 别名映射
 */
public @interface EasyBoxDataType {
    //别名
    String alias() default "";

    DataType_Type type() default DataType_Type.VARCHAR;

    public enum DataType_Type {
        DATE, VARCHAR
    }

}
