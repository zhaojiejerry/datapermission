package com.lunz.permission.strategy.rules;

import com.lunz.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;

import java.util.Arrays;
import java.util.List;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:16
 */
public class NotInRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        InExpression notIn = new InExpression();
        notIn.setNot(true);
        notIn.setLeftExpression(exp[0]);
        String[] split = exp[1].toString().replace("'", "").split(",");
        Expression[] expressions = new Expression[split.length];
        for (int i = 0; i < split.length; i++) {
            expressions[i] = new LongValue("'" + split[i] + "'");
        }
        ExpressionList expressionList = new ExpressionList();
        List<Expression> rightExpressions = Arrays.asList(expressions);
        expressionList.setExpressions(rightExpressions);
        notIn.setRightItemsList(expressionList);
        return notIn;
    }
}
