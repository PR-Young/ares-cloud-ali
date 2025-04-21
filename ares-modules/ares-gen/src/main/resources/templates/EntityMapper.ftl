<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPackage}.I${entityName}Dao">
    <resultMap id="${entityName}" type="${entityPackage}.${entityName}">
        <id column="ID_" jdbcType="BIGINT" property="id"/>
        <#list columns as column>
            <result column="${column.columnName}" jdbcType="${column.jdbcType}" property="${column.name}"/>
        </#list>
        <result column="CREATOR_" jdbcType="BIGINT" property="creator"/>
        <result column="CREATE_TIME_" jdbcType="TIMESTAMP" property="createTime"/>
        <result column="MODIFIER_" jdbcType="BIGINT" property="modifier"/>
        <result column="MODIFY_TIME_" jdbcType="TIMESTAMP" property="modifyTime"/>
    </resultMap>

    <sql id="Base_Column_List">
        `ID_`,${strColumn} `CREATOR_`,`CREATE_TIME_`,`MODIFIER_`, `MODIFY_TIME_`
    </sql>

    <insert id="insert" parameterType="${entityPackage}.${entityName}">
        insert into `${tableName}` (
        <include refid="Base_Column_List"/>
        )
        values (
        ${insertValue}
        )
    </insert>

    <update id="update" parameterType="${entityPackage}.${entityName}">
        update `${tableName}`
        <set>
            ${updateValue}
        </set>
        ${whereIdSql}
    </update>

    <delete id="deleteByIds" parameterType="java.util.List">
        delete from `${tableName}`
        where `ID_` in
        <foreach collection="list" item="id" open="(" separator="," close=")">
            ${specialId}
        </foreach>
    </delete>

    <select id="getById" parameterType="java.lang.Long" resultMap="${entityName}">
        select
        <include refid="Base_Column_List"/>
        from `${tableName}`
        ${whereIdSql}
    </select>

    <select id="list" parameterType="java.util.Map" resultMap="${entityName}">
        select
        <include refid="Base_Column_List"/>
        from `${tableName}`
        <where>
        <if test="search != null">

        </if>
        </where>
        <if test="sort != null">
            order by ${specialSort}
        </if>
        <if test="sort == null">
            order by `CREATE_TIME_` desc
        </if>
    </select>

    <select id="selectList" parameterType="${entityPackage}.${entityName}Query" resultMap="${entityName}">
        select
        <include refid="Base_Column_List"/>
        from `${tableName}`
        <where>
        <if test="beginTime != null and beginTime != ''"><!-- 开始时间检索 -->
            AND date_format(`CREATE_TIME_`,'%y%m%d') &gt;= date_format(${r"#{beginTime}"},'%y%m%d')
        </if>
        <if test="endTime != null and endTime != ''"><!-- 结束时间检索 -->
            AND date_format(`CREATE_TIME_`,'%y%m%d') &lt;= date_format(${r"#{endTime}"},'%y%m%d')
        </if>
        </where>
    </select>
</mapper>
