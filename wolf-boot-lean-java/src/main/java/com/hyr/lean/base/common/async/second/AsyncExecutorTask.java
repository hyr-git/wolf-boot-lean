package com.hyr.lean.base.common.async.second;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class AsyncExecutorTask extends AbstractTask {
	
	
	/*@Bean("taskExecutor")
	public Executor taskExecutor() {
	    ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
	    executor.setPoolSize(20);
	    executor.setThreadNamePrefix("taskExecutor-");
	    executor.setWaitForTasksToCompleteOnShutdown(true);
	    executor.setAwaitTerminationSeconds(60);
	    return executor;
	}*/
	
    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
        super.doTaskOne();
        System.out.println("任务一，当前线程：" + Thread.currentThread().getName());
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception {
        super.doTaskTwo();
        System.out.println("任务二，当前线程：" + Thread.currentThread().getName());
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception {
        super.doTaskThree();
        System.out.println("任务三，当前线程：" + Thread.currentThread().getName());
    }
}
