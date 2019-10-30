package com.data.permission.strategy.rules;

import com.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-9:56
 */
public class GreaterThanRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        GreaterThan gt = new GreaterThan();
        gt.setLeftExpression(exp[0]);
        gt.setRightExpression(exp[1]);
        return gt;
    }
}
