package com.ares.generator.persistence.model;

/**
 * @description:
 * @author: Young 2020/03/31
 **/
public enum ColumnType {
    STRING("varchar", "String"),
    DATETIME("datetime", "Date"),
    TIMESTAMP("timestamp", "Date"),
    INTEGER("int", "Integer"),
    BIGINT("bigint", "Integer"),
    NUMERIC("numeric", "Long"),
    DECIMAL("decimal", "Long"),
    LONGVARCHAR("longvarchar", "String");

    private String jdbcType;

    private String javaType;

    ColumnType(String jdbcType, String javaType) {
        this.jdbcType = jdbcType;
        this.javaType = javaType;
    }

    public static String getJavaType(String jdbcType) {
        String result = "";
        for (ColumnType type : values()) {
            if (jdbcType.equalsIgnoreCase(type.jdbcType)) {
                result = type.javaType;
            }
        }
        return "".equals(result) ? "String" : result;
    }
}
