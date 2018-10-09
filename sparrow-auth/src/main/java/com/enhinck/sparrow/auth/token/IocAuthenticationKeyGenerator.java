package com.enhinck.sparrow.auth.token;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

/**
 * Basic key generator taking into account the client id, scope, resource ids and username (principal name) if they
 * exist.
 * 修改key生成机制  适应一个用户多处登录
 * @author Dave Syer  hueb
 *
 */
public class IocAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

	private static final String CLIENT_ID = "client_id";

	private static final String SCOPE = "scope";

	private static final String USERNAME = "username";

	private static final String SESSION_ID = "random_num";

	
	public String extractKey(OAuth2Authentication authentication) {
		Map<String, String> values = new LinkedHashMap<String, String>();
		OAuth2Request authorizationRequest = authentication.getOAuth2Request();
		
		Map<String, String>  params = authorizationRequest.getRequestParameters();
		
		String random = params.get(SESSION_ID);
		
		if(StringUtils.isNotBlank(random)) {
			values.put(SESSION_ID, random);
		}
		
		if (!authentication.isClientOnly()) {
			values.put(USERNAME, authentication.getName());
		}
		
		values.put(CLIENT_ID, authorizationRequest.getClientId());
		if (authorizationRequest.getScope() != null) {
			values.put(SCOPE, OAuth2Utils.formatParameterList(new TreeSet<String>(authorizationRequest.getScope())));
		}
		return generateKey(values);
	}

	protected String generateKey(Map<String, String> values) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		} catch (NoSuchAlgorithmException nsae) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
		}
	}
}
