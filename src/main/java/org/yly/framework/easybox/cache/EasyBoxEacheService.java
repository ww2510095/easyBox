package org.yly.framework.easybox.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yly.framework.easybox.mybatis.EasyBoxSqlException;
import org.yly.framework.easybox.mybatis.bean.EasyBoxSql;
import org.yly.framework.easybox.mybatis.dao.EasyBoxBaseDao;

import java.util.List;
import java.util.Map;

/**
 * 缓存服务核心类
 * */
public class EasyBoxEacheService {

    /**
     * 获取数据表的内容
     * */
   public  List<Map> getAll(EasyBoxSql mEasyBoxSql,String tabName,EasyBoxBaseDao cYlyBaseDao){
       mEasyBoxSql.setCount(false);
       return (List<Map>)EasyBoxBeanEache.getEacheMap()
               .getData(tabName,mEasyBoxSql.toString(),EasyBoxEachMapKey.GETALL,cYlyBaseDao);
   }
   public Integer getAllCount(EasyBoxSql mEasyBoxSql,String tabName,EasyBoxBaseDao cYlyBaseDao){
       mEasyBoxSql.setCount(true);
       return Integer.valueOf(EasyBoxBeanEache.getEacheMap()
               .getData(tabName,mEasyBoxSql.toString(),EasyBoxEachMapKey.GETALLCOUNT,cYlyBaseDao).toString());
   }
    /**
     * 单纯的执行一条sql，不求返回值，除了主体sql所有的参数无效
     * */
    public void exeSql(EasyBoxSql mEasyBoxSql,String tabName,EasyBoxBaseDao cYlyBaseDao){
        String sql = mEasyBoxSql.getSql().toUpperCase();
        if(sql.trim().indexOf("UPDATE")==0||sql.trim().indexOf("DELETE")==0||sql.trim().indexOf("INSERT")==0){
            EasyBoxBeanEache.getEacheMap().addVersion(tabName);
        }
         cYlyBaseDao.exeSql(mEasyBoxSql);
    }
    /**
     * 查询sql，所有参数有效
     * */
    public List<Map> exeSelectSql(EasyBoxSql mEasyBoxSql,String tabName,EasyBoxBaseDao cYlyBaseDao){
        return (List<Map>)EasyBoxBeanEache.getEacheMap()
                .getData(tabName,mEasyBoxSql.toString(),EasyBoxEachMapKey.EXESELECTSQL,cYlyBaseDao);
    }
    /**
     * 得到对应的参数的值+1
     * */
    public Integer getParamAddOne(String tabName,String column,EasyBoxBaseDao cYlyBaseDao){
        try{
            String sql = "select nvl(max(to_number("+column+")),0)+1 from "+tabName;
            return cYlyBaseDao.getParamAddOne(sql);
        }catch (Exception e){
            throw new EasyBoxSqlException("给定字段"+column+"错误，该列不存在或者不能换换为number");
        }
    }

}
