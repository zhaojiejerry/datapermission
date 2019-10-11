package com.data.permission.service.impl;

import com.data.permission.dao.TableDAO;
import com.data.permission.entity.TableCondition;
import com.data.permission.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:lizhaojie
 * 创建日期:2019/10/11-15:50
 */
@Service
public class TableServiceImpl implements TableService {

    @Autowired
    TableDAO tableDao;

    @Override
    public List<TableCondition> getTableCondition(String tableName) {

        List<TableCondition> conditions =  tableDao.getTableConditionByTableName(tableName);
        return  conditions;
    }
}
