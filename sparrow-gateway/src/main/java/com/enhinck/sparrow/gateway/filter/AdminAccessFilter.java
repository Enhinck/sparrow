package com.enhinck.sparrow.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.enhinck.sparrow.common.constant.SecurityConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author lvziqiang
 *
 */
@Component
@Slf4j
public class AdminAccessFilter extends ZuulFilter {

	/*@Autowired
	@Lazy
	private UserService userService;
*/
	@Value("${gate.ignore.startWith}")
	private String startWith;

	@Value("${zuul.prefix}")
	private String zuulPrefix;

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@SuppressWarnings("unused")
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
		final String method = request.getMethod();

		ctx.set("startTime", System.currentTimeMillis());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			RequestContext requestContext = RequestContext.getCurrentContext();
			requestContext.addZuulRequestHeader(SecurityConstants.USER_HEADER, authentication.getName());
			requestContext.addZuulRequestHeader(SecurityConstants.ROLE_HEADER,
					CollectionUtil.join(authentication.getAuthorities(), ","));

		}
		return null;

	}

	public static final String SPLIT_CHAR = ",";

	/**
	 * URI是否以什么打头
	 *
	 * @param requestUri
	 * @return
	 */
	protected boolean isStartWith(String requestUri) {
		boolean flag = false;
		for (String s : startWith.split(SPLIT_CHAR)) {
			if (pathMatcher.match(s, requestUri)) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 网关抛异常
	 *
	 * @param body
	 * @param code
	 */
	protected void setFailedRequest(String body, int code) {
		if (log.isDebugEnabled()) {
			log.debug("Reporting error ({}): {}", code, body);
		}
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.setResponseStatusCode(code);
		if (ctx.getResponseBody() == null) {
			ctx.setResponseBody(body);
			ctx.setSendZuulResponse(false);
		}
	}

}
