package com.ares.core.controller;

import com.ares.core.model.page.PageDomain;
import com.ares.core.model.page.TableDataInfo;
import com.ares.core.model.page.TableSupport;
import com.ares.core.utils.SqlUtil;
import com.ares.core.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/01/23
 **/
public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    protected TableDataInfo getDataTable(Page<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setRows(list.getResult());
        rspData.setTotal(list.getTotal());
        return rspData;
    }

    protected MapBuilder buildMapBuilder() {
        return new MapBuilder();
    }

    protected class MapBuilder {
        Map<String, Object> map = new HashMap<>();

        protected MapBuilder() {
        }

        public MapBuilder addParam(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public Map<String, Object> getMap() {
            return map;
        }
    }
}
