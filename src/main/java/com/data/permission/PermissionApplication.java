package com.data.permission;

import net.sf.jsqlparser.JSQLParserException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.data.permission.dao")
@SpringBootApplication
public class PermissionApplication {

    public static void main(String[] args) throws JSQLParserException {
        SpringApplication.run(PermissionApplication.class, args);
//        JsqlparserDemo jsqlparserDemo = new JsqlparserDemo();
//        jsqlparserDemo.demo();

    }


}
