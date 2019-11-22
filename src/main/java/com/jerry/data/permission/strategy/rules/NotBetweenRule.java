package com.jerry.data.permission.strategy.rules;

import com.jerry.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.Between;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:16
 */
public class NotBetweenRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        Between bt = new Between();
        bt.setNot(true);
        bt.setLeftExpression(exp[0]);
        String[] split = exp[1].toString().split(",");

        Expression[] expressions = new Expression[]{
                new LongValue(split[0] + "'"),
                new LongValue("'" + split[1])};
        bt.setBetweenExpressionStart(expressions[0]);
        bt.setBetweenExpressionEnd(expressions[1]);
        return bt;
    }
}
