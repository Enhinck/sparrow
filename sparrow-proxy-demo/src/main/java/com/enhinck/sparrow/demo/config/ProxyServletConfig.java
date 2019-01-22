package com.enhinck.sparrow.demo.config;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Configuration
public class ProxyServletConfig {
	@Value("${proxy.solr.target_url}")
	private String targetUri;

	@Value("${proxy.solr.servlet_url}")
	private String servletUrl;

	public ProxyServletConfig() {
		// TODO Auto-generated constructor stub
		log.info("。。。。。。初始化。。。。。。。。。。");
	}

	@Bean
	public ServletRegistrationBean proxyServletRegistration() {
		log.info("。。。。。。初始化。。。。。。。。。。");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), servletUrl);
		servletRegistrationBean.addInitParameter("targetUri", targetUri);
		servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "false");
		log.info("。。。。。。初始化完成。。。。。。。。。。");
		return servletRegistrationBean;
	}

}
