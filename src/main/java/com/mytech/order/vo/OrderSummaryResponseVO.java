package com.mytech.order.vo;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrderSummaryResponseVO {


    private Integer pageCount;
    private List<OrderVO> data;

    public List<OrderVO> getData() {
        return data;
    }

    public void setData(List<OrderVO> data) {
        this.data = data;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
