package com.enhinck.sparrow.gateway.fallback;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * 
 * @author hueb
 *
 */
@Component
public class ProviderFallback implements FallbackProvider {

	private Logger logger = LoggerFactory.getLogger(ProviderFallback.class);

	@Override
	public String getRoute() {
		return "*";
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return response(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ClientHttpResponse response(final HttpStatus status) {
		return new ClientHttpResponse() {
			@Override
			public HttpStatus getStatusCode() {
				return status;
			}

			@Override
			public int getRawStatusCode() {
				return status.value();
			}

			@Override
			public String getStatusText() {
				return status.getReasonPhrase();
			}

			@Override
			public void close() {
				logger.info("close");
			}

			@Override
			public InputStream getBody() {
				String message = "{\n" + "\"code\": " + HttpStatus.BAD_GATEWAY + ",\n" + "\"message\": \"微服务故障, 请稍后再试\"\n" + "}";
				return new ByteArrayInputStream(message.getBytes());
			}

			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}
		};
	}

	@Override
	public ClientHttpResponse fallbackResponse(Throwable cause) {
		// TODO Auto-generated method stub
		return null;
	}
}
