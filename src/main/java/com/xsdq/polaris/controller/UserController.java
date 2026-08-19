package com.xsdq.polaris.controller;

import java.util.List;

import com.xsdq.polaris.bean.vo.RetrieveLoginHistoryVO;
import com.xsdq.polaris.repository.Response;
import com.xsdq.polaris.repository.vo.MenuRouter;
import com.xsdq.polaris.repository.vo.UserInfo;
import com.xsdq.polaris.security.AuthenticationUtils;
import com.xsdq.polaris.security.PolarisUserDetails;
import com.xsdq.polaris.service.LoginHistoryService;
import com.xsdq.polaris.service.MenuService;
import com.xsdq.polaris.service.UserService;
import com.xsdq.polaris.tenant.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final MenuService menuService;
	private final UserService userService;
	private final LoginHistoryService loginHistoryService;

	public UserController(MenuService menuService, UserService userService, LoginHistoryService loginHistoryService) {
		this.menuService = menuService;
		this.userService = userService;
		this.loginHistoryService = loginHistoryService;
	}

	@GetMapping("/info")
	public Response<UserInfo> info() {
		PolarisUserDetails userDetails = AuthenticationUtils.getUserDetails();
		UserInfo userInfo = UserInfo.create(userDetails);
		return Response.ok(userInfo);
	}

	@GetMapping("/menus")
	public Response<List<MenuRouter>> menus() {
		log.info("TenantId: {}", TenantContext.currentTenantId().id());
		PolarisUserDetails userDetails = AuthenticationUtils.getUserDetails();
		List<Long> roles = userDetails.getRoles();
		List<MenuRouter> routers = menuService.findMenuRouters(roles);
		return Response.ok(routers);
	}

	@GetMapping("/list")
	public Response<List<Object>> list() {
		return Response.ok(List.of());
	}

	@GetMapping("/login-histories")
	public Response<List<Void>> retrieveLoginHistories(RetrieveLoginHistoryVO retrieveLoginHistoryVO) {
		loginHistoryService.retrieve(retrieveLoginHistoryVO.parameter());
		return Response.ok(List.of());
	}
}
