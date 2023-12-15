package com.xigua.service.impl;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.xigua.entity.User;
import com.xigua.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void test01() {
        userServiceImpl.saveOrUpdateTest();
    }

    @Test
    void test02(){
        TableInfo tableInfo = TableInfoHelper.getTableInfo(User.class);
        String keyProperty = tableInfo.getKeyProperty();
    }
}
