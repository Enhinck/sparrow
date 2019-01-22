package com.enhinck.sparrow.auth.config;

import java.util.Arrays;

import javax.sql.DataSource;

import com.enhinck.sparrow.auth.integration.IntegrationAuthenticationFilter;
import com.enhinck.sparrow.auth.service.IntegrationUserDetailsService;
import com.enhinck.sparrow.auth.token.SparrowTokenServices;
import com.enhinck.sparrow.common.oauth.SparrowRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.enhinck.sparrow.auth.token.AuthJwtAccessTokenConverter;
import com.enhinck.sparrow.common.constant.SecurityConstants;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

/**
 *  认证服务器逻辑实现
 */

@Configuration
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IntegrationUserDetailsService integrationUserDetailsService;
	@Autowired
	private IntegrationAuthenticationFilter integrationAuthenticationFilter;

	@Autowired
	private WebResponseExceptionTranslator webResponseExceptionTranslator;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService());
	}

	public ClientDetailsService clientDetailsService() {
		JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		return clientDetailsService;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.exceptionTranslator(webResponseExceptionTranslator)
				.authenticationManager(authenticationManager)
				.reuseRefreshTokens(false)
				.userDetailsService(integrationUserDetailsService)
				.tokenServices(tokenServices());
	}
	/**
	 * 访问token有效期7天
	 */
	public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7;
	/**
	 * 刷新token有效期30天
	 */
	public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

	@Primary
	@Bean
	public AuthorizationServerTokenServices tokenServices() {
		SparrowTokenServices defaultTokenServices = new SparrowTokenServices();
		defaultTokenServices.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
		defaultTokenServices.setRefreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setReuseRefreshToken(false);
		defaultTokenServices.setTokenStore(redisTokenStore());
		defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
		defaultTokenServices.setAuthenticationManager(authenticationManager);

		if (integrationUserDetailsService != null) {
			PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
			provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(
					integrationUserDetailsService));
			defaultTokenServices
					.setAuthenticationManager(new ProviderManager(Arrays.<AuthenticationProvider>asList(provider)));
		}

		defaultTokenServices.setClientDetailsService(clientDetailsService());
		return defaultTokenServices;
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security//.allowFormAuthenticationForClients()
				.tokenKeyAccess("isAuthenticated()")
				.checkTokenAccess("permitAll()")
				.addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new NoneEncryptPasswordEncoder();
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		AuthJwtAccessTokenConverter jwtAccessTokenConverter = new AuthJwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(SecurityConstants.SIGN_KEY);
		return jwtAccessTokenConverter;
	}

	/**
	 * tokenstore 定制化处理
	 *
	 *  如果使用的 redis-cluster 模式请使用 IocRedisClusterTokenStore
	 * IocRedisClusterTokenStore tokenStore = new IocRedisClusterTokenStore();
	 * tokenStore.setRedisTemplate(redisTemplate);
	 */
	@Bean
	public TokenStore redisTokenStore() {
		SparrowRedisTokenStore tokenStore = new SparrowRedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityConstants.SPARROW_PREFIX);
		return tokenStore;
	}


}
