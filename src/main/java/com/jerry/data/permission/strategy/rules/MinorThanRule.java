package com.lunz.permission.strategy.rules;

import com.lunz.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:01
 */
public class MinorThanRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        MinorThan mt = new MinorThan();
        mt.setLeftExpression(exp[0]);
        mt.setRightExpression(exp[1]);
        return mt;
    }
}
