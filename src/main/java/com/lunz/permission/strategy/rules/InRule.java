package com.data.permission.strategy.rules;

import com.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;

import java.util.Arrays;
import java.util.List;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:15
 */
public class InRule implements Strategy{
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        InExpression in = new InExpression();
        in.setNot(false);
        in.setLeftExpression(exp[0]);
        String[] split = exp[1].toString().replace("'", "").split(",");
        Expression[] expressions = new Expression[split.length];
        for (int i = 0; i < split.length; i++) {
            expressions[i] = new LongValue("'" + split[i] + "'");
        }
        ExpressionList expressionList = new ExpressionList();
        List<Expression> rightExpressions = Arrays.asList(expressions);
        expressionList.setExpressions(rightExpressions);
        in.setRightItemsList(expressionList);
        return in;
    }
}
