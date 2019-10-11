package com.data.permission.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Application {
    private String id;
    private String name;
    private int typeId;
    private int deleted;
    private Date createAt;
    private Integer number;
}
