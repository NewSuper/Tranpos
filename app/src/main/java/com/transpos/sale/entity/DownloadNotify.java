package com.transpos.sale.entity;

public class DownloadNotify {

    /// <summary>
    /// 是否下载成功
    /// </summary>
    private boolean success;

    /// <summary>
    /// 同步操作
    /// </summary>
    private DownloadCacheName operate;

    /// <summary>
    /// 缓存名称
    /// </summary>
    private String cacheName;

    /// <summary>
    /// 同步信息
    /// </summary>
    private String message;

    /// <summary>
    /// 是否分页
    /// </summary>
    private boolean isPager;

    /// <summary>
    /// 总页数
    /// </summary>
    private int pageCount;

    /// <summary>
    /// 当前页码
    /// </summary>
    private int pageNumber;

    /// <summary>
    /// 每页数量
    /// </summary>
    private int pageSize;

    /// <summary>
    /// 总条数
    /// </summary>
    private int totalCount;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DownloadCacheName getOperate() {
        return operate;
    }

    public void setOperate(DownloadCacheName operate) {
        this.operate = operate;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPager() {
        return isPager;
    }

    public void setPager(boolean pager) {
        isPager = pager;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

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
}
