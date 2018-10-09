package com.enhinck.sparrow.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enhinck.sparrow.common.constant.SecurityConstants;
import com.enhinck.sparrow.common.response.AppResponseEntity;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	@Autowired
	@Qualifier("consumerTokenServices")
	private ConsumerTokenServices consumerTokenServices;

	/**
	 * 清除Redis中 accesstoken refreshtoken
	 *
	 * @param accesstoken accesstoken
	 * @return true/false
	 */
	@PostMapping("/removeToken")
	@CacheEvict(value = SecurityConstants.TOKEN_USER_DETAIL, key = "#accesstoken")
	public AppResponseEntity removeToken(String accesstoken) {
		return new AppResponseEntity(consumerTokenServices.revokeToken(accesstoken));
	}
}