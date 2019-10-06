package org.yly.framework.easybox.utils.sqlUtil;

import org.yly.framework.easybox.utils.EasyBoxStringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期格式化
 * 
 */
public class EasyBoxDateUtil {


	/**
	 * @param date
	 *            需要格式化的日期对像
	 * @param formatter
	 *            格式化的字符串形式
	 * @return 按照formatter指定的格式的日期字符串
	 * @throws java.text.ParseException
	 *             无法解析的字符串格式时拋出
	 */
	public static String formatDateToStr(Date date, String formatter) {
		if (date == null)
			return "";
		try {
			return new SimpleDateFormat(formatter).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String long2StringDate(Long time) {
		if(time==null)return "";
		return formatDateToStr(new Date(time),"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}




	/**
	 * 将文字日期转换为时间错，支持任意格式,但必须是标准的时间格式
	 * 注意：如果后面的值是空的，则最后一位的下一位会有1的误差
	 * 比如yyyy-mm,那么得出的值会有一天误差，需要+1天时间
	 * 比如yyyy-mm-dd 那么将会有一小时的误差
	 * @param strDate
	 */
	public static long strToLong(String strDate) {
		try {
			return Long.valueOf(strDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String err ="错误的时间格式";
		if(EasyBoxStringUtil.isBlank(strDate))throw new RuntimeException(err);
		String skey ="";
		for(int i=0;i<strDate.length();i++) {
			if(strDate.charAt(i)>47&&strDate.charAt(i)<58) {
				skey=skey+strDate.charAt(i);
			}
		}
		if(skey.length()>17) {
			throw new RuntimeException(err);
		}
		if(skey.length()<17) {
			int a =17-skey.length();
			 strDate="";
			for(int i=0;i<a;i++) {
				strDate=strDate+"0";
			}
			skey=skey+strDate;
		}
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    try {
	    	  Date date = simpleDateFormat.parse(skey);
	    	  long ts = date.getTime();
	    	  return ts;
		} catch (Exception e) {
			throw new RuntimeException(err);
		}
	}


}
