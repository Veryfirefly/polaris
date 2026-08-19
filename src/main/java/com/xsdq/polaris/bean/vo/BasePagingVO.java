package com.xsdq.polaris.bean.vo;

import lombok.Data;

/**
 *
 * @author XiaoYu
 * @since 2026/7/2 17:54
 */
@Data
public abstract class BasePagingVO {

	private int pageNo;
	private int pageSize;
}
