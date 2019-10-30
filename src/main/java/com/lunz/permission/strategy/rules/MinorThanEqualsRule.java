package com.data.permission.strategy.rules;

import com.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:03
 */
public class MinorThanEqualsRule implements Strategy {


    @Override
    public Expression getOperator(String op, Expression[] exp) {
        MinorThanEquals leq = new MinorThanEquals();
        leq.setLeftExpression(exp[0]);
        leq.setRightExpression(exp[1]);
        return leq;
    }

}
