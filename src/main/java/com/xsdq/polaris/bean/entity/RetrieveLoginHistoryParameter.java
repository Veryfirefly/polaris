package com.xsdq.polaris.bean.entity;

public record RetrieveLoginHistoryParameter(Long tenantId,
											String account,
											String ipAddr,
											int limit,
											int offset) {
}
