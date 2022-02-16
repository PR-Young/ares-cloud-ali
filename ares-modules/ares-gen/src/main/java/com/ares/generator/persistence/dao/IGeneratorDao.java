package com.ares.generator.persistence.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Young 2020/04/28
 **/
@Repository
public interface IGeneratorDao {

    public List<Map<String, Object>> getTables(Map<String, Object> map);

    public List<Map<String, Object>> getColumnsByTable(Map<String, Object> map);

    public List<Map<String, Object>> selectTableColumnListByTableName(String tableName);
}
