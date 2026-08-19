package com.xsdq.polaris.http.whois;

/**
 *
 * @author XiaoYu
 * @since 2026/6/30 10:31
 */
public interface WhoisInfoQuery {

	WhoisInfo getWhoisInfo(String ip) throws WhoisInfoException;
}
