package com.jerry.data.permission.vistor;

import net.sf.jsqlparser.statement.select.SelectItemVisitor;

import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

/**
 * author:lizhaojie
 * 创建日期:2019/10/11-15:50
 */
public class SelectItemVisitorImpl implements SelectItemVisitor {

    @Override
    public void visit(AllColumns allColumns) {

    }

    @Override
    public void visit(AllTableColumns allTableColumns) {

    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        selectExpressionItem.getExpression().accept(new ExpressionVisitorImpl());
    }
}
