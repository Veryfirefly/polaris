package com.xsdq.polaris.http.whois;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Whois info
 *
 * @author XiaoYu
 * @since 2026/6/30 10:15
 */
@Validated
public record WhoisInfo(
    @NotNull @NotEmpty String ip,
    @NotNull String country,
    @NotNull String province,
    @NotNull String city,
    @NotNull String isp) {}
