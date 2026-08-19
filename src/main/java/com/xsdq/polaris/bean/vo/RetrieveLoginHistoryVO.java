package com.xsdq.polaris.bean.vo;

import com.xsdq.polaris.bean.entity.RetrieveLoginHistoryParameter;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.validation.annotation.Validated;

/**
 *
 * @author XiaoYu
 * @since 2026/7/2 17:53
 */
@Data
@Validated
@EqualsAndHashCode(callSuper = true)
public class RetrieveLoginHistoryVO extends BasePagingVO {

	@NotNull
	@Deprecated
	private Long tenantId;
	private String account;
	private String ip;

	public RetrieveLoginHistoryParameter parameter() {
		return new RetrieveLoginHistoryParameter(tenantId, account, ip, getPageNo(), getPageSize());
	}
}
