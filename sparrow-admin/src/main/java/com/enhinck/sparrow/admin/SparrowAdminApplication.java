package com.enhinck.sparrow.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.codecentric.boot.admin.config.EnableAdminServer;




@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class SparrowAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowAdminApplication.class, args);
	}
	
}
