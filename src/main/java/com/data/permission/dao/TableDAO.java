package com.data.permission.dao;

import com.data.permission.entity.TableCondition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:lizhaojie
 * 创建日期:2019/10/11-15:51
 */
@Repository
@Mapper
public interface TableDAO {
    List<TableCondition> getTableConditionByTableName(String tableName);
}
