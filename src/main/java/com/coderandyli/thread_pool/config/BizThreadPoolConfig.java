package com.coderandyli.thread_pool.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 业务线程池
 *
 * @Date 2021/7/16 2:37 下午
 * @Created by lizhenzhen
 */
@Configuration
public class BizThreadPoolConfig {
    /**
     * 订单业务线程池
     * @return
     */
    @Bean(value = "bizOrderThreadPool")
    public ExecutorService buildBizOrderQueueThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("order-thread-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, // 核心线程数
                5,  // 最大线程数
                0L, //
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5), // 等待队列
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy()); // 拒绝策略
        return pool ;
    }

    /**
     * 报价业务线程池
     */
    @Bean(value = "bizQuoteThreadPool")
    public ExecutorService buildBizQuoteQueueThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("quote-thread-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, // 核心线程数
                5,  // 最大线程数
                0L, //
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5), // 等待队列
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy()); // 拒绝策略
        return pool ;
    }

}
