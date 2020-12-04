package com.newsuper.t.markert.entity;

import java.util.List;

public class BasePagerResponseData<T> {

    /// <summary>
    /// 当前页码
    /// </summary>
    private int pageNumber;

    /// <summary>
    /// 每页展示
    /// </summary>
    private int pageSize;

    /// <summary>
    /// 总条数
    /// </summary>
    private int totalCount;

    /// <summary>
    /// 总页数
    /// </summary>
    public int pageCount;

    /// <summary>
    /// 返回数据
    /// </summary>
    private List<T> list;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
