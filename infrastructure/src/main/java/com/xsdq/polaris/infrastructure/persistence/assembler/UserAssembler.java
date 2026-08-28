package com.xsdq.polaris.infrastructure.persistence.assembler;

import java.util.Collection;
import java.util.stream.Collectors;

import com.xsdq.polaris.domain.model.role.Role;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.user.Account;
import com.xsdq.polaris.domain.model.user.Email;
import com.xsdq.polaris.domain.model.user.Password;
import com.xsdq.polaris.domain.model.user.User;
import com.xsdq.polaris.domain.model.user.UserId;
import com.xsdq.polaris.domain.model.user.UserStatus;
import com.xsdq.polaris.infrastructure.persistence.UserPO;

/**
 *
 * @author XiaoYu
 * @since 2026/8/28 17:49
 */
public final class UserAssembler {

	public static User toDomain(UserPO userPO, Collection<Role> roles) {
		return User.reconstitute(
				UserId.of(userPO.getId()),
				TenantId.of(userPO.getTenantId()),
				userPO.getNickname(),
				Account.of(userPO.getAccount()),
				Password.of(userPO.getPassword()),
				Email.of(userPO.getEmail()),
				userPO.getPhone(),
				userPO.getAddress(),
				userPO.getAvatarPath(),
				UserStatus.of(userPO.getStatus()),
				roles.stream().map(Role::getRoleId).collect(Collectors.toSet()),
				userPO.getCreateTime(),
				userPO.getUpdateTime());
	}
}
