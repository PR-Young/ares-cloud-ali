package com.ares.core.model.table;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/24
 **/
@Data
public class TableResult<T> {
    private Integer pageNo;
    private Long total;
    private List<T> rows;

    public TableResult() {
    }

    public TableResult(Integer pageNo, Long total, List<T> rows) {
        this.pageNo = pageNo;
        this.total = total;
        this.rows = rows;
    }
}
