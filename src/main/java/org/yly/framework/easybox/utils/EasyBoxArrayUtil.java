package org.yly.framework.easybox.utils;

import java.lang.reflect.Array;

/**
 * @author 亚里亚--罗玉波
 * 2019/10/19 0019
 * gitHub https://github.com/ww2510095/easyBox.git
 * CSDN:https://blog.csdn.net/qq_25861361
 */
public class EasyBoxArrayUtil {
    /**
     * @param array1
     * @param array2
     * @return Object[]
     * 将数组array1与array2合并成一个新的数组
     * */
    public static Object[] addAll(Object[] array1, Object[] array2) {
        if (array1 == null) {
            return null;
        } else if (array2 == null) {
            return null;
        }
        Object[] joinedArray = (Object[]) Array.newInstance(array1.getClass().getComponentType(),
                array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            final Class type1 = array1.getClass().getComponentType();
            final Class type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)){
                throw new IllegalArgumentException("Cannot store "+type2.getName()+" in an array of "+type1.getName());
            }
            throw ase;
        }
        return joinedArray;
    }
}
