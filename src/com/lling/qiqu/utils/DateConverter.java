package com.lling.qiqu.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 用于将表单的日期字符串和日期类型绑定
 * @author Administrator
 *
 */
public class DateConverter implements WebBindingInitializer {

	@InitBinder 
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setLenient(true);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
}
