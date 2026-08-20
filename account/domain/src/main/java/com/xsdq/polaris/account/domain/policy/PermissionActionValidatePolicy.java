package com.xsdq.polaris.account.domain.policy;

import com.xsdq.polaris.account.domain.model.role.PermissionCode;

/**
 * @author XiaoYu
 * @since 2026/8/17 19:06
 */
public interface PermissionActionValidatePolicy {

  void validate(PermissionCode action) throws IllegalArgumentException;
}
