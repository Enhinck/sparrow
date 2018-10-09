package com.enhinck.sparrow.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * @author hueb
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 忽略css.jq.img等文件
		web.ignoring().antMatchers("/**.html", "/**.css", "/img/**", "/**.js", "/third-party/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// HTTP with Disable CSRF
		http.csrf().disable() 
		// Authorize Request Configuration
				.authorizeRequests() 
				 // 放开"/api/**"：为了给被监控端免登录注册并解决Log与Logger冲突
				.antMatchers("/login", "/api/**", "/**/heapdump", "/**/loggers", "/**/liquibase", "/**/logfile",
						"/**/flyway", "/**/auditevents", "/**/jolokia",
						"/health/**",
						"/env/**",
						"/metrics/**",
						"/trace/**",
						"/dump/**",
						"/jolokia/**",
						"/info/**",
						"/logfile/**",
						"/refresh/**",
						"/flyway/**",
						"/liquibase/**",
						"/heapdump/**",
						"/loggers/**",
						"/auditevents/**",
						"/autoconfig",
						"/beans",
						"/shutdown",
						"/mappings"
				)

				.permitAll()
				.and().authorizeRequests().antMatchers("/**").hasRole("USER").antMatchers("/**").authenticated().and()
				.formLogin()
				.loginPage("/login.html")
				.loginProcessingUrl("/login").permitAll()
				.defaultSuccessUrl("/")
				.and() 
				.logout()
				.deleteCookies("remove")
				.logoutSuccessUrl("/login.html").permitAll()
				.and()
				.httpBasic();

	}

}
