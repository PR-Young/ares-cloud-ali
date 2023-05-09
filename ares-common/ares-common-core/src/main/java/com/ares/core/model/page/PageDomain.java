/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

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
