package com.data.permission.dao;

import com.data.permission.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ApplicationDAO {

    Application getApplicationById(String id);

    int insertApplication(Application application);

    List<Application> getList();
}
