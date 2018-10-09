package com.enhinck.sparrow.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enhinck.sparrow.auth.user.JwtUserFactory;
import com.enhinck.sparrow.common.model.RoleVO;
import com.enhinck.sparrow.common.model.UserVO;

@Service("userDetailService")
public class JwtUserDetailsService implements UserDetailsService {

	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = new UserVO();
		user.setUsername("admin");
		user.setPassword("123456");
		user.setGmtModify(new Date(System.currentTimeMillis() - 1000));
		List<RoleVO> roleVOS = new ArrayList<>();
		RoleVO roleVO = new RoleVO();
		roleVO.setCode("ROLE_ADMIN");
		roleVOS.add(roleVO);
		user.setRoleVOS(roleVOS);
		if (user != null) {
			return JwtUserFactory.create(user);
		} else {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
	}
}
