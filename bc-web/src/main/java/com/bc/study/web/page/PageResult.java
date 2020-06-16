package com.bc.study.web.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author bc
 * @Date 2020-06-16 9:58
 * @title 请详细描述该类含义
 */
public class PageResult<T> implements Serializable {


  private static final long serialVersionUID = 4124544000863436867L;

  private final long total;

  private final List<T> rows;

  public PageResult(long total, List<T> rows) {
    this.total = total;
    this.rows = rows;
  }

  public long getTotal() {
    return total;
  }


  public List<T> getRows() {
    return rows;
  }


}
