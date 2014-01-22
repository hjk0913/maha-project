package com.maha.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


public class BeanManager {

	@Autowired
	private ApplicationContext ctx;
	
	public Object getBean(String beanName) {
		return ctx.getBean(beanName);
	}
}
