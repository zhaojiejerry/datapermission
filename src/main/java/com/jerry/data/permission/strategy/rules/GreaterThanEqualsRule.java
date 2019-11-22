package com.jerry.data.permission.strategy.rules;

import com.jerry.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-9:58
 */
public class GreaterThanEqualsRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        GreaterThanEquals geq = new GreaterThanEquals();
        geq.setLeftExpression(exp[0]);
        geq.setRightExpression(exp[1]);
        return geq;
    }
}
