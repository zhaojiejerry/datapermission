package com.data.permission.utils;

import com.data.permission.entity.TableCondition;
import com.data.permission.service.TableService;
import com.data.permission.service.impl.TableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public static List<TableCondition> getTableCondition(String tableName){

        String role1 = "1";
        String role2 = "2";
        List<String> roleIds = new ArrayList<>();
        roleIds.add(role1);
        roleIds.add(role2);

        TableService tableService = SpringUtil.getBean(TableService.class);

        return tableService.getTableCondition(tableName,roleIds);
    }
}
