package com.xigua.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xigua.mapper.UserMapper;
import com.xigua.entity.User;
import com.xigua.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserImpl
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/9 15:18
 */
@AllArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    public void saveOrUpdateTest(){
        User user = new User();
        user.setName("zz");
        user.setAge(20);
        this.saveOrUpdate(user);
        this.saveOrUpdateBatch(null);
    }
}
