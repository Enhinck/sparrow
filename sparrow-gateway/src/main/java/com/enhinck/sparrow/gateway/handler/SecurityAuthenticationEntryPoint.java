package com.enhinck.sparrow.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * token超期或无效token 异常响应
 *
 * @author hueb
 */
@Slf4j
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    public static final String APP_PATH = "/api/bbb/**";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>(3);
        map.put("sourceErrorMessage", authException.getMessage());
        Throwable cause = authException.getCause();
        if (cause instanceof InvalidTokenException) {
            map.put("errorCode", 100017);
            map.put("errorMessage", "access_token失效");
        } else {
            // 未授权的请求
            map.put("errorMessage", "未经授权的请求");
            map.put("errorCode", 999973);
        }
        response.setContentType("application/json");

        if (antPathMatcher.match(APP_PATH, request.getRequestURI())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
