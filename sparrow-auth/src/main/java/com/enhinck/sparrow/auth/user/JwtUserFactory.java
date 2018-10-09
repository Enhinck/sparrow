package com.enhinck.sparrow.auth.user;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.enhinck.sparrow.common.model.RoleVO;
import com.enhinck.sparrow.common.model.UserVO;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static Long ONE_HUNDRED_YEAR_MILLIS = 3153600000000L;

	public static JwtUser create(UserVO user) {
    	Date lastPasswordResetDate = new Date(System.currentTimeMillis() - ONE_HUNDRED_YEAR_MILLIS);
        return new JwtUser(
                user.getId(),
                user.getUsername(),
               "",// user.getFirstname(),
               "",//user.getLastname(),
               "",//user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoleVOS()),
                true,//user.getEnabled(),
                lastPasswordResetDate//user.getLastPasswordResetDate()
        );
    }

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleVO> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getCode()))
				.collect(Collectors.toList());
	}
}
