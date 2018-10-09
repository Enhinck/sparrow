package com.enhinck.sparrow.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.enhinck.sparrow.common.redis.FastJsonRedisSerializer;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients({ "com.greentown.gateway.feign" })
@EnableZuulProxy
@ImportResource({"classpath*:applicationContext.xml"})
@EnableScheduling
public class SparrowGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowGatewayApplication.class, args);
	}
	
	
	/**
	 * 将zuul的url配置动态化
	 * 
	 * @return
	 */
	@RefreshScope
	@ConfigurationProperties("zuul")
	public ZuulProperties zuulProperties() {
		return new ZuulProperties();
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		RedisTemplate<?, ?> template = new RedisTemplate<>();
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		template.setEnableTransactionSupport(false);
		template.setEnableDefaultSerializer(false);
		template.setConnectionFactory(jedisConnectionFactory);
		template.setKeySerializer(stringSerializer);
		template.setValueSerializer(new FastJsonRedisSerializer<String>());
		template.setHashKeySerializer(stringSerializer);
		template.setHashValueSerializer(new FastJsonRedisSerializer<String>());
		return template;

	}
}
