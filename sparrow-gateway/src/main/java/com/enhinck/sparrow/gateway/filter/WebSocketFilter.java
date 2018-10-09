package com.enhinck.sparrow.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 
 * @author hueb
 *
 */
@Component
public class WebSocketFilter extends ZuulFilter {
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	public static final String WEB_SOCKET_HEADER = "websocket";
	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		String upgradeHeader = request.getHeader("Upgrade");
		if (null == upgradeHeader) {
			upgradeHeader = request.getHeader("upgrade");
		}
		if (null != upgradeHeader && WEB_SOCKET_HEADER.equalsIgnoreCase(upgradeHeader)) {
			context.addZuulRequestHeader("connection", "Upgrade");
		}
		return null;
	}
}
