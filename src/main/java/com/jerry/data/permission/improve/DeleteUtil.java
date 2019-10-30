package com.jerry.data.permission.improve;

import lombok.val;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author:lizhaojie
 * 创建日期:2019/10/25-12:57
 */
@Component
public class DeleteUtil {

    public static void improveMybatisPlusLogicDeletedSql(MetaObject statementHandler, String sql, Update update, Map<String, Object> map) throws Throwable {

        List<Expression> expressions = new ArrayList<>();

        List<Column> columnList = new ArrayList<>();


        columnList.addAll(update.getColumns());

        expressions.addAll(update.getExpressions());

        update.getColumns().clear();

        update.getExpressions().clear();

        map.forEach((key, val) -> {

            boolean match = columnList.stream().anyMatch(column -> column.getColumnName().equals(key));
            if (!match) {
                columnList.add(new Column(key));
                expressions.add(new StringValue((String) val));
            }
        });

        update.setExpressions(expressions);

        update.setColumns(columnList);

        statementHandler.setValue("delegate.boundSql.sql", update.toString());
    }
}
