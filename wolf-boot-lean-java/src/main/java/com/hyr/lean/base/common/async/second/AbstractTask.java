package com.hyr.lean.base.common.async.second;

import java.util.Random;

public abstract class AbstractTask {
    private static Random random = new Random();

    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    public void doTaskTwo() throws Exception {
    	System.out.println("开始做任务二");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    public void doTaskThree() throws Exception {
    	System.out.println("开始做任务三");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }
    
    private long currentTimeMillis() {
    	return System.currentTimeMillis();
    }
}
