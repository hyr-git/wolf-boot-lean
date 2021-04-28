package com.hyr.lean.base.common.async.config;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
 
/**
 * @version : 1.0
 * @description :
 **/
 
@Configuration
public class AsyncConfig {
 
    /**
     * the core pool size of thread pool 2
     */
    @Value("${system.async.core-pool-size:5}")
    private int corePoolSize;
    /**
     * the max pool size of thread pool 10
     */
    @Value("${system.async.max-pool-size:30}")
    private int maxPoolSize;
    /**
     * the  thread pool name
     */
    @Value("${system.async.thread-name:Oylmpic-Async-Service-}")
    private String threadName;
   /**
     * the  queue size of thread pool
     */
    @Value("${system.async.queue-size:30}")
    private int queueSize;
 
    @Value("${system.async.keep-alive-time:10}")
    private int keepAliveTime;
 
    /** 
     *   默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
     *	当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     *  当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝 
     */
    	
    /**
     * 注入 Spring 线程池
     * @return
     */
    @Bean
    public TaskExecutor taskExecutor() {
 
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix(threadName);
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueSize);
        taskExecutor.setKeepAliveSeconds(keepAliveTime);
        //用来蛇者线程池关闭的时候等待所有任务都完成后，在继续销毁其他的bean.这些异步任务的销毁就会先于数据库连接池对象的销毁
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间,若超过该时间还没有销毁就强制销毁,已确保应用能够关闭而不是阻塞住
        taskExecutor.setAwaitTerminationSeconds(60);
		
		// 线程池对拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 初始化
        taskExecutor.initialize();
	    return taskExecutor;
    }
}
