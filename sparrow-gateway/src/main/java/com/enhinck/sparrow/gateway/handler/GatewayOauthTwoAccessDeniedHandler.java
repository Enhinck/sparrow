package com.enhinck.sparrow.gateway.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.enhinck.sparrow.common.constant.CommonConstant;
import com.enhinck.sparrow.common.response.AppResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


/**
 * 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 * @author hueb
 *
 */
@Slf4j
@Component
public class GatewayOauthTwoAccessDeniedHandler extends OAuth2AccessDeniedHandler {
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 授权拒绝处理
	 *
	 * @param request       request
	 * @param response      response
	 * @param authException authException
	 * @throws IOException      IOException
	 * @throws ServletException ServletException
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException)
			throws IOException, ServletException {
		if(log.isDebugEnabled()) {
			log.debug("授权失败，禁止访问 {}", request.getRequestURI());
		}
		response.setCharacterEncoding(CommonConstant.UTF8);
		response.setContentType(CommonConstant.CONTENT_TYPE);
		AppResponseEntity appResponseEntity = new AppResponseEntity("授权失败，禁止访问");
		response.setStatus(HttpStatus.SC_FORBIDDEN);
		PrintWriter printWriter = response.getWriter();
		printWriter.append(objectMapper.writeValueAsString(appResponseEntity));
	}
}
