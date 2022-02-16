package com.ares.core.model.page;


import com.ares.core.utils.StringUtils;

/**
 * 分页数据
 */
public class PageDomain {
    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String isAsc;

    public static Builder newInstance() {
        return new Builder();
    }

    protected PageDomain(Builder builder) {
        this.pageNum = builder.pageNum;
        this.pageSize = builder.pageSize;
        this.orderByColumn = builder.orderByColumn;
        this.isAsc = builder.isAsc;
    }

    public static class Builder {
        private Integer pageNum;
        private Integer pageSize;
        private String orderByColumn;
        private String isAsc;

        public Builder() {
        }

        public Builder withPageNum(Integer value) {
            this.pageNum = value;
            return this;
        }

        public Builder withPageSize(Integer value) {
            this.pageSize = value;
            return this;
        }

        public Builder withOrderByColumn(String value) {
            this.orderByColumn = value;
            return this;
        }

        public Builder withIsAsc(String value) {
            this.isAsc = value;
            return this;
        }

        public PageDomain build() {
            return new PageDomain(this);
        }
    }

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        //return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
        return orderByColumn + "  " + ("ascending".equals(isAsc) ? "asc" : "desc");
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

}
