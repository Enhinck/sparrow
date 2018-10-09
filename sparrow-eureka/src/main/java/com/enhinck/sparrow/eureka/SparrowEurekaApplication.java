package com.enhinck.sparrow.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * @author huenb
 * @date 2018年10月9日
 */
@SpringBootApplication
@EnableEurekaServer
public class SparrowEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowEurekaApplication.class, args);
	}
}
