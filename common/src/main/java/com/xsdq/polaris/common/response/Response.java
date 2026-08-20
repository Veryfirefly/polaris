package com.xsdq.polaris.common.response;

/**
 * @author XiaoYu
 * @since 2026/8/11 15:16
 */
public class Response<T> {

  private final int status;
  private final T data;
  private final String message;

  Response(int status, T data, String message) {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public Response(int status, T data) {
    this(status, data, null);
  }

  public Response(int status, String message) {
    this(status, null, message);
  }

  public int getStatus() {
    return status;
  }

  public T getData() {
    return data;
  }

  public String getMessage() {
    return message;
  }

  public static <T> Response<T> ok(T data) {
    return new Response<>(200, data);
  }

  public static <T> Response<T> error(int status, String message) {
    return new Response<>(status, message);
  }

  //	public static <T> Response<T> forbidden(String message) {
  //		return new Response<>(HttpStatus.FORBIDDEN.value(), message);
  //	}
  //
  //	public static <T> Response<T> forbidden(Throwable t) {
  //		return forbidden(t.getMessage());
  //	}
  //
  //	public static <T> Response<T> unauthorized(String message) {
  //		return new Response<>(HttpStatus.UNAUTHORIZED.value(), message);
  //	}
  //
  //	public static <T> Response<T> unauthorized(Throwable t) {
  //		return unauthorized(t.getMessage());
  //	}
}
