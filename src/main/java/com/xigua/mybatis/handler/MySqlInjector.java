package com.xigua.mybatis.handler;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.xigua.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @ClassName MySqlInjector
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/11 16:30
 */
public class MySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);

        InsertBatchMethod insertBatchMethod = new InsertBatchMethod();
        Predicate<TableFieldInfo> fieldInfoPredicate = fieldInfo -> {
            String fillName = fieldInfo.getFieldFill().name();
            List<String> fillNameList = Arrays.asList(FieldFill.INSERT.name(),FieldFill.INSERT_UPDATE.name());
            boolean b = fillNameList.stream().anyMatch(fillName::contains);
            return !b;
        };
        insertBatchMethod.setPredicate(fieldInfoPredicate);
        methodList.add(insertBatchMethod);

        return methodList;
    }
}
