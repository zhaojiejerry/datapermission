package com.data.permission.strategy.rules;

import com.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:13
 */
public class IsNullRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        IsNullExpression isNull = new IsNullExpression();
        isNull.setNot(false);
        isNull.setLeftExpression(exp[0]);
        return isNull;
    }
}
