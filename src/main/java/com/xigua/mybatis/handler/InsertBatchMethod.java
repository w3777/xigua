package com.xigua.mybatis.handler;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

/**
 * @ClassName insertBatch
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/11 16:53
 */
public class InsertBatchMethod extends InsertBatchSomeColumn {
    @Override
    public String getMethod(SqlMethod sqlMethod) {
        return "insertBatch";
    }
}
