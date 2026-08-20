package com.xsdq.polaris.repository;

import org.springframework.http.HttpStatus;

/**
 * @author XiaoYu
 * @since 2026/1/19 10:48
 */
public record Response<T>(int status, T data, String message) {

  public Response(int status, T data) {
    this(status, data, null);
  }

  public Response(int status, String message) {
    this(status, null, message);
  }

  public static <T> Response<T> ok(T data) {
    return new Response<>(HttpStatus.OK.value(), data);
  }

  public static <T> Response<T> error(int status, String message) {
    return new Response<>(status, message);
  }

  public static <T> Response<T> forbidden(String message) {
    return new Response<>(HttpStatus.FORBIDDEN.value(), message);
  }

  public static <T> Response<T> forbidden(Throwable t) {
    return forbidden(t.getMessage());
  }

  public static <T> Response<T> unauthorized(String message) {
    return new Response<>(HttpStatus.UNAUTHORIZED.value(), message);
  }

  public static <T> Response<T> unauthorized(Throwable t) {
    return unauthorized(t.getMessage());
  }
}
