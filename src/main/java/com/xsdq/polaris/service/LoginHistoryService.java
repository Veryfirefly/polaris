package com.xsdq.polaris.service;

import java.util.List;

import com.xsdq.polaris.bean.entity.RetrieveLoginHistoryParameter;
import com.xsdq.polaris.bean.po.LoginHistoryPO;

/**
 *
 * @author XiaoYu
 * @since 2026/6/26 17:19
 */
public interface LoginHistoryService {

	List<LoginHistoryPO> retrieve(RetrieveLoginHistoryParameter retrieveParameter);
}
