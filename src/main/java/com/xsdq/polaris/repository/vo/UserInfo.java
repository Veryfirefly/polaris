package com.xsdq.polaris.repository.vo;

import java.util.ArrayList;
import java.util.List;

import com.xsdq.polaris.security.PermissionGrantedAuthority;
import com.xsdq.polaris.security.PolarisUserDetails;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author XiaoYu
 * @since 2026/5/21 16:47
 */
public record UserInfo(Long tenantId,
					   Long uid,
					   String account,
					   String nickname,
					   String avatar,
					   List<PermissionExposer> permissions) {

	public static UserInfo create(PolarisUserDetails userDetails) {
		List<PermissionExposer> permissions = new ArrayList<>(userDetails.getAuthorities().size());
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if (authority instanceof PermissionGrantedAuthority pga) {
				permissions.add(new PermissionExposer(pga.getName(), pga.getAuthority()));
			}
		}

		return new UserInfo(
				userDetails.tenantId(),
				userDetails.getUser().getId(),
				userDetails.getUser().getAccount(),
				userDetails.getUser().getNickname(),
				userDetails.getUser().getAvatarPath(),
				permissions
		);
	}
}
