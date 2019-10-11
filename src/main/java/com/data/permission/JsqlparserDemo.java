package com.data.permission;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

import java.io.StringReader;

public class JsqlparserDemo {

    public void demo() throws JSQLParserException{

        CCJSqlParserManager pm = new CCJSqlParserManager();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update ac_operator op ");
        stringBuffer.append("set op.errcount=(");
        stringBuffer.append("(select case when op1.errcount is null then 0 else op1.errcount end as errcount ");
        stringBuffer.append("from ac_operator op1 ");
        stringBuffer.append("where op1.loginname = '中国' )+1");
        stringBuffer.append("),lastlogin='中国' ");
        stringBuffer.append("where PROCESS_ID=");
        stringBuffer.append("(select distinct g.id from tempTable g where g.ID='中国')");
        stringBuffer.append("and columnName2 = '890' and columnName3 = '678' and columnName4 = '456'");

        Statement statement = pm.parse(new StringReader(stringBuffer.toString()));

        if (statement instanceof Update) {
            //获得Update对象
            Update updateStatement = (Update) statement;
            //获得表名
            System.out.println("table:"+updateStatement.getTables().get(0).getName());
            //获得where条件表达式
            Expression where = updateStatement.getWhere();
            //初始化接收获得到的字段信息
            StringBuffer allColumnNames = new StringBuffer();
            //BinaryExpression包括了整个where条件，
            //例如：AndExpression/LikeExpression/OldOracleJoinBinaryExpression
            if(where instanceof BinaryExpression){
                allColumnNames = getColumnName((BinaryExpression)(where),allColumnNames);
                System.out.println("allColumnNames:"+allColumnNames.toString());
            }
        }
    }
    /**
     * 获得where条件字段中列名，以及对应的操作符
     * @Title: getColumnName
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param expression
     * @param @param allColumnNames
     * @param @return 设定文件
     * @return StringBuffer 返回类型
     * @throws
     */
    private static StringBuffer getColumnName(Expression expression,StringBuffer allColumnNames) {

        String columnName = null;
        if(expression instanceof BinaryExpression){
            //获得左边表达式
            Expression leftExpression = ((BinaryExpression) expression).getLeftExpression();
            //如果左边表达式为Column对象，则直接获得列名
            if(leftExpression  instanceof Column){
                //获得列名
                columnName = ((Column) leftExpression).getColumnName();
                allColumnNames.append(columnName);
                allColumnNames.append(":");
                //拼接操作符
                allColumnNames.append(((BinaryExpression) expression).getStringExpression());
                //allColumnNames.append("-");
            }
            //否则，进行迭代
            else if(leftExpression instanceof BinaryExpression){
                getColumnName((BinaryExpression)leftExpression,allColumnNames);
            }

            //获得右边表达式，并分解
            Expression rightExpression = ((BinaryExpression) expression).getRightExpression();
            if(rightExpression instanceof BinaryExpression){
                Expression leftExpression2 = ((BinaryExpression) rightExpression).getLeftExpression();
                if(leftExpression2 instanceof Column){
                    //获得列名
                    columnName = ((Column) leftExpression2).getColumnName();
                    allColumnNames.append("-");
                    allColumnNames.append(columnName);
                    allColumnNames.append(":");
                    //获得操作符
                    allColumnNames.append(((BinaryExpression) rightExpression).getStringExpression());
                }
            }
        }
        return allColumnNames;
    }
}
