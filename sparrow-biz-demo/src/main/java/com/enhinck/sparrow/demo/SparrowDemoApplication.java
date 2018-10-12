package com.enhinck.sparrow.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
public class SparrowDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowDemoApplication.class, args);
	}
}
