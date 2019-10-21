package org.yly.framework.easybox.codeGen;

import org.yly.framework.easybox.init.scan.EasyBoxInit;
import org.yly.framework.easybox.mybatis.bean.dataInterface.EasyBoxParamsTitle;
import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;
import org.yly.framework.easybox.utils.EasyBoxCheckJavaBean;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.exception.EasyBoxCheckException;
import org.yly.framework.easybox.utils.sqlUtil.EasyBoxSqlUtil;
import org.yly.framework.easybox.utils.sqlUtil.sqlInterface.EasyBoxNotParams;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/19 0019
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * 可以把javaBean组装成sql
 */

public class EasyBoxJavaBeanToSql {


    /**
     * 新建表，值得注意的是必须要在运行时使用，否则会抛出NoClassDefFoundError
     * @param packageName 需要解锁的包名
     * */
    public   String createAllTab(String packageName){
            List<String> liststr = EasyBoxInit.getClassNameA(packageName);
            int ok=0;
            int no=0;
            int tab=0;
            String tabSql ="";
            for(String str:liststr){
                try{
                    Class clazz = Class.forName(str);
                    String s =toSql(clazz);
                    if(s==null){
                        continue;
                    }
                    tabSql=tabSql+s;
                    ok=ok+1;
                }catch (Exception e){
                    no=no+1;
                }
                tab=tab+1;
            }
            System.out.println("总共有"+tab+"张表，成功"+ok+"次。失败"+no+"次");
            return tabSql;
    }
    public   String toSql(Class<?> clazz){
        EasyBoxCodeGenTabKey mEasyBoxCodeGenTabKey = clazz.getAnnotation(EasyBoxCodeGenTabKey.class);
        if(mEasyBoxCodeGenTabKey==null){
            System.out.println(clazz.getSimpleName()+"不是一张表，忽略");
            return null;
        }
        StringBuilder sb = new StringBuilder();//主sql
        sb.append(" create table ");
        String tabName;
        String idKey=null;
        EasyBoxCodeGenTab mEasyBoxCodeGenTabName=clazz.getAnnotation(EasyBoxCodeGenTab.class);
        if(mEasyBoxCodeGenTabName==null||EasyBoxStringUtil.isBlank(mEasyBoxCodeGenTabName.value())){
            tabName=clazz.getSimpleName();
        }else{
            idKey=mEasyBoxCodeGenTabName.id();
            tabName=mEasyBoxCodeGenTabName.value();
        }
        sb.append(tabName);
        StringBuilder sb2 = new StringBuilder();//注释
        sb.append(" ( ");
        sb2.append("\n");
        Field[] mFields =clazz.getDeclaredFields();
        for(Field mField:mFields){
            EasyBoxNotParams mEasyBoxNotParams = mField.getAnnotation(EasyBoxNotParams.class);
            if(mEasyBoxNotParams!=null){
                continue;
            }
            sb.append(EasyBoxSqlUtil.getColumnName(mField));
            sb.append(getJdbcTypeSql(mField));
            sb.append(" , ");
            sb.append("\n");
            sb2.append(getCommentSql(tabName,mField));
            sb2.append("\n");
        }
        sb=new StringBuilder(sb.substring(0,sb.length()-3));
        sb2.append("\n");
        sb.append(");\n");
        sb.append(sb2);
        if(idKey!=null){
            sb.append(" alter table ");
            sb.append(tabName);
            sb.append(" add constraint ");
            sb.append(tabName);
            sb.append("_");
            sb.append(idKey);
            sb.append(" primary key (");
            sb.append(idKey);
            sb.append(");");
        }
        return sb.toString();
    }
    public  final String getColumnSql(String tabName,Field mField){
        return new StringBuilder()
                .append(" alter table ")
                .append(tabName)
                .append(" add ")
                .append(EasyBoxSqlUtil.getColumnName(mField))
                .append(getJdbcTypeSql(mField))
                .append(";")
                .append(getCommentSql(tabName,mField))
                .toString();
    }
    /**
     * 获取添加类型sql
     * */
    private   String getJdbcTypeSql(Field mField){
        StringBuilder sb =new StringBuilder();
        EasyBoxCheckJavaBean mEasyBoxCheckJavaBean = mField.getAnnotation(EasyBoxCheckJavaBean.class);
        if(mField.getType().equals(boolean.class)||mField.getType().equals(Boolean.class)){
            sb.append(" varchar2(5) ");
        }else if(EasyBoxBaseService.isBaseType(mField.getType())){
            sb.append(" number(");
            String s =mField.getType().toString().toUpperCase();
            if(s.contains("INT")){
                sb.append((Integer.MAX_VALUE+"").length()-1);
                sb.append(" ) ");
            }else if(s.contains("LONG")){
                sb.append((Long.MAX_VALUE+"").length()-1);
                sb.append(" ) ");
            }else if(s.contains("SHORT")){
                sb.append((Short.MAX_VALUE+"").length()-1);
                sb.append(" ) ");
            }else{
                sb.append((Integer.MAX_VALUE+"").length()+2);
                sb.append(",3) ");
            }

        }else if(mField.getType().equals(String.class)){
            if(EasyBoxStringUtil.isBlank(mEasyBoxCheckJavaBean)){
                sb.append(" varchar2(255) ");
            }else{
                if(mEasyBoxCheckJavaBean.number()){
                    sb.append(" number(");
                    sb.append((mEasyBoxCheckJavaBean.numberMax()+"").length());
                    sb.append(") ");

                }else{
                    if(mEasyBoxCheckJavaBean.maxLength()==-1){
                        sb.append(" varchar2(255) ");
                    }else{
                        if(mEasyBoxCheckJavaBean.maxLength()>4000){
                            sb.append(" clob ");
                        }else{
                            sb.append(" varchar2(");
                            sb.append(mEasyBoxCheckJavaBean.maxLength()+1);
                            sb.append(") ");
                        }

                    }
                }

            }
        }else if(mField.getType().equals(Date.class)){
            sb.append(" timestamp ");
            System.err.println("不建议使用date类型，建议使用时间戳(long)");

        }else{
            throw new EasyBoxCheckException("未知的类型，只支持String，Date，以及基本类型");
        }
        return sb.toString();
    }
    /**
     * 获取添加注释的sql
     * */
    public  String getCommentSql(String tabName,Field mField){
        StringBuilder sb2 =new StringBuilder();
        EasyBoxParamsTitle mEasyBoxParamsTitle=mField.getAnnotation(EasyBoxParamsTitle.class);
        sb2.append("comment on column ");
        sb2.append(tabName);
        sb2.append(".");
        sb2.append(EasyBoxSqlUtil.getColumnName(mField));
        if(mEasyBoxParamsTitle==null||EasyBoxStringUtil.isBlank(mEasyBoxParamsTitle.value())){
            sb2.append(" is '';");
        }else{
            sb2.append(" is '");
            sb2.append(mEasyBoxParamsTitle.value());
            sb2.append(" '; ");
        }
        return sb2.toString();
    }


}
