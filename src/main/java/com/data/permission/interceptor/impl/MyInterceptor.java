package com.data.permission.interceptor.impl;

import java.io.StringReader;
import java.sql.Connection;
import java.util.Properties;
import com.data.permission.impl.SelectVisitorImpl;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts({
        @Signature(method = "prepare", type = StatementHandler.class,
                args = { Connection.class ,Integer.class}) })
public class MyInterceptor implements Interceptor {

    CCJSqlParserManager parserManager = new CCJSqlParserManager();
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler)invocation.getTarget();
        //由于mappedStatement为protected的，所以要通过反射获取
        MetaObject statementHandler = SystemMetaObject.forObject(handler);
        //mappedStatement中有我们需要的方法id
        MappedStatement mappedStatement = (MappedStatement) statementHandler.getValue("delegate.mappedStatement");
        //获取sql
        BoundSql boundSql = handler.getBoundSql();
        String sql = boundSql.getSql();
        //获取方法id
        String id = mappedStatement.getId();
        //获得方法类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        System.out.println(sqlCommandType);
        if (("com.data.permission.dao.ApplicationDAO.getList").equals(id)) {
            //增强sql代码块
            if ("SELECT".equals(sqlCommandType.name())) {
                //如果是select就将sql转成SELECT对象
                Select select = (Select)parserManager.parse(new StringReader(sql));
                //访问各个visitor
                select.getSelectBody().accept(new SelectVisitorImpl());
                //将增强后的sql放回
                statementHandler.setValue("delegate.boundSql.sql",select.toString());
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
