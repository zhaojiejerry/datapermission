package com.lunz.permission.improve;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * author:lizhaojie
 * 创建日期:2019/10/25-12:57
 */
@Component
public class InsertUtil {

    public  static  void improveInsertSql(Insert insert,MetaObject statementHandler, String sql, Map<String,Object> map)throws Throwable{

        ExpressionList expressionList = new ExpressionList();

        List<Expression> expressions = ((ExpressionList) insert.getItemsList()).getExpressions();


        List<Column> columns = insert.getColumns();

        map.forEach((key,val)->{

            boolean match = columns.stream().anyMatch(column -> column.getColumnName().equals(key));
            if (!match) {
                columns.add(new Column(key));
                expressions.add(new StringValue((String) val));
            }
        });
        expressionList.setExpressions(expressions);
        insert.setItemsList(expressionList);

        insert.setColumns(columns);

        statementHandler.setValue("delegate.boundSql.sql",insert.toString());
    }
}
