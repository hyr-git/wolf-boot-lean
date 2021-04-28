package com.hyr.lean.base.common.async.controller;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.base.common.async.component.AsyncTask;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AsyncTaskController {

	@Autowired
	private AsyncTask asyncTask;
	
	@Autowired(required = false)
	@Qualifier(value = "taskExecutor")
	private TaskExecutor taskExecutor;


	@GetMapping("task")
	public String doTask() throws InterruptedException {
        Objects.requireNonNull(null);

		long currentTimeMillis = System.currentTimeMillis();
		asyncTask.task1();
		asyncTask.task2();
		asyncTask.task3();
		long currentTimeMillis1 = System.currentTimeMillis();
		return "task任务总耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms";
	}

	@GetMapping("futureTask")
	public String doFutureTask() throws InterruptedException {
		long currentTimeMillis = System.currentTimeMillis();
		Future<String> task1 = asyncTask.taskFuture4();
		Future<String> task2 = asyncTask.taskFuture5();
		Future<String> task3 = asyncTask.taskFuture6();
		String result = null;
		for (;;) {
			if (task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(1000);
		}
		long currentTimeMillis1 = System.currentTimeMillis();
		result = "task任务总耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms";
		return result;
	}

	@GetMapping("test1")
	public void test1() {
		log.info("==========1"+LocalDateTime.now());
		asyncTask.testAsync();
		log.info("==========4"+LocalDateTime.now());
	}

	@GetMapping("test2")
	public void test2() {
		log.info("=test2=========1"+LocalDateTime.now());
		int y = 0;
		for (int i = 0; i < 1000; i++) {
			try {
				asyncTask.testAsync1();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("丢弃任务数" + (++y));
			}
		}
		log.info("test2==========结束"+LocalDateTime.now());

	}

	@GetMapping("test3")
	public void test3() {
		ThreadPoolTaskExecutor tpe = ((ThreadPoolTaskExecutor) taskExecutor);
		log.info("test2----------线程池中的线程数量：{}", tpe.getThreadPoolExecutor().getPoolSize());
		log.info("test2----------线程池中曾经创建过的最大线程数：{}", tpe.getThreadPoolExecutor().getLargestPoolSize());
		log.info("test2----------线程池中活跃的线程数：{}", tpe.getThreadPoolExecutor().getActiveCount());
		log.info("test2----------线程池中需要执行的任务数量：{}", tpe.getThreadPoolExecutor().getTaskCount());
		log.info("test2----------线程池中完成的任务数量：{}", tpe.getThreadPoolExecutor().getCompletedTaskCount());
	}
}
