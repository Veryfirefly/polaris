package com.xsdq.polaris.common.response;

/**
 * TODO 暂时还没有涉及到page response的使用
 *
 * @author XiaoYu
 * @since 2026/8/11 15:27
 */
public class PageResponse<T> extends Response<T> {

  private final int page;
  private final int size;
  private final int total;

  public PageResponse(int page, int size, int total, int status, T data, String message) {
    this.page = page;
    this.size = size;
    this.total = total;
    super(status, data, message);
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }

  public int getTotal() {
    return total;
  }
}
