package com.xigua.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xigua.entity.Count;
import com.xigua.mapper.CountMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.Lock;

/**
 * @ClassName CountController
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/12/18 16:03
 */
@AllArgsConstructor
@RequestMapping("count")
@RestController
public class CountController {
    private final CountMapper countMapper;

//    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    @PostMapping("/test01")
    public synchronized void  test01() throws InterruptedException {
//        Thread.sleep(1000);
        Integer count = countMapper.selectOne(new LambdaQueryWrapper<Count>().eq(Count::getId, 1)).getCount();
        countMapper.update(null,new LambdaUpdateWrapper<Count>()
                .eq(Count::getId,1)
                .set(Count::getCount,count + 1));
    }
}
