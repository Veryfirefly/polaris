package com.xsdq.polaris.tenant;

/**
 *
 * @author XiaoYu
 * @since 2026/7/3 16:05
 */
public final class TenantContext {

	private static final ThreadLocal<TenantId> threadLocal = new ThreadLocal<>();

	private TenantContext() {
		throw new IllegalStateException();
	}

	public static TenantId currentTenantId() {
		return threadLocal.get();
	}

	public static void clear() {
		threadLocal.remove();
	}

	public static void setTenantId(TenantId tenant) {
		threadLocal.set(tenant);
	}

	public static boolean hasTenantId() {
		return threadLocal.get() != null;
	}
}
