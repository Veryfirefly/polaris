package com.xsdq.polaris.infrastructure.web;

import com.xsdq.polaris.common.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class PolarisExceptionAdvice {

	private static final Logger log = LoggerFactory.getLogger(PolarisExceptionAdvice.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoResourceFoundException.class)
	public Response<String> notFoundResourceException(NoResourceFoundException exception) {
		log.info("No resource found, '{} {}'", exception.getHttpMethod().name(), exception.getResourcePath());
		return new Response<>(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
}
