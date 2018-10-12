package com.enhinck.sparrow.gateway.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ApplicationContextProvider implements ApplicationContextAware {

	/**
	 * 上下文对象实例
	 */
	private ApplicationContext applicationContext;

	/**
	 * 获取上下文
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

	/**
	 * 通过class 获取bean
	 */
	public <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 通过name获取bean
	 */
	public Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 通过bean和class返回指定的Bean
	 */
	public <T> T getBean(Class<T> clazz, String name) {
		return getApplicationContext().getBean(clazz, name);
	}
}
