package com.xsdq.polaris.controller;

import lombok.Builder;
import lombok.Data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author XiaoYu
 * @since 2026/1/15 16:02
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	@GetMapping("/analysis")
	public AnalysisData analysis() {
		return AnalysisData.builder()
				.module("/api/dashboard/analysis")
				.path("GET /api/dashboard/analysis")
				.build();
	}

	@Data
	@Builder
	public static class AnalysisData {
		private String module;
		private String path;
	}
}
