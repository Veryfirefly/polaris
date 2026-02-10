package com.xsdq.polaris.repository;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 *
 * @author XiaoYu
 * @since 2026/1/19 10:48
 */
public record Response<T>(int statusCode, T data, String message) {

	public Response(int statusCode, T data) {
		this(statusCode, data, null);
	}

	public Response(int statusCode, String message) {
		this(statusCode, null, message);
	}

	public void writeToServletResponse(HttpServletResponse response, int httpStatusCode, ObjectMapper objectMapper)
			throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatusCode);
		response.setCharacterEncoding("UTF-8");

		objectMapper.writeValue(response.getWriter(), this);
	}

	public void writeToServletResponse(HttpServletResponse response, HttpStatus httpStatus, ObjectMapper objectMapper)
			throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");

		objectMapper.writeValue(response.getWriter(), this);
	}
}
