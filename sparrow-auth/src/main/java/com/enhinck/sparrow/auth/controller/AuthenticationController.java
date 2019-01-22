package com.enhinck.sparrow.auth.controller;

import com.enhinck.sparrow.auth.integration.authenticator.Result;
import com.enhinck.sparrow.auth.integration.authenticator.VerificationCodeClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enhinck.sparrow.common.constant.SecurityConstants;
import com.enhinck.sparrow.common.response.AppResponseEntity;

import java.security.Principal;

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

	@Autowired
	private VerificationCodeClient verificationCodeClient;

	@GetMapping("/current")
	@ApiOperation("获取当前用户信息")
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PostMapping("/sms/token")
	@ApiOperation("获取短信登录Token")
	public Result<String> getToken(String phoneNumber){
		return verificationCodeClient.getToken(6,null,"0",phoneNumber,true);
	}

}