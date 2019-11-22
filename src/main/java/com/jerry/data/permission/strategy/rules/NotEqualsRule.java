package com.jerry.data.permission.strategy.rules;

import com.jerry.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:05
 */
public class NotEqualsRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        NotEqualsTo neq = new NotEqualsTo();
        neq.setLeftExpression(exp[0]);
        neq.setRightExpression(exp[1]);
        return neq;
    }
}
