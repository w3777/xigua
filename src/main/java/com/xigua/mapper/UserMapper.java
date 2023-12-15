package com.xigua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xigua.entity.User;
import com.xigua.mybatis.handler.CommonBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/9 15:15
 */
@Mapper
public interface UserMapper extends CommonBaseMapper<User> {
}
