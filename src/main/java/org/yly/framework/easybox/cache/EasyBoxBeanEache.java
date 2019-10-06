package org.yly.framework.easybox.cache;

import lombok.Data;
import org.yly.framework.easybox.cache.bean.EasyBoxMap;
import org.yly.framework.easybox.cache.bean.EasyBoxTabMap;
import org.yly.framework.easybox.init.RedisConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * javaBean内容缓存
 * */
@Data
public class EasyBoxBeanEache {

    /**
     * key:表名
     * value：最新版本号
     * */
    private static final EasyBoxTabMap tabEasyBoxMap = new EasyBoxTabMap();
    public static EasyBoxTabMap getTabEasyBoxMap() {
        return tabEasyBoxMap;
    }

    /**
     * 缓存的核心工具，
     *  仅当启用了缓存，切redis不存在的时候采用的策略，如果有redis将使用redis
     * key:缓存方法
     * value:
     *        key:缓存sql
     *        value：结果集的json
     * */
    private static final EasyBoxMap eacheMap= new EasyBoxMap();
    public static EasyBoxMap getEacheMap() {
        return eacheMap;
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
}
