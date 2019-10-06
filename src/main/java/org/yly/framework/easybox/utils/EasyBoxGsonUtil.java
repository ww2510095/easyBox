package org.yly.framework.easybox.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class EasyBoxGsonUtil {

	public static Gson gson = null;
	
	
	public static Gson getInstance(){
		if(gson == null){
			gson = new Gson();
		}
		return gson;
	}
	
	/**
	 * 获取普通GSON实例
	 * @return
	 */
	public static Gson getNormalInstance(){
		return new Gson();
	}
	
	/**
	 * 实体类转为json字符串
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj){
		return getInstance().toJson(obj);
	}
	
	/**
	 * 将json字符串转换为指定类的实例
	 * 转换失败时返回null
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T fromJsonString(String json, Class<T> classOfT){
		T t = null;
		try {
			t = getInstance().fromJson(json, classOfT);
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return t;
	}
	
	/**
	 * 将json字符串转换为指定类的实例的集合
	 * 转换失败时返回null
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> List<T> fromJsonList(String json, Class<T> classOfT){
		ArrayList<T> list = new ArrayList<T>();
		
		JsonArray array = null;
		try {
			array = new JsonParser().parse(json).getAsJsonArray();
		} catch (Exception e) {
			return list;
		}
		if(array.size() == 0)
			return list;
		for (JsonElement element : array) {
			try {
				T t = getInstance().fromJson(element, classOfT);
				list.add(t);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
}

