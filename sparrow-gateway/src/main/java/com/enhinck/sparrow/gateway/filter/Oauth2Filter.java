package com.enhinck.sparrow.gateway.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.enhinck.sparrow.common.constant.SecurityConstants;
import com.enhinck.sparrow.common.response.AppResponseEntity;
import com.enhinck.sparrow.gateway.config.FilterIgnorePropertiesConfig;
import com.enhinck.sparrow.gateway.util.AuthUtils;
import com.enhinck.sparrow.gateway.util.ValidateCodeException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author hueb security.validate.code.enabled 默认 为false，开启需要设置为true
 */
@Slf4j
@RefreshScope
@Configuration("Oauth2Filter")
//@ConditionalOnProperty(value = "security.validate.code", havingValue = "true")
public class Oauth2Filter extends ZuulFilter {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_ERROR_FILTER_ORDER + 1;
	}

	/**
	 * 是否校验验证码 1. 判断验证码开关是否开启 2. 判断请求是否登录请求 3. 判断终端是否支持
	 *
	 * @return true/false
	 */
	@Override
	public boolean shouldFilter() {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

		if (StrUtil.containsAnyIgnoreCase(request.getRequestURI(), SecurityConstants.OAUTH_TOKEN_URL)) {
			return true;
		}

		return false;
	}

	@Override
	public Object run() {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		RequestContext ctx = RequestContext.getCurrentContext();
		AppResponseEntity appResponseEntity = new AppResponseEntity("xxx");
		ctx.setResponseStatusCode(478);
		ctx.setSendZuulResponse(false);
		
		ctx.getResponse().setContentType("application/json;charset=UTF-8");
		ctx.setResponseBody(JSONObject.toJSONString(appResponseEntity));
		return null;
	}

}
