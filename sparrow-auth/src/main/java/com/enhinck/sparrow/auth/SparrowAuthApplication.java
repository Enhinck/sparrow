package com.enhinck.sparrow.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
public class SparrowAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowAuthApplication.class, args);
	}
}
