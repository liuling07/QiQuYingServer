/**
 * 
 */
package com.lling.qiqu.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author zhr
 * @project video
 * @create_date 2014-5-7 下午3:32:16
 */
public class InitConfig {
	static Properties prop = new Properties();
	static {
		try {
			InputStream in = InitConfig.class.getResourceAsStream("/init.properties");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		if (prop.containsKey(key)) {
			return prop.get(key).toString();
		}
		return "";
	}
}
