package com.lunz.permission.improve;

import com.lunz.permission.entity.TableCondition;
import com.lunz.permission.utils.UserUtils;
import com.lunz.permission.vistor.SelectVisitorImpl;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author:lizhaojie
 * 创建日期:2019/10/25-12:56
 */
@Component
public class SelectUtil {

    public static void improveSelectSql(MetaObject statementHandler, String sql,Select select, List<TableCondition> tableCondition)throws Throwable {

        UserUtils.tableConditions(tableCondition);
        //访问各个visitor
        select.getSelectBody().accept(new SelectVisitorImpl());
        //将增强后的sql放回
        statementHandler.setValue("delegate.boundSql.sql",select.toString());

    }

    public static List<String> getTableNameList(Select select){
        //获取表名
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return  tablesNamesFinder.getTableList(select);
    }



}
