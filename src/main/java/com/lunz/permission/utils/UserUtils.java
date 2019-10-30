package com.lunz.permission.utils;

import com.lunz.permission.entity.TableCondition;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * UserUtils
 * 用来获取表的限制条件
 * @author lizhaojie
 */
@Component
public class UserUtils {

    private  static  List<TableCondition> tableConditions ;

    public static void tableConditions(List<TableCondition> test) {
        UserUtils.tableConditions = test;
    }

    public static List<TableCondition> getTableConditions() {
        return tableConditions;
    }
}
