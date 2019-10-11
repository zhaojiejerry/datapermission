package com.data.permission.service.impl;

import com.data.permission.dao.ApplicationDAO;
import com.data.permission.entity.Application;
import com.data.permission.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:lizhaojie
 * 创建日期:2019/9/24-15:50
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationDAO applicationDAO;


    @Override
    public List<Application> getAllApplications() {
        return applicationDAO.getList();
    }

    @Override
    public int addApplication(Application application) {
        return applicationDAO.insertApplication(application);
    }

    @Override
    public Application getApplicationById(String id) {

            Application result = applicationDAO.getApplicationById(id);

            return result;
    }
}
