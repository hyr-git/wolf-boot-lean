package com.hyr.lean.base.common.async.component;

import java.time.LocalDateTime;
import java.util.concurrent.Future;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AsyncTask {

	@Async
	public void task1() throws InterruptedException {
		log.info("task1任务耗时-------------->>>>"+ LocalDateTime.now());
		long currentTimeMillis = System.currentTimeMillis();
		Thread.sleep(1000);
		long currentTimeMillis1 = System.currentTimeMillis();
		log.info("task1任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
	}

	@Async
	public void task2() throws InterruptedException {
		log.info("task2任务耗时-------------->>>>"+LocalDateTime.now());
		long currentTimeMillis = System.currentTimeMillis();
		Thread.sleep(2000);
		long currentTimeMillis1 = System.currentTimeMillis();
		log.info("task2任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
	}

	@Async
	public void task3() throws InterruptedException {
		log.info("task3任务耗时-------------->>>>"+LocalDateTime.now());
		long currentTimeMillis = System.currentTimeMillis();
		Thread.sleep(3000);
		long currentTimeMillis1 = System.currentTimeMillis();
		log.info("task3任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
	}

	@Async
	public Future<String> taskFuture4() throws InterruptedException {
		log.info("taskFuture4任务耗时-------------->>>>"+LocalDateTime.now());
		long currentTimeMillis = System.currentTimeMillis();
		Thread.sleep(1000);
		long currentTimeMillis1 = System.currentTimeMillis();
		log.info("taskFuture4任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
		return new AsyncResult<String>("taskFuture4执行完毕");
	}

	@Async
	public Future<String> taskFuture5() throws InterruptedException {
		log.info("taskFuture5任务耗时-------------->>>>"+LocalDateTime.now());
		long currentTimeMillis = System.currentTimeMillis();
		Thread.sleep(2000);
		long currentTimeMillis1 = System.currentTimeMillis();
		log.info("taskFuture5任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
		return new AsyncResult<String>("taskFuture5执行完毕");
	}

	@Async
	public Future<String> taskFuture6() throws InterruptedException {
		log.info("taskFuture6任务耗时-------------->>>>"+LocalDateTime.now());
		long currentTimeMillis = System.currentTimeMillis();
		Thread.sleep(3000);
		long currentTimeMillis1 = System.currentTimeMillis();
		log.info("taskFuture6任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
		return new AsyncResult<String>("taskFuture6执行完毕");
	}
	
    @Autowired(required = false)
    @Qualifier(value = "taskExecutor")
    private TaskExecutor taskExecutor;
 
    @Async
    public void testAsync() {
        log.info("==========2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("==========3");
    }
 
 
    @Async
    public void testAsync1() {
        ThreadPoolTaskExecutor tpe = ((ThreadPoolTaskExecutor) taskExecutor);
        log.info("----------线程池中的目前存在的线程数量：{}", tpe.getThreadPoolExecutor().getPoolSize());
        log.info("----------线程池中队列大小：{}",tpe.getThreadPoolExecutor().getQueue().size());
        log.info("----------线程池中正在执行任务的线程数量：{}",tpe.getThreadPoolExecutor().getActiveCount());
        log.info("----------线程池中需要执行的任务数量：{}",tpe.getThreadPoolExecutor().getTaskCount());
        log.info("----------线程池中完成的任务数量：{}",tpe.getThreadPoolExecutor().getCompletedTaskCount());
        log.info("=========={}", Thread.currentThread().getName());
    }

}
