package com.coderandyli.thread_pool.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling-annotation-support-async
 *
 * @Date 2021/7/14 3:14 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Configuration
@EnableAsync
@EnableConfigurationProperties(ThreadPoolProperties.class)
@ConditionalOnProperty(prefix = "spring.async-thread-pool", name = "enable", havingValue = "true", matchIfMissing = false)
public class AysncThreadPoolConfig implements AsyncConfigurer {
    /**
     * The number of CPUs
     */
    private static final int NCPUS = Runtime.getRuntime().availableProcessors();

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    /**
     * 全局线程池
     *  - 不单独指定的话，会使用此线程池
     */
    @Bean("globalAsyncExecutor")
    public ThreadPoolTaskExecutor globalAsyncExecutor() {
        /**
         * 配置公式
         * coreSize = cpu核数 * 2
         * maxSize = cpu核数 * 25
         * 该配置公式没有考虑多业务场景，不适合多线程池的应用中.
         */

        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPool.setCorePoolSize(Objects.nonNull(threadPoolProperties.getCorePoolSize()) ? threadPoolProperties.getCorePoolSize() : 2 * NCPUS);
        //设置最大线程数
        threadPool.setMaxPoolSize(Objects.nonNull(threadPoolProperties.getMaxPoolSize()) ? threadPoolProperties.getMaxPoolSize() : 25 * NCPUS);
        //线程池所使用的缓冲队列
        threadPool.setQueueCapacity(Objects.nonNull(threadPoolProperties.getQueueCapacity()) ? threadPoolProperties.getQueueCapacity() : 20);
        //等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(threadPoolProperties.isWaitForTasksToCompleteOnShutdown());
        // 设置拒绝策略
        // threadPool.setRejectedExecutionHandler();
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setAwaitTerminationSeconds(threadPoolProperties.getAwaitTerminationSeconds());
        //核心线程是否允许超时，默认:false
        threadPool.setAllowCoreThreadTimeOut(threadPoolProperties.isAllowCoreThreadTimeOut());
        //  线程名称前缀
        threadPool.setThreadNamePrefix(StringUtils.isNoneBlank(threadPoolProperties.getThreadNamePrefix()) ? threadPoolProperties.getThreadNamePrefix() : "Async-ThreadPool-");
        // 初始化线程
        threadPool.initialize();
        return threadPool;
    }

    /**
     * 订单业务线程池
     */
    @Bean("orderAsyncExecutor")
    public ThreadPoolTaskExecutor orderAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPool.setCorePoolSize(5);
        //设置最大线程数
        threadPool.setMaxPoolSize(10);
        //线程池所使用的缓冲队列
        threadPool.setQueueCapacity(10);
        //等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setAwaitTerminationSeconds(60);
        //  线程名称前缀
        threadPool.setThreadNamePrefix("OrderAsync-");
        // 初始化线程
        threadPool.initialize();
        return threadPool;
    }

    /**
     * 报价业务线程池
     */
    @Bean("quoteAsyncExecutor")
    public ThreadPoolTaskExecutor quoteAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPool.setCorePoolSize(5);
        //设置最大线程数
        threadPool.setMaxPoolSize(10);
        //线程池所使用的缓冲队列
        threadPool.setQueueCapacity(10);
        //等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setAwaitTerminationSeconds(60);
        //  线程名称前缀
        threadPool.setThreadNamePrefix("QuoteAsync-");
        // 初始化线程
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.globalAsyncExecutor();
    }

    /**
     * https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling-annotation-support-async
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                log.error("【AsyncExecutor】 happend exception, the error message is 【{}】", throwable.getMessage());
            }
        };
    }
}
