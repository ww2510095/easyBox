package org.yly.framework.easybox.utils;


import org.yly.framework.easybox.mybatis.service.EasyBoxBaseService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 亚里亚--罗玉波
 * @date 2019/10/2 0002
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 * javaBean工具
 */
public class EasyBoxBeanUtil {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");// 数据库里面的data查出来的格式


    public static <T> T map2JavaBean(Map<String, Object> map, Class<T> clazz) throws Exception {
        if (map == null || map.size() == 0) {
            return clazz.newInstance();
        }
        Field[] field1 = clazz.getDeclaredFields();
        List<Field> listField = new ArrayList<Field>();
        for (Field field2 : field1) {
            listField.add(field2);
        }
        field1 = clazz.getFields();
        for (Field field2 : field1) {
            listField.add(field2);
        }
        T obj = clazz.newInstance();
        Set<String> se = map.keySet();
        for (Field field2 : listField) {
            field2.setAccessible(true);
            for (String str : se) {
                if (str.replace("_", "").toLowerCase().equals(EasyBoxBaseService.getName(field2).toLowerCase())) {
                    // String
                    if (field2.getType().equals(java.lang.String.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, map.get(str).toString());
                        }
                    } else if (field2.getType().equals(java.lang.Long.class) || field2.getType().equals(long.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, Long.valueOf(map.get(str).toString()));
                        }
                    } else if (field2.getType().equals(java.lang.Double.class)
                            || field2.getType().equals(double.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, Double.valueOf(map.get(str).toString()));
                        }
                    } else if (field2.getType().equals(java.lang.Float.class) || field2.getType().equals(float.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, Float.valueOf(map.get(str).toString()));
                        }
                    } else if (field2.getType().equals(java.lang.Boolean.class)
                            || field2.getType().equals(boolean.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, Boolean.valueOf(map.get(str).toString()));
                        }
                    } else if (field2.getType().equals(java.lang.Short.class) || field2.getType().equals(short.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, Short.valueOf(map.get(str).toString()));
                        }
                    } else if (field2.getType().equals(java.lang.Integer.class) || field2.getType().equals(int.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, Integer.valueOf(map.get(str).toString()));
                        }
                    } else if (field2.getType().equals(java.math.BigDecimal.class)) {
                        if (map.get(str) != null) {
                            field2.set(obj, new BigDecimal(map.get(str).toString()));
                        }
                    } else if (field2.getType().equals(java.util.Date.class)) {
                        if (map.get(str) != null) {
                            try {
                                long l = Long.valueOf(map.get(str).toString());
                                field2.set(obj, new Date(l));
                            } catch (Exception e) {
                                field2.set(obj, simpleDateFormat.parse(map.get(str).toString()));
                            }

                        }
                    }

                }
            }

        }
        return obj;

    }


    /**
     * 将map《String,object》 格式转换为javabean clazz字节码文件必须提供空参构造方法
     *
     * @throws Exception
     */
    public static <T> List<T> ListMap2ListJavaBean(List<Map> lmap, Class<T> clazz) throws Exception {
        if (lmap.size() == 0)
            return new ArrayList<>();
        List<T> ltmap = new ArrayList<>();
        for (Map<String, Object> map : lmap) {
            ltmap.add(map2JavaBean(map, clazz));
        }
        return ltmap;
    }
}
