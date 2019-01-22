package com.enhinck.sparrow.gateway.config;

import com.enhinck.sparrow.common.constant.SecurityConstants;
import com.enhinck.sparrow.common.oauth.SparrowRedisTokenStore;
import com.enhinck.sparrow.gateway.handler.SecurityAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

import com.enhinck.sparrow.gateway.handler.GatewayOauthTwoAccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 
 * @author hueb
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Autowired
    private GatewayOauthTwoAccessDeniedHandler gateWayOAuth2AccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        filterIgnorePropertiesConfig.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest()
                .access("@permissionService.hasPermission(request,authentication)");
    }
    @Autowired
    SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.expressionHandler(expressionHandler);
        resources.accessDeniedHandler(gateWayOAuth2AccessDeniedHandler);
        resources.authenticationEntryPoint(securityAuthenticationEntryPoint);
    }

    /**
     * 配置解决 spring-security-oauth问题
     * https://github.com/spring-projects/spring-security-oauth/issues/730
     *
     * @param applicationContext ApplicationContext
     * @return OAuth2WebSecurityExpressionHandler
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    /**
     * 加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new NoneEncryptPasswordEncoder();
    }

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
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