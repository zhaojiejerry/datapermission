package com.lunz.permission.strategy;

import net.sf.jsqlparser.expression.Expression;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-9:18
 */
public interface Strategy {

  Expression getOperator(String op, Expression[] exp);

}
