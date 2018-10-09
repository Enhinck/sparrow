package com.enhinck.sparrow.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 
 * @author hueb
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
	}

	

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("sparrow网关API").description("sparrow网关接口文档说明")
				.termsOfServiceUrl("http://localhost:8762").contact(new Contact("sparrow", "", "")).version("1.0").build();
	}

	/*@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(null, "list", "alpha", "schema", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
				false, true, 60000L);
	}*/
}