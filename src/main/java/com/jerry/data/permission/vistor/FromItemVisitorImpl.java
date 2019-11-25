package com.jerry.data.permission.vistor;


import com.jerry.data.permission.entity.TableCondition;
import com.jerry.data.permission.strategy.Strategy;
import com.jerry.data.permission.strategy.context.OperateContext;
import com.jerry.data.permission.utils.UserUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:lizhaojie
 * 创建日期:2019/10/11-15:50
 */
public class FromItemVisitorImpl implements FromItemVisitor {

    private static Logger logger = LoggerFactory
            .getLogger(FromItemVisitorImpl.class);
    // 声明增强条件
    private Expression enhancedCondition;

    // FROM 表名 <----主要的就是这个，判断用户对这个表有没有权限
    @Override
    public void visit(Table tableName) {
        //获取该sql的限制条件
        List<TableCondition> conditions = UserUtils.getTableConditions();
        //判断该表是否是需要操作的表
        if (isActionTable(tableName.getFullyQualifiedName())) {
            if (conditions != null) {
                //过滤该表的限制条件
                List<TableCondition> filterConditions = conditions.stream().filter(
                        a -> a.getTableName().toUpperCase().equals(tableName.getFullyQualifiedName().toUpperCase()))
                        .collect(Collectors.toList());
                //增强sql
                for (TableCondition tableCondition : filterConditions) {
                    // 声明表达式数组
                    Expression[] expressions = new Expression[0];
                    Column column = new Column(new Table(tableName.getAlias() != null ? tableName.getAlias().getName() : tableName.getFullyQualifiedName()), tableCondition.getFieldName());
                    if ("1".equals(tableCondition.getFieldName())) {
                        expressions = new Expression[]{new LongValue(tableCondition.getFieldName()), new LongValue(tableCondition.getFieldValue())};
                    } else {
                        expressions = new Expression[]{column, new StringValue(tableCondition.getFieldValue())};
                    }

                    try {
                    if (this.enhancedCondition != null) {
                        if (tableCondition.getEnhancedType().equalsIgnoreCase("or")) {
                            Strategy strategy = OperateContext.getStrategy(tableCondition.getOperator());
                            Expression operator = strategy.getOperator(tableCondition.getOperator(), expressions);
                            enhancedCondition = new OrExpression(enhancedCondition, operator);
                        }
                        if (tableCondition.getEnhancedType().equalsIgnoreCase("and")) {
                            Strategy strategy = OperateContext.getStrategy(tableCondition.getOperator());
                            Expression operator = strategy.getOperator(tableCondition.getOperator(), expressions);
                            enhancedCondition = new AndExpression(enhancedCondition, operator);
                        }
                        if (tableCondition.getEnhancedType().equalsIgnoreCase("not")) {
                            Strategy strategy = OperateContext.getStrategy(tableCondition.getOperator());
                            Expression operator = strategy.getOperator(tableCondition.getOperator(), expressions);
                            enhancedCondition = new NotExpression(enhancedCondition);
                        }
                    } else {
                        Strategy strategy = OperateContext.getStrategy(tableCondition.getOperator());
                        Expression operator = strategy.getOperator(tableCondition.getOperator(), expressions);
                        enhancedCondition = operator;
                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // FROM 子查询
    @Override
    public void visit(SubSelect subSelect) {
        // 如果是子查询的话返回到select接口实现类
        subSelect.getSelectBody().accept(new SelectVisitorImpl());
    }

    // FROM subjoin
    @Override
    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(new FromItemVisitorImpl());
        subjoin.getJoin().getRightItem().accept(new FromItemVisitorImpl());
    }

    // FROM 横向子查询
    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        lateralSubSelect.getSubSelect().getSelectBody()
                .accept(new SelectVisitorImpl());
    }

    // FROM value列表
    @Override
    public void visit(ValuesList valuesList) {
    }

    // FROM tableFunction
    @Override
    public void visit(TableFunction tableFunction) {
    }

    public Expression getEnhancedCondition() {
        return enhancedCondition;
    }

    // 判断传入的table是否是要进行操作的table
    public boolean isActionTable(String tableName) {
        // 默认为操作
        boolean flag = true;
        // 无需操作的表的表名
        List<String> tableNames = new ArrayList<String>();
        // 由于sql可能格式不规范可能表名会存在小写，故全部转换成大写,最上面的方法一样
        if (tableNames.contains(tableName.toUpperCase())) {
            // 如果表名在过滤条件中则将flag改为flase
            flag = false;
        }
        return flag;
    }
}
