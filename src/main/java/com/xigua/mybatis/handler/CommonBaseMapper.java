package com.xigua.mybatis.handler;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @ClassName CommonBaseMapper
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/11 16:22
 */
public interface CommonBaseMapper<T> extends BaseMapper<T> {
    int insertBatch(List<T> list);
}
