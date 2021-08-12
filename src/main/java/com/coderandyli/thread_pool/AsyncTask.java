package com.coderandyli.thread_pool;

import com.coderandyli.thread_pool.config.AysncThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 *
 *
 * @Date 2021/7/14 3:27 下午
 * @Created by lizhenzhen
 */
@Slf4j
@Component
public class AsyncTask {
    
    @Resource(name = "bizOrderThreadPool")
    private ExecutorService orderThreadPool;

    /**
     * 使用默认线程池
     */
    @Async
    public void defaultAsyncTask() {
        log.info("exec the default asynchronous task, the crrentName is 【{}】", Thread.currentThread().getName());
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用订单业务线程池
     */
    @Async("orderAsyncExecutor")
    public void orderAsyncTask() {
        log.info("exec the order asynchronous task, the crrentName is 【{}】", Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 模拟异常 {@link AysncThreadPoolConfig#getAsyncUncaughtExceptionHandler()} 测试
         */
        throw new NullPointerException("模拟空指针异常");
    }

    /**
     * 使用报价业务线程池
     */
    @Async("quoteAsyncExecutor")
    public void quoteAsyncTask() {
        log.info("exec the quote asynchronous task, the crrentName is 【{}】", Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * ------------------------- 非注解方式 -----------------------------
     */

    public void orderAsyncTask2() {
        orderThreadPool.execute(new OrderBiz());

        // orderThreadPool.execute(() -> {});
    }

    public void orderAsyncTask3() {
        Future<?> submit = orderThreadPool.submit(new OrderBiz());
        System.out.println( "orderAsyncTask3 ....");
        // Object o = submit.get();
    }



}
