package com.xsdq.polaris.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xsdq.polaris.bean.LoginDevice;
import com.xsdq.polaris.bean.entity.RetrieveLoginHistoryParameter;
import com.xsdq.polaris.bean.event.LoginHistoryEvent;
import com.xsdq.polaris.bean.po.LoginHistoryPO;
import com.xsdq.polaris.http.whois.WhoisInfo;
import com.xsdq.polaris.http.whois.WhoisInfoQuery;
import com.xsdq.polaris.repository.dao.LoginHistoryDAO;
import com.xsdq.polaris.repository.po.UserPO;
import com.xsdq.polaris.security.PolarisUserDetails;
import com.xsdq.polaris.service.LoginHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author XiaoYu
 * @since 2026/6/26 17:22
 */
@Service
public class LoginHistoryServiceImpl extends ServiceImpl<LoginHistoryDAO, LoginHistoryPO> implements LoginHistoryService {

	private static final Logger log = LoggerFactory.getLogger(LoginHistoryServiceImpl.class);

	private final WhoisInfoQuery whoisInfoQuery;

	public LoginHistoryServiceImpl(WhoisInfoQuery whoisInfoQuery) {
		this.whoisInfoQuery = whoisInfoQuery;
	}

	/**
	 * <p>处理用户登录历史日志, 事件将会在三处触发:</p>
	 * <ul>
	 *     <li>登录成功后触发</li>
	 *     <li>续签令牌后触发</li>
	 *     <li>登出后触发</li>
	 * </ul>
	 *
	 * @param event 用户登录日志
	 */
	@Async
	@EventListener(LoginHistoryEvent.class)
	public void handleLoginHistoryEvent(LoginHistoryEvent event) {
		log.info("[{}] Executing LoginHistoryEvent", Thread.currentThread().getName());
		PolarisUserDetails userDetails = event.getUserDetails();

		LoginHistoryPO entity = new LoginHistoryPO();
		entity.setTenantId(userDetails.tenantId());

		UserPO user = userDetails.getUser();
		entity.setUserId(user.getId());
		entity.setAccount(user.getAccount());

		LoginDevice device = userDetails.getDevice();
		entity.setOs(device.os());
		entity.setOsVersion(device.osVersion());
		entity.setPlatform(device.platform());
		entity.setBrowser(device.browser());
		entity.setBrowserVersion(device.browserVersion());
		entity.setEngine(device.engine());
		entity.setEngineVersion(device.engineVersion());
		entity.setMobile(device.isMobile());

		// 获取当前操作请求的ip地址.
		String ipAddr = event.getIpAddr();
		entity.setIpAddr(ipAddr);

		WhoisInfo whoisInfo = whoisInfoQuery.getWhoisInfo(ipAddr);
		entity.setCountry(whoisInfo.country());
		entity.setProvince(whoisInfo.province());
		entity.setCity(whoisInfo.city());
		entity.setIsp(whoisInfo.isp());

		entity.setLoginStatus(event.getLoginStatus());

		int numResult = getBaseMapper().insert(entity);
		if (numResult != 1) {
			log.warn("Failed to save user login information to the 'user_login_history' table, " +
					"the db update result: {}, uid: {}, ip: {}", numResult, user.getId(), userDetails.getIpAddress());
		}
	}

	@Override
	public List<LoginHistoryPO> retrieve(RetrieveLoginHistoryParameter retrieveLoginHistoryParameter) {
		// todo
		return List.of();
	}
}
