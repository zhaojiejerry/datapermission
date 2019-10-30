package com.jerry.data.permission.improve;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * author:lizhaojie
 * 创建日期:2019/10/25-12:56
 */
@Component
public class UpdateUtil {

    public static void improveUpdateSql(MetaObject statementHandler, String sql, Update update, Map<String,Object> map){

        List<Expression> expressions = update.getExpressions();

        List<Column> columns = update.getColumns();

        map.forEach((key,val)->{

            boolean match = columns.stream().anyMatch(column -> column.getColumnName().equals(key));
            if (!match) {
                columns.add(new Column(key));
                expressions.add(new StringValue((String) val));
            }
        });

        update.setExpressions(expressions);

        update.setColumns(columns);

        statementHandler.setValue("delegate.boundSql.sql",update.toString());
    }
}
