package com.xsdq.polaris.http.whois;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;

import com.xsdq.polaris.util.Utils;
import jakarta.annotation.PostConstruct;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 *
 * @author XiaoYu
 * @since 2026/6/30 10:34
 */
@Component
class IP2RegionWhoisInfoQuery implements WhoisInfoQuery {

	private static final String DEFAULT_GEO_DATABASE_NAME = "ip2region_v4.xdb";
	private static final String UNKNOWN_WHOIS = "unknown|unknown|unknown|unknown";
	private static final String LAN_WHOIS = "内网IP|内网IP|内网IP|内网IP";

	private final Logger log = LoggerFactory.getLogger(getClass());

	private Searcher searcher;

	@PostConstruct
	public void init() {
		try {
			File xdbFile = new ClassPathResource(DEFAULT_GEO_DATABASE_NAME).getFile();
			if (Files.notExists(xdbFile.toPath())) {
				log.warn("classpath下不存在'{}', 加载Geo数据库失败.", DEFAULT_GEO_DATABASE_NAME);
				return;
			}

			byte[] xdbBuffer = Searcher.loadContent(new RandomAccessFile(xdbFile, "r"));
			this.searcher = Searcher.newWithBuffer(xdbBuffer);

			log.info("Geo数据库已加载({}).", xdbFile.getAbsolutePath());
		} catch (Exception e) {
			throw new WhoisInfoException("An error occurred while loading geo database.", e);
		}
	}

	public void destroy() {
		if (searcher != null) {
			try {
				searcher.close();
			} catch (Exception e) {
				// ignore
			}
		}
	}

	public String searchRaw(String ip) {
		if (searcher == null || ip == null || ip.isBlank()) {
			return UNKNOWN_WHOIS;
		}

		if (Utils.isInnerIp(ip)) {
			return LAN_WHOIS;
		}

		try {
			return searcher.search(ip);
		} catch (Exception e) {
			log.warn("ip查询异常 ip={}", ip, e);
			return UNKNOWN_WHOIS;
		}
	}

	@Override
	public WhoisInfo getWhoisInfo(String ip) throws WhoisInfoException {
		String raw = searchRaw(ip);
		String[] rawSplits = raw.split("\\|");
		return new WhoisInfo(ip, rawSplits[0], rawSplits[1], rawSplits[2], rawSplits[3]);
	}
}
