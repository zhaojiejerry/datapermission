package com.data.permission.service;

import com.data.permission.entity.TableCondition;

import java.util.List;

/**
 * author:lizhaojie
 * 创建日期:2019/10/11-15:48
 */
public interface TableService {

    List<TableCondition> getTableCondition(String tableName);

}