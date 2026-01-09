package com.xsdq.polaris.repository.dao;

import com.xsdq.polaris.repository.Status;
import com.xsdq.polaris.repository.po.UserPO;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author XiaoYu
 * @since 2026/1/5 16:04
 */
public interface UserMockDataTestKit {

	PasswordEncoder passwordEncoder();

	default UserPO createMockUser(long tenantId) {
		var user = new UserPO();
		user.setAccount("xiaoyu");
		user.setPassword(passwordEncoder().encode("123456"));
		user.setTenantId(tenantId);
		user.setNickname("测试昵称");
		user.setStatus(Status.ENABLED);
		user.setEmail("xiaoyu@polaris.com");
		user.setPhone("13112345678");
		user.setCreateBy(0L);
		user.setUpdateBy(0L);
		return user;
	}
}
