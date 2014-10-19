package com.fire.interceptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * ================================================================== DataConverter.java created by yydx811 at 2014年2月28日 下午11:31:59 这里对类或者接口作简要描述
 * 
 * @author yydx811
 * @version 1.0
 * @mail yydx811@jtang.cn,yydx811@163.com ==================================================================
 */

public class DataConverter implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		TimeZone tz = TimeZone.getTimeZone("GMT+08:00");//获取中国北京时区
		TimeZone.setDefault(tz);//设置中国北京时区为默认时区
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf1, true));
	}
	
	public static void main(String[] args) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.print(sdf1.parse("1900-01-01 08:01:00"));
	}

}
