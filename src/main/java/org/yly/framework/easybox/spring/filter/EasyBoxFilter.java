package org.yly.framework.easybox.spring.filter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.yly.framework.easybox.cache.EasyBoxBeanEache;
import org.yly.framework.easybox.mybatis.bean.EasyBoxBaseBean;
import org.yly.framework.easybox.mybatis.bean.EasyBoxSql;
import org.yly.framework.easybox.mybatis.dao.EasyBoxBaseDao;
import org.yly.framework.easybox.spring.util.SpringContextUtil;
import org.yly.framework.easybox.utils.EasyBoxStringUtil;
import org.yly.framework.easybox.utils.EasyBoxUserils;
import org.yly.framework.easybox.utils.codeGen.EasyBoxCodeGenTab;
import org.yly.framework.easybox.utils.codeGen.EasyBoxCodeGenTabKey;
import org.yly.framework.easybox.utils.codeGen.EasyBoxJavaBeanToSql;
import org.yly.framework.easybox.utils.sqlUtil.EasyBoxSqlUtil;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/18 0018
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */

@WebFilter(filterName = "easyBoxUserFilter", urlPatterns = "/*")
public class EasyBoxFilter  implements Filter {
	
    @Autowired
    private EasyBoxBaseDao ccEasyBoxBaseDao;

    @Override
    public void init(FilterConfig filterConfig)  {
        Map<String, String> mMap  =EasyBoxBeanEache.getsSqlMap();
        initCreateTab(mMap.keySet());
        mMap.clear();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        EasyBoxUserils.updateUser(req.getSession());
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    private void initCreateTab(Set<String> set){
        EasyBoxSql mEasyBoxSql =new EasyBoxSql();
        EasyBoxJavaBeanToSql mEasyBoxJavaBeanToSql =new EasyBoxJavaBeanToSql();
        for(String str:set){
            try{
                Class<?> clazz = Class.forName(str);
                if(clazz.toString().equals(EasyBoxBaseBean.class.toString())){
                    continue;
                }
                EasyBoxCodeGenTabKey mEasyBoxCodeGenTabKey = clazz.getAnnotation(EasyBoxCodeGenTabKey.class);
                if(mEasyBoxCodeGenTabKey==null){
                    continue;
                }
                System.out.println();
                EasyBoxCodeGenTab mEasyBoxCodeGenTabName=clazz.getAnnotation(EasyBoxCodeGenTab.class);
                String tabName;
                if(mEasyBoxCodeGenTabName==null||EasyBoxStringUtil.isBlank(mEasyBoxCodeGenTabName.value())){
                    tabName=clazz.getSimpleName();
                }else{
                    tabName=mEasyBoxCodeGenTabName.value();
                }
                tabName=tabName.toUpperCase();
                mEasyBoxSql.setSql("select nvl(count(*),0)a from user_tables where table_name =upper('"+tabName+"')");
                int iskey = Integer.valueOf(ccEasyBoxBaseDao.exeSelectSql(mEasyBoxSql).get(0).get("A").toString());
                if(iskey==0){
                   String[] sql = mEasyBoxJavaBeanToSql.toSql(clazz).replace("\n","").split(";");
                    for(int i=0;i<sql.length;i++){
                        mEasyBoxSql.setSql(sql[i]);
                        ccEasyBoxBaseDao.exeSql(mEasyBoxSql);
                    }
                }else{
                    Field[] mFields = clazz.getDeclaredFields();
                    for(Field mField :mFields){
                       String mname = EasyBoxSqlUtil.getSqlParams(mField);
                       if(mname==null){
                           continue;
                       }
                        mEasyBoxSql.setSql("select nvl(count(*),0)a from USER_TAB_COLUMNS " +
                                "where TABLE_NAME = '"+tabName+"' AND COLUMN_NAME = '"+mname+"'");
                        iskey = Integer.valueOf(ccEasyBoxBaseDao.exeSelectSql(mEasyBoxSql).get(0).get("A").toString());
                        if(iskey==0){
                            String[] mColumns= mEasyBoxJavaBeanToSql.getColumnSql(tabName,mField).split(";");
                            for(String mmColumnSql:mColumns ){
                                mEasyBoxSql.setSql(mmColumnSql);
                                ccEasyBoxBaseDao.exeSql(mEasyBoxSql);
                            }
                        }
                    }


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
