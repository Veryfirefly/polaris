package com.xsdq.polaris.infrastructure.identity;

/**
 * @author XiaoYu
 * @since 2026/8/20 14:41
 */
public interface VariableLengthIdentityGenerator<T> extends IdentityGenerator<T> {

  Identity<T> generate(int length) throws IdentityGenerateException;
}
