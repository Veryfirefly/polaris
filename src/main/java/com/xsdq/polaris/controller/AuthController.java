package com.xsdq.polaris.controller;

import com.xsdq.polaris.repository.Response;
import com.xsdq.polaris.repository.vo.LoginRequest;
import com.xsdq.polaris.security.CreatedToken;
import com.xsdq.polaris.security.PolarisUserDetails;
import com.xsdq.polaris.security.TokenManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器，处理用户登录及令牌签发。
 *
 * @author XiaoYu
 * @since 2026/1/9 14:25
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final TokenManager<PolarisUserDetails> tokenManager;
  private final AuthenticationManager authenticationManager;

  public AuthController(
      TokenManager<PolarisUserDetails> tokenManager, AuthenticationManager authenticationManager) {
    this.tokenManager = tokenManager;
    this.authenticationManager = authenticationManager;
  }

  /**
   * 用户登录接口。
   *
   * <p>验证账号密码，认证通过后生成并返回 JWT 令牌。
   *
   * @param request 登录请求体，包含账号和密码
   * @return 包含令牌及过期时间的登录响应
   */
  @PostMapping("/login")
  public Response<CreatedToken> login(@RequestBody LoginRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(request.account(), request.password());
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    PolarisUserDetails userDetails = (PolarisUserDetails) authentication.getPrincipal();
    CreatedToken token = tokenManager.createToken(userDetails);

    return Response.ok(token);
  }
}
