package com.jerry.data.permission.strategy.rules;

import com.jerry.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:14
 */
public class LikeRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        LikeExpression like = new LikeExpression();
        like.setNot(false);
        like.setLeftExpression(exp[0]);
        like.setRightExpression(exp[1]);
        return like;
    }
}
