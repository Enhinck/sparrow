package com.enhinck.sparrow.demo.config;

import static com.google.common.base.Predicates.or;
import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
	@Value("${spring.application.name}")
	public String applicationName;

	@Value("${config.oauth2.accessTokenUri}")
	private String accessTokenUri;

	public static final String securitySchemaOAuth2 = "oauth2schema";
	public static final String authorizationScopeGlobal = "global";
	public static final String authorizationScopeGlobalDesc = "accessEverything";

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName(applicationName).select()
				.apis(RequestHandlerSelectors.basePackage("com.enhinck.sparrow.demo.controller")).paths(postPaths()).build()
				.securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Arrays.asList(securitySchema()/*, apiKey()*/)).apiInfo(apiInfo());
	}

	/*@Bean
	public SecurityScheme apiKey() {
		return new ApiKey(HttpHeaders.AUTHORIZATION, "apiKey", "header");
	}*/
	/*
	 * @Bean public SecurityScheme apiCookieKey() { return new
	 * ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie"); }
	 */

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = newArrayList();
		authorizationScopeList.add(new AuthorizationScope("server", "server"));

		List<GrantType> grantTypes = newArrayList();
		GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
		grantTypes.add(passwordCredentialsGrant);

		return new OAuth("oauth2", authorizationScopeList, grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = new AuthorizationScope("server", "server");

		return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
	}

	@Bean
	@Order(0)
	public SecurityConfiguration securityConfiguration() {
		String clientId = "gateway";
		String clientSecret = "gateway";
		String realm = "";
		String appName = "";
		String apiKey = "Bearer access token";
		ApiKeyVehicle apiKeyVehicle = ApiKeyVehicle.HEADER;
		String apiKeyName = HttpHeaders.AUTHORIZATION;
		String scopeSeparator = "";
		return new SecurityConfiguration(clientId, clientSecret, realm, appName, apiKey, apiKeyVehicle, apiKeyName,
				scopeSeparator);
	}

	private Predicate<String> postPaths() {
		return or(regex("/posts.*"), regex("/*.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Sparrow Demo API")
				.description("spring cloud  API reference for developers").termsOfServiceUrl("http://github.com")
				.contact(new Contact("huenbin", "http://github.com", "huenbin@foxmail.com"))
				.license("Enhinck License").licenseUrl("huenbin@foxmail.com").version("1.0").build();
	}

}