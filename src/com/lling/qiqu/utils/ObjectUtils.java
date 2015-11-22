/**
 * 
 */
package com.lling.qiqu.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * ***********************************
 * @author sandy
 * @project aider
 * @create_date 2013-10-11  下午9:55:55
 * ***********************************
 */
public class ObjectUtils {
	/**
	 * 集合是否为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static boolean isEmpty(Collection<?> s) {
		if (null == s) {
			return true;
		}
		return s.isEmpty();
	}

	/**
	 * map是否为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static boolean isEmpty(Map<?, ?> s) {
		if (null == s) {
			return true;
		}
		return s.isEmpty();
	}

	/**
	 * 字符串是否为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static boolean isEmpty(String s) {
		if (null == s) {
			return true;
		}
		return s.toString().trim().length() <= 0;
	}
	/**
	 * 对象是否为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static <T> boolean isEmpty(T s) {
		if (null == s) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * 对象是否为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static <T> boolean isEmpty(T[] s) {
		if (null == s) {
			return true;
		}
		return Array.getLength(s) < 1;
	}
	
	/**
	 * 集合不为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static boolean isNotEmpty(Collection<?> s) {
		if (null == s) {
			return false;
		}
		return !s.isEmpty();
	}

	/**
	 * map不为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static boolean isNotEmpty(Map<?, ?> s) {
		if (null == s) {
			return false;
		}
		return !s.isEmpty();
	}
	/**
	 * 字符串不为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static boolean isNotEmpty(String s) {
		if (null == s) {
			return false;
		}
		return s.toString().trim().length() > 0;
	}
	/**
	 * 对象是否为空
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:09
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static <T> boolean isNotEmpty(T s) {
		if (null == s) {
			return false;
		}
		return !isEmpty(s);
	}

	/**
	 * 转换list为 id列表
	 * ***********************************
	 * @author sandy
	 * @create_date 2013-10-11 下午10:03:18
	 * @param t
	 * @return
	 * ***********************************
	 */
	public static <T> String listToString(Collection<T> t, String keyName) {
		String methodName = "";
		StringBuilder keys = new StringBuilder();
		try {
			for (T t2 : t) {
				methodName = "get" + keyName.substring(0, 1).toUpperCase() + keyName.substring(1);
				Method method = t2.getClass().getDeclaredMethod(methodName);
				Object res = method.invoke(t2);
				if (null != res) {
					keys.append(res);
					keys.append(",");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (keys.length() > 0) {
			return keys.substring(0, keys.length() - 1);
		} else {
			return "";
		}
	}
	public static String toFirstLetterUpperCase(String str) {  
	    if(str == null || str.length() < 2){  
	        return str;  
	    }  
	     String firstLetter = str.substring(0, 1).toUpperCase();  
	     return firstLetter + str.substring(1, str.length());  
	 }  
	 /** 
     * 整型转换为4位字节数组 
     * @param intValue 
     * @return 
     */  
    public static byte[] int2Byte(int intValue) {  
        byte[] b = new byte[4];  
        for (int i = 0; i < 4; i++) {  
            b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);  
            //System.out.print(Integer.toBinaryString(b[i])+" ");  
            //System.out.print((b[i] & 0xFF) + " ");  
        }  
        return b;  
    }  
    /** 
     * 4位字节数组转换为整型 
     * @param b 
     * @return 
     */  
    public static int byte2Int(byte[] b) {  
        int intValue = 0;  
//        int tempValue = 0xFF;  
        for (int i = 0; i < b.length; i++) {  
            intValue += (b[i] & 0xFF) << (8 * (3 - i));  
            // System.out.print(Integer.toBinaryString(intValue)+" ");  
        }  
        return intValue;  
    }  
}
