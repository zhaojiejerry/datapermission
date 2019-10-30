package com.data.permission.strategy.rules;

import com.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-9:27
 */
public class EqualsRule implements Strategy {

    @Override
    public Expression getOperator(String op, Expression[] exp) {
        EqualsTo eq = new EqualsTo();
        eq.setLeftExpression(exp[0]);
        eq.setRightExpression(exp[1]);
        return eq;
    }
}
