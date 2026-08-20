package com.xsdq.polaris.bean.event;

import com.xsdq.polaris.bean.LoginStatus;
import com.xsdq.polaris.security.PolarisUserDetails;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author XiaoYu
 * @since 2026/6/30 16:00
 */
@Getter
public class LoginHistoryEvent extends ApplicationEvent {

  private final PolarisUserDetails userDetails;
  private final LoginStatus loginStatus;
  private final String ipAddr;

  public LoginHistoryEvent(PolarisUserDetails userDetails, LoginStatus loginStatus, String ipAddr) {
    super(userDetails);
    this.userDetails = userDetails;
    this.loginStatus = loginStatus;
    this.ipAddr = ipAddr;
  }
}
