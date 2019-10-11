package com.data.permission.utils;

import com.data.permission.entity.TableCondition;
import com.data.permission.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class UserUtils {

    @Autowired
    TableService tableService;
    private static UserUtils userUtils;

    @PostConstruct
    public void init() {
        userUtils = this;
        userUtils.tableService = this.tableService;
   }

    public static List<TableCondition> getTableCondition(String tableName){
        return userUtils.tableService.getTableCondition(tableName);
    }
}
