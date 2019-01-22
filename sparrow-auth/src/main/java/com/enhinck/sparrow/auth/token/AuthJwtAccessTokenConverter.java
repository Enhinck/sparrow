package com.enhinck.sparrow.auth.token;

import java.util.LinkedHashMap;
import java.util.Map;

import com.enhinck.sparrow.auth.integration.authenticator.SysUserClient;
import com.enhinck.sparrow.auth.service.SysUserAuthentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.enhinck.sparrow.common.constant.SecurityConstants;

/**
 * jwt token 转换器
 */
public class AuthJwtAccessTokenConverter extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		info.put("license", SecurityConstants.IOC_LICENSE);
		SysUserAuthentication unionUser = (SysUserAuthentication) authentication.getPrincipal();
		info.put("user_id", unionUser.getId());
		info.put("errorCode", 0);
		result.setAdditionalInformation(info);
		return super.enhance(result, authentication);
	}
}
