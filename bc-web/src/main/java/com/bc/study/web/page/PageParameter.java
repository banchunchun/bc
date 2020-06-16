package com.bc.study.web.page;

import java.io.Serializable;

/**
 * @author bc
 * @Date 2020-06-16 9:57
 * @title 请详细描述该类含义
 */
public class PageParameter implements Serializable {


  private static final long serialVersionUID = 3819073942233400328L;

  private final int pageNumber;

  private final int pageSize;

  private final String orderBy;

  private final long offset;

  public enum Order {asc, desc}

  private final Order order;

  public PageParameter(int pageNumber, int pageSize, String orderBy, Order order) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.orderBy = orderBy;
    this.order = order;
    this.offset = (long) ((pageNumber - 1) * pageSize);
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public long getOffset() {
    return offset;
  }

  public Order getOrder() {
    return order;
  }
}
