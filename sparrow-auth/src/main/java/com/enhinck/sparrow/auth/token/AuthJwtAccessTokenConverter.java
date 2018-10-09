package com.enhinck.sparrow.auth.token;

import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.enhinck.sparrow.common.constant.SecurityConstants;

/**
 * jwt token 转换器
 */
public class AuthJwtAccessTokenConverter extends JwtAccessTokenConverter {
	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> representation = (Map<String, Object>) super.convertAccessToken(token, authentication);
		representation.put("license", SecurityConstants.IOC_LICENSE);
		return representation;
	}

	@Override
	public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
		return super.extractAccessToken(value, map);
	}

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		return super.extractAuthentication(map);
	}
}
