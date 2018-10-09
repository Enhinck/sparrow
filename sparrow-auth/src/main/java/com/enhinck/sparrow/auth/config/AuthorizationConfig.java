package com.enhinck.sparrow.auth.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.enhinck.sparrow.auth.token.AuthJwtAccessTokenConverter;
import com.enhinck.sparrow.auth.token.IocRedisTokenStore;
import com.enhinck.sparrow.common.constant.SecurityConstants;

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
	private UserDetailsService userDetailsService;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		// token增强配置
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

		endpoints.tokenStore(redisTokenStore()).tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager).reuseRefreshTokens(false)
				.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients().tokenKeyAccess("isAuthenticated()")
				.checkTokenAccess("permitAll()");
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
		IocRedisTokenStore tokenStore = new IocRedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityConstants.IOC_PREFIX);
		return tokenStore;
	}

	/**
	 * jwt 生成token 定制化处理
	 *
	 * @return TokenEnhancer
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			final Map<String, Object> additionalInfo = new HashMap<>(1);
			additionalInfo.put("license", SecurityConstants.IOC_LICENSE);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}

}
