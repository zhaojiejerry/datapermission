package com.data.permission.strategy.rules;

import com.data.permission.strategy.Strategy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-10:15
 */
public class NotLikeRule implements Strategy {
    @Override
    public Expression getOperator(String op, Expression[] exp) {
        LikeExpression like = new LikeExpression();
        like.setNot(true);
        like.setLeftExpression(exp[0]);
        like.setRightExpression(exp[1]);
        return like;
    }
}
