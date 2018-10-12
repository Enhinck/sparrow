package com.enhinck.sparrow.gateway.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.enhinck.sparrow.gateway.service.PermissionService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author hueb
 *
 */
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	/*
	 * @Autowired private MenuCache menuCache;
	 * 
	 * @Autowired private RoleCache roleCache;
	 */

	// private AntPathMatcher antPathMatcher = new AntPathMatcher();

	// @SuppressWarnings("unchecked")
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		// ele-admin options 跨域配置，现在处理是通过前端配置代理，不使用这种方式，存在风险
		if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		log.debug("hasPermission");
		/*
		 * Object principal = authentication.getPrincipal(); List<GrantedAuthority>
		 * grantedAuthorityList = (List<GrantedAuthority>)
		 * authentication.getAuthorities(); boolean hasPermission = false; if (principal
		 * != null) { if (CollectionUtil.isEmpty(grantedAuthorityList)) { if
		 * (log.isDebugEnabled()) { log.debug("角色列表为空：{}",
		 * authentication.getPrincipal()); } return hasPermission; }
		 * 
		 * Set<Long> permissionIds = new HashSet<>(); for (GrantedAuthority
		 * grantedAuthority : grantedAuthorityList) { String roleCode =
		 * grantedAuthority.getAuthority(); RoleVO roleVO = roleCache.get(roleCode); if
		 * (roleVO != null && CollectionUtils.isNotEmpty(roleVO.getPermissionVOS())) {
		 * List<PermissionVO> permissionVOs = roleVO.getPermissionVOS(); for
		 * (PermissionVO permissionVO : permissionVOs) {
		 * permissionIds.add(permissionVO.getId()); } } }
		 * 
		 * // 有权限的用户 获取默认资源 if (CollectionUtils.isNotEmpty(permissionIds)) { RoleVO
		 * roleVO = roleCache.get("ROLE_USER"); if (roleVO != null &&
		 * CollectionUtils.isNotEmpty(roleVO.getPermissionVOS())) { List<PermissionVO>
		 * permissionVOs = roleVO.getPermissionVOS(); for (PermissionVO permissionVO :
		 * permissionVOs) { permissionIds.add(permissionVO.getId()); } } }
		 * 
		 * Set<MenuVO> menuSet = menuCache.getAllSet(); // 权限过滤 Set<MenuVO> urls =
		 * SysUtil.filter(menuSet, permissionIds);
		 * 
		 * for (MenuVO menu : urls) { if (StringUtils.isNotEmpty(menu.getUrl()) &&
		 * antPathMatcher.match(menu.getUrl(), request.getRequestURI())) { if
		 * (StringUtils.isEmpty(menu.getMethod()) ||
		 * request.getMethod().equalsIgnoreCase(menu.getMethod())) { hasPermission =
		 * true; break; } } } }
		 */
		return true;
	}
}
