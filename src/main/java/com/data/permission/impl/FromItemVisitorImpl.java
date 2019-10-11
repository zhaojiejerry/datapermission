package com.data.permission.impl;


import java.util.ArrayList;
import java.util.List;

import com.data.permission.entity.TableCondition;
import com.data.permission.service.TableService;
import com.data.permission.utils.UserUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FromItemVisitorImpl implements FromItemVisitor {

    @Autowired
    TableService tableService;


    private static Logger logger = LoggerFactory
            .getLogger(FromItemVisitorImpl.class);
    // 声明增强条件
    private Expression enhancedCondition;

    // FROM 表名 <----主要的就是这个，判断用户对这个表有没有权限
    @Override
    public void visit(Table tableName) {
        //判断该表是否是需要操作的表
        if (isActionTable(tableName.getFullyQualifiedName())) {
            //根据表名获取该用户对于该表的限制条件
            List<TableCondition> test = UserUtils.getTableCondition(tableName.getFullyQualifiedName().toUpperCase());
            //If the TableConditionList is exist
            if (test!=null) {
                //增强sql
                for (TableCondition tableCondition : test) {
                    // 声明表达式数组
                    Expression[] expressions = new Expression[0];
                    // 如果操作符是between
                    if ("between".equalsIgnoreCase(tableCondition.getOperator())|| "not between".equalsIgnoreCase(tableCondition.getOperator())) {
                        expressions = new Expression[] {
                                new LongValue(tableCondition.getFieldName()),
                                new LongValue(tableCondition.getOperator()),
                                new LongValue(tableCondition.getFieldValue()) };
                    } else if ("is null".equalsIgnoreCase(tableCondition.getOperator())|| "is not null".equalsIgnoreCase(tableCondition.getOperator())) {
                        // 如果操作符是 is null或者是is not null的时候
                        //expressions = new Expression[] { new LongValue(	tableCondition.getFieldName()) };
                    } else {
                        // 其他情况,也就是最常用的情况，比如where   1 = 1
                        Column column = new Column(new Table(tableName.getAlias()!=null?tableName.getAlias().getName():tableName.getFullyQualifiedName()), tableCondition.getFieldName());
                        if ("1".equals(tableCondition.getFieldName())) {
                            expressions = new Expression[] {new LongValue(tableCondition.getFieldName()),new LongValue(tableCondition.getFieldValue())};
                        }else{
                            expressions = new Expression[] {column,new StringValue(tableCondition.getFieldValue())};
                        }
                    }
                    // 根据运算符对原始数据进行拼接
                    Expression operator = this.getOperator(
                            tableCondition.getOperator(), expressions);
                    if (this.enhancedCondition != null) {
                        enhancedCondition = new AndExpression(enhancedCondition , operator);
                    } else {
                        enhancedCondition = operator;
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

    // 将字符串类型的运算符转换成数据库运算语句
    private Expression getOperator(String op, Expression[] exp) {
        if ("=".equals(op)) {
            EqualsTo eq = new EqualsTo();
            eq.setLeftExpression(exp[0]);
            eq.setRightExpression(exp[1]);
            return eq;
        } else if (">".equals(op)) {
            GreaterThan gt = new GreaterThan();
            gt.setLeftExpression(exp[0]);
            gt.setRightExpression(exp[1]);
            return gt;
        } else if (">=".equals(op)) {
            GreaterThanEquals geq = new GreaterThanEquals();
            geq.setLeftExpression(exp[0]);
            geq.setRightExpression(exp[1]);
            return geq;
        } else if ("<".equals(op)) {
            MinorThan mt = new MinorThan();
            mt.setLeftExpression(exp[0]);
            mt.setRightExpression(exp[1]);
            return mt;
        } else if ("<=".equals(op)) {
            MinorThanEquals leq = new MinorThanEquals();
            leq.setLeftExpression(exp[0]);
            leq.setRightExpression(exp[1]);
            return leq;
        } else if ("<>".equals(op)) {
            NotEqualsTo neq = new NotEqualsTo();
            neq.setLeftExpression(exp[0]);
            neq.setRightExpression(exp[1]);
            return neq;
        } else if ("is null".equalsIgnoreCase(op)) {
            IsNullExpression isNull = new IsNullExpression();
            isNull.setNot(false);
            isNull.setLeftExpression(exp[0]);
            return isNull;
        } else if ("is not null".equalsIgnoreCase(op)) {
            IsNullExpression isNull = new IsNullExpression();
            isNull.setNot(true);
            isNull.setLeftExpression(exp[0]);
            return isNull;
        } else if ("like".equalsIgnoreCase(op)) {
            LikeExpression like = new LikeExpression();
            like.setNot(false);
            like.setLeftExpression(exp[0]);
            like.setRightExpression(exp[1]);
            return like;
        } else if ("not like".equalsIgnoreCase(op)) {
            LikeExpression nlike = new LikeExpression();
            nlike.setNot(true);
            nlike.setLeftExpression(exp[0]);
            nlike.setRightExpression(exp[1]);
            return nlike;
        } else if ("between".equalsIgnoreCase(op)) {
            Between bt = new Between();
            bt.setNot(false);
            bt.setLeftExpression(exp[0]);
            bt.setBetweenExpressionStart(exp[1]);
            bt.setBetweenExpressionEnd(exp[2]);
            return bt;
        } else if ("not between".equalsIgnoreCase(op)) {
            Between bt = new Between();
            bt.setNot(true);
            bt.setLeftExpression(exp[0]);
            bt.setBetweenExpressionStart(exp[1]);
            bt.setBetweenExpressionEnd(exp[2]);
            return bt;
        } else {
            // 如果没有该运算符对应的语句
            return null;
        }

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
