package com.data.permission.entity;

import lombok.Data;

@Data
public class TableCondition {

    private  String id;

    private  String operator;

    private  String fieldName;

    private  String fieldValue;

    private  String tableName;

}
