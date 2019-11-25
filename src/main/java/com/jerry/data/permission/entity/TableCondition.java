package com.jerry.data.permission.entity;

import lombok.Data;

/**
 * author:lizhaojie
 * 创建日期:2019/10/11-15:50
 */

@Data
public class TableCondition {

    private  String id;

    private  String operator;

    private  String fieldName;

    private  String fieldValue;

    private  String tableName;

    private  Integer roleId;

    private  String enhancedType;

    public TableCondition(String id, String operator, String fieldName, String fieldValue, String tableName, Integer roleId, String enhancedType) {
        this.id = id;
        this.operator = operator;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.tableName = tableName;
        this.roleId = roleId;
        this.enhancedType = enhancedType;
    }

    public TableCondition() {

    }
}
