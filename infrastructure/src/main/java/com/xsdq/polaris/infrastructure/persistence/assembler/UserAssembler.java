package com.xsdq.polaris.infrastructure.persistence.assembler;

import com.xsdq.polaris.domain.model.role.RoleId;
import com.xsdq.polaris.domain.model.tenant.TenantId;
import com.xsdq.polaris.domain.model.user.*;
import com.xsdq.polaris.infrastructure.persistence.UserPO;

import java.util.Set;

/**
 *
 * @author XiaoYu
 * @since 2026/8/28 17:49
 */
public final class UserAssembler {

	public static User toDomain(TenantId tenantId, UserPO userPO, Set<RoleId> roles) {
		return User.reconstitute(
				UserId.of(userPO.getId()),
                tenantId,
				userPO.getNickname(),
				Account.of(userPO.getAccount()),
				Password.of(userPO.getPassword()),
				Email.of(userPO.getEmail()),
				userPO.getPhone(),
				userPO.getAddress(),
				userPO.getAvatarPath(),
				UserStatus.of(userPO.getStatus()),
                roles,
				userPO.getCreateTime(),
				userPO.getUpdateTime());
	}
}
