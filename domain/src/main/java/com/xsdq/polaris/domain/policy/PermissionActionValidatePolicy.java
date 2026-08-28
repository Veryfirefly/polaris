package com.xsdq.polaris.domain.policy;


import com.xsdq.polaris.domain.model.role.PermissionCode;

/**
 * @author XiaoYu
 * @since 2026/8/17 19:06
 */
public interface PermissionActionValidatePolicy {

  void validate(PermissionCode action) throws IllegalArgumentException;
}
