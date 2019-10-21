package org.yly.framework.easybox.utils.sqlUtil;

import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxColumn;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxDataType;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxDeCode;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxNotParams;

import java.lang.reflect.Field;

/**
 * @author 亚里亚--罗玉波
 *  2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 自动装配Sql
 */
public class EasyBoxSqlUtil {
    private EasyBoxSqlUtil(){}

    public static final String getColumnSql(Object mObj,String mTabName){
        if(!EasyBoxStringUtil.isBlank(EasyBoxBeanEache.getsSqlMap().get(mTabName))){
            return EasyBoxBeanEache.getsSqlMap().get(mTabName);
        }
        if(mObj==null){
            return mTabName+".*";
        }
        Class<?> clazzA = mObj.getClass();
        StringBuilder skey=new StringBuilder();//字段
        StringBuilder sb ;//翻译

        Field[] fields = clazzA.getDeclaredFields();
        boolean isIndex=true;
        for(Field mField :fields) {
           String mfiledColumn= getSqlParams(mField);
           if(mfiledColumn==null){
               continue;
           }
           if(!isIndex){
               skey.append(",");
           }
            isIndex=false;
            EasyBoxDeCode mDeCode =mField.getAnnotation(EasyBoxDeCode.class);
            if(mDeCode!=null) {
                if(mDeCode.dcode().length!=0) {
                    sb =new StringBuilder("decode(");
                    sb.append(mfiledColumn);
                    sb.append(",'");
                    for(int a=0;a<mDeCode.dcode().length;a++) {
                        sb.append(mDeCode.dcode()[a]);
                        sb.append("','");
                    }

                    skey.append(sb.substring(0, sb.length()-2));
                    skey.append(")");
                }else if(mDeCode.dataLong()) {
                    sb =new StringBuilder();
                    if(mField.getType().equals(java.lang.String.class)) {
                        sb.append("TO_CHAR(");
                    }
                    sb.append(mfiledColumn);
                    sb.append("/ (1000 * 60 * 60 * 24) + TO_DATE('1970-01-01 08:00:00', 'YYYY-MM-DD HH:MI:SS')");
                    if(mField.getType().equals(java.lang.String.class)) {
                        sb.append(", 'YYYY-MM-DD HH:MI:SS')");
                    }
                    skey.append(sb);
                }
                EasyBoxDataType mDataType = mDeCode.alias();
                if(!EasyBoxStringUtil.isBlank(mDataType.alias())) {
                    if(mDataType.type()==EasyBoxDataType.DataType_Type.DATE) {
                        //日期型
                        skey.append("to_char(");
                        skey.append(mfiledColumn);
                        skey.append(",'yyyy-mm-dd hh24:mi:ss')");
                    }
                    skey.append(" ");
                    skey.append(mDataType.alias());
                    skey.append(",");
                    skey.append(mfiledColumn);
                }else {
                    skey.append(mfiledColumn);
                }
            }else {
                skey.append(mfiledColumn);
            }


        }
        EasyBoxBeanEache.getsSqlMap().put(mTabName,skey.toString());
        return skey.toString();
    }
    /**
     * 获取数据库里面的字段名，以默认的名字，采用注解的方式去除非字段的成员
     * */
    public static final String  getSqlParams(Field field) {
        EasyBoxNotParams mNotParams=field.getAnnotation(EasyBoxNotParams.class);
        if(mNotParams!=null){
            return null;
        }
        return getColumnName(field);
    }
    public static final String getColumnName(Field mField){
        EasyBoxColumn mEasyBoxColumn =mField.getAnnotation(EasyBoxColumn.class);
        if(mEasyBoxColumn==null||EasyBoxStringUtil.isBlank(mEasyBoxColumn.value())){
            return mField.getName().toUpperCase();
        }else{
            return mEasyBoxColumn.value().toUpperCase();
        }
    }
}
