package com.enhinck.sparrow.gateway.config;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * stomwebsocket
 * 
 * @author hueb
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 订阅Broker名称
		config.enableSimpleBroker("/topic", "/project");
		// 全局使用的订阅前缀（客户端订阅路径上会体现出来）
		config.setApplicationDestinationPrefixes("/app");
		// 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
		config.setUserDestinationPrefix("/project/");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/webServer").setAllowedOrigins("*").withSockJS();
	}

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages.anyMessage().permitAll();
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}

	@Override
	protected void customizeClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptorAdapter() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					String jwtToken = accessor.getFirstNativeHeader("Authorization");
					if (StringUtils.isNotEmpty(jwtToken)) {
						Map<String, Object> sessionAttributes = SimpMessageHeaderAccessor
								.getSessionAttributes(message.getHeaders());
						sessionAttributes.put(CsrfToken.class.getName(),
								new DefaultCsrfToken("Authorization", "Authorization", jwtToken));
					}
				}
				return message;
			}
		});
	}

}
