package org.yly.framework.easybox.cache;

import lombok.Data;
import org.yly.framework.easybox.cache.bean.EasyBoxTabMap;
import org.yly.framework.easybox.init.RedisConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 缓存的核心工具，
 * 如果存在redis的时候采用redis，不存在采用内存缓存
 *      暂不支持redis
 * */
@Data
public class EasyBoxBeanEache {
    /**
     * key:sessionId
     * value：
     *       key:线程Id
     *       value:用户实体
     * */
    private static final Map<String,Map<Long,Object>> userMap = new HashMap<>();
    public static Map<String, Map<Long, Object>> getUserMap() {
        return userMap;
    }

    /**
     * key:表名
     * value：最新版本号
     * */
    private static final EasyBoxTabMap tabEasyBoxMap = new EasyBoxTabMap();
    public static EasyBoxTabMap getTabEasyBoxMap() {
        return tabEasyBoxMap;
    }


    /**
     * key：用于存储javaben的名字
     * value:需要查询的字段，已经解码完成，
     * 采用预编译的方式组合sql字段，省去了这一个步骤，可以略微提升效率
     * 仅当策略组为speed的时候生效，如果为Resources则不生效
     * */
    private static final Map<String,String> sSqlMap= new HashMap<>();
    public static Map<String, String> getsSqlMap() {
        return sSqlMap;
    }
    /**
     * redis的集群
     * */
    private static final List<RedisConfig> sListRedisConfig = new ArrayList<>();
    public static List<RedisConfig> getsListRedisConfig() {
        return sListRedisConfig;
    }
    /**
     * key:表名
     * value：简单日志表的字段
     * */
    private static final Map<String,String> logKey = new HashMap<>();
    public static Map<String, String> getLogKey() {
        return logKey;
    }
}
