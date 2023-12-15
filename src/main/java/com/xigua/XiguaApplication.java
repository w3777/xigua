package com.xigua;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.xigua.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XiguaApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiguaApplication.class, args);
        TableInfo tableInfo = TableInfoHelper.getTableInfo(User.class);
    }

}
