package com.enhinck.sparrow.demo;


import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@ServletComponentScan
@ImportResource({"classpath*:applicationContext.xml"})
@Slf4j
public class SparrowProxyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowProxyDemoApplication.class, args);
	}
	
	@Value("${proxy.solr.target_url}")
	private String targetUri;

	@Value("${proxy.solr.servlet_url}")
	private String servletUrl;


	@Bean
	public ServletRegistrationBean servletRegistrationBean1() {
		log.info("...初始化...");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), servletUrl);
		servletRegistrationBean.addInitParameter("targetUri", targetUri);
		servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "false");
		log.info("...初始化完成...");
		return servletRegistrationBean;
	}
}
