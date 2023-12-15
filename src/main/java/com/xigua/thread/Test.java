package com.xigua.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Test
 * @Description TODO
 * @Author wangjinfei
 * @Date 2023/11/6 14:08
 */
public class Test {
    public static void main(String[] args) {
//        MyRunnable myRunnable = new MyRunnable();
//        Thread thread1 = new Thread(myRunnable);
//        Thread thread2 = new Thread(myRunnable);
//        thread1.run();
//        thread2.run();
//        //Runnable.run()   同步执行的
//        //通过线程名为main  也可以看出是同步
//        System.out.println("执行完卖票");

        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        //异步
//        myThread1.start();
//        myThread2.start();
        new Thread(myThread1).start();
        new Thread(myThread1).start();
        //同步
//        myThread1.run();
//        myThread2.run();
        System.out.println("执行完卖票");
    }

    static class MyRunnable implements Runnable{
        private int ticket = 5;
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "  " + "Runnable ticket = " + ticket--);
                if(ticket <= 0){
                    break;
                }
            }
        }
    }

    static class MyThread extends Thread{
        private int ticket = 5;
        @Override
        public void run() {
            while(true){
                synchronized (this){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "  " + "Runnable ticket = " + ticket--);
                    if(ticket <= 0){
                        break;
                    }
                }

            }
        }
    }
}
