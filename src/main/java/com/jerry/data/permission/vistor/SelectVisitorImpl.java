package com.jerry.data.permission.vistor;


import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;

/**
 * author:lizhaojie
 * 创建日期:2019/10/11-15:50
 */
public class SelectVisitorImpl  implements SelectVisitor {
    // 主要工作就是实现各种底层visitor，然后在解析的时候添加条件

    // 正常的select，也就是包含全部属性的select
    @Override
    public void visit(PlainSelect plainSelect) {

        // 访问 select
        if (plainSelect.getSelectItems() != null) {
            for (SelectItem item : plainSelect.getSelectItems()) {
                item.accept(new SelectItemVisitorImpl());
            }
        }


        // 访问from
        FromItem fromItem = plainSelect.getFromItem();

        FromItemVisitorImpl fromItemVisitorImpl = new FromItemVisitorImpl();
        fromItem.accept(fromItemVisitorImpl);

        // 访问where
        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(new ExpressionVisitorImpl());
        }

        //过滤增强的条件
        if (fromItemVisitorImpl.getEnhancedCondition() != null) {
            if (plainSelect.getWhere() != null) {
                Expression expr = new Parenthesis(plainSelect.getWhere());
                Expression enhancedCondition =  new Parenthesis(fromItemVisitorImpl.getEnhancedCondition());
                AndExpression and = new AndExpression(enhancedCondition, expr);
                plainSelect.setWhere(and);
            } else {
                plainSelect.setWhere(fromItemVisitorImpl.getEnhancedCondition());
            }
        }

        // 访问join并过滤增强条件
        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                System.out.println(join.getRightItem().toString());
                FromItemVisitorImpl joinFromItemVisitorImpl = new FromItemVisitorImpl();
                join.getRightItem().accept(joinFromItemVisitorImpl);
                if (joinFromItemVisitorImpl.getEnhancedCondition() != null) {
                    if (plainSelect.getWhere() != null) {
                        Expression expr = new Parenthesis(plainSelect.getWhere());
                        Expression enhancedCondition =  new Parenthesis(joinFromItemVisitorImpl.getEnhancedCondition());
                        AndExpression and = new AndExpression(enhancedCondition, expr);
                        plainSelect.setWhere(and);
                    } else {
                        plainSelect.setWhere(joinFromItemVisitorImpl.getEnhancedCondition());
                    }
                }
            }
        }

        // 访问 order by
        if (plainSelect.getOrderByElements() != null) {
            for (OrderByElement orderByElement : plainSelect
                    .getOrderByElements()) {
                orderByElement.getExpression().accept(
                        new ExpressionVisitorImpl());
            }
        }

        // 访问group by having
        if (plainSelect.getHaving() != null) {
            plainSelect.getHaving().accept(new ExpressionVisitorImpl());
        }

    }

    // set操作列表
    @Override
    public void visit(SetOperationList setOpList) {
        for (SelectBody plainSelect : setOpList.getSelects()) {
            plainSelect.accept(new SelectVisitorImpl());
        }
    }

    // with项
    @Override
    public void visit(WithItem withItem) {
        withItem.getSelectBody().accept(new SelectVisitorImpl());
    }
}
