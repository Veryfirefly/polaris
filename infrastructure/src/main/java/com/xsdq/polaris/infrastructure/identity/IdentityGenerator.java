package com.xsdq.polaris.infrastructure.identity;

/**
 * @author XiaoYu
 * @since 2026/8/19 17:36
 */
public interface IdentityGenerator<T> {

  Identity<T> generate() throws IdentityGenerateException;
}
