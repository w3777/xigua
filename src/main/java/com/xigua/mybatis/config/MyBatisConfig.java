package com.xigua.mybatis.config;

import com.xigua.mybatis.handler.MySqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MyBatisConfig
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/11 16:41
 */
@Configuration
public class MyBatisConfig {
    @Bean
    public MySqlInjector mySqlInjector(){
        return new MySqlInjector();
    }
}
