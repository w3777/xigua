package com.xigua.threadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName Test
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/7 10:27
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1();
//        Map map = new HashMap<String,Object>();
//        map.compute();
        String s = test2();
        System.out.println(s);
    }
    static void test1(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "========>正在执行！");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "========>执行成功！");
        });
        System.out.println("----------");
    }

    static String test2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "========>正在执行！");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "========>执行成功！");
            return "call";
        });
        String s = future.get();
        return s;
    }
}
