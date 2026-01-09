package com.xsdq.polaris.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xsdq.polaris.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StringUtils;

import static org.mockito.Mockito.when;

public abstract class MockServletTestKit {

	private static final String QUERY_WHOIS_URL = "http://whois.pconline.com.cn/ipJson.jsp?json=true";
	protected static final String[] USER_AGENT_SAMPLES = {
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.6 Safari/605.1.15"
	};

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Mock
	protected HttpServletRequest request;

	@Mock
	protected HttpServletResponse response;

	protected ObjectMapper polymorphicIdObjectMapper;
	protected WhoisInfo whoisInfo;
	protected boolean shouldQueryWhoisInfo = true;

	@BeforeEach
	void setup() {
		this.polymorphicIdObjectMapper = createPolymorphicIdentifiedObjectMapper();

		when(request.getRemoteAddr()).thenReturn("127.0.0.1");
		when(request.getHeader("User-Agent")).thenReturn(randomChoiceUserAgent());

		this.whoisInfo = queryWhoisInfo();
		for (String headerName : Utils.PROXY_HEADERS) {
			when(request.getHeader(headerName)).thenReturn(whoisInfo.getIp());
		}
	}

	public void shouldQueryWhoisInfo(boolean shouldQueryWhoisInfo) {
		this.shouldQueryWhoisInfo = shouldQueryWhoisInfo;
	}

	String randomChoiceUserAgent() {
		return USER_AGENT_SAMPLES[new Random().nextInt(USER_AGENT_SAMPLES.length)];
	}

	WhoisInfo queryWhoisInfo() {
		if (shouldQueryWhoisInfo) {
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create(QUERY_WHOIS_URL))
					.GET()
					.timeout(Duration.ofSeconds(10))
					.header("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
					.build();

			try (HttpClient httpClient = HttpClient.newBuilder()
					.connectTimeout(Duration.ofSeconds(10))
					.build())
			{
				ObjectMapper localObjectMapper = new ObjectMapper();
				HttpResponse<String> response = httpClient.send(httpRequest,
						HttpResponse.BodyHandlers.ofString(Charset.forName("GBK")));
				if (response.statusCode() == 200) {
					WhoisInfo whoisInfo = localObjectMapper.readValue(response.body(), WhoisInfo.class);
					if (whoisInfo != null && !whoisInfo.isError()) {
						return whoisInfo;
					}
				}
			} catch (IOException | InterruptedException e) {
				log.warn("Failed to query whois info, used mock QueryWhoisInfo", e);
			}
		}
		return mockWhoisInfo();
	}

	protected ObjectMapper createPolymorphicIdentifiedObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
				ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

	protected WhoisInfo mockWhoisInfo() {
		WhoisInfo whoisInfo = new WhoisInfo();
		whoisInfo.setIp("182.150.24.251");
		whoisInfo.setProvince("");
		whoisInfo.setPostCode("");
		whoisInfo.setCity("");
		whoisInfo.setCityCode("");
		whoisInfo.setRegion("");
		whoisInfo.setRegionCode("");
		whoisInfo.setAddr("");
		whoisInfo.setRegionNames("");
		whoisInfo.setError("Mock whois info");
		return whoisInfo;
	}

	public static class WhoisInfo {

		@JsonProperty("ip") private String ip;
		@JsonProperty("pro") private String province;
		@JsonProperty("proCode") private String postCode;
		@JsonProperty("city") private String city;
		@JsonProperty("cityCode") private String cityCode;
		@JsonProperty("region") private String region;
		@JsonProperty("regionCode") private String regionCode;
		@JsonProperty("addr") private String addr;
		@JsonProperty("regionNames") private String regionNames;
		@JsonProperty("err") private String error;

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getPostCode() {
			return postCode;
		}

		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCityCode() {
			return cityCode;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getRegionCode() {
			return regionCode;
		}

		public void setRegionCode(String regionCode) {
			this.regionCode = regionCode;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getRegionNames() {
			return regionNames;
		}

		public void setRegionNames(String regionNames) {
			this.regionNames = regionNames;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		@JsonIgnore
		public boolean isError() {
			return StringUtils.hasText(error);
		}
	}
}
