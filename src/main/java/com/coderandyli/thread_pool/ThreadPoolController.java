package com.coderandyli.thread_pool;

import com.coderandyli.thread_pool.dto.ThreadPoolDynamicInfoDto;
import com.coderandyli.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Date 2021/7/14 3:55 下午
 * @Created by lizhenzhen
 */
@Slf4j
@RestController
@RequestMapping("thread-pool")
public class ThreadPoolController {

    @Autowired
    private AsyncTask asyncTask;

    @GetMapping("/exec-async-task")
    public void asyncTask() {
        log.info("exec async task, the current is 【{}】", Thread.currentThread().getName());
//        asyncTask.defaultAsyncTask();
//        asyncTask.orderAsyncTask();
//        asyncTask.quoteAsyncTask();
        asyncTask.orderAsyncTask2();
//        asyncTask.orderAsyncTask3();
    }

    @GetMapping("/monitoring")
    public ThreadPoolDynamicInfoDto theadPoolMonitoring() {
        ThreadPoolTaskExecutor globalAsyncExecutor = (ThreadPoolTaskExecutor) SpringContextUtils.getBean("globalAsyncExecutor");
        log.info("【GLOBAL-ASYNC-EXECUTOR INFO】the core pool size is 【{}】, " +
                        "the max pool size is 【{}】," +
                        "the active count is 【{}】," +
                        "the pool size is 【{}】," +
                        "the keep alive seconds is 【{}】," +
                        "the thread group is 【{}】," +
                        "the ThreadNamePrefix is 【{}】," +
                        "the ThreadPriority is 【{}】,",
                globalAsyncExecutor.getCorePoolSize(),
                globalAsyncExecutor.getMaxPoolSize(),
                globalAsyncExecutor.getActiveCount(),
                globalAsyncExecutor.getPoolSize(),
                globalAsyncExecutor.getKeepAliveSeconds(),
                globalAsyncExecutor.getThreadGroup(),
                globalAsyncExecutor.getThreadNamePrefix(),
                globalAsyncExecutor.getThreadPriority());

        ThreadPoolTaskExecutor orderAsyncExecutor = (ThreadPoolTaskExecutor) SpringContextUtils.getBean("orderAsyncExecutor");
        log.info("【ORDER-ASYNC-EXECUTOR INFO】the core pool size is 【{}】, " +
                        "the max pool size is 【{}】," +
                        "the active count is 【{}】," +
                        "the pool size is 【{}】," +
                        "the keep alive seconds is 【{}】," +
                        "the thread group is 【{}】," +
                        "the ThreadNamePrefix is 【{}】," +
                        "the ThreadPriority is 【{}】,",
                orderAsyncExecutor.getCorePoolSize(),
                orderAsyncExecutor.getMaxPoolSize(),
                orderAsyncExecutor.getActiveCount(),
                orderAsyncExecutor.getPoolSize(),
                orderAsyncExecutor.getKeepAliveSeconds(),
                orderAsyncExecutor.getThreadGroup(),
                orderAsyncExecutor.getThreadNamePrefix(),
                orderAsyncExecutor.getThreadPriority());

        ThreadPoolTaskExecutor quoteAsyncExecutor = (ThreadPoolTaskExecutor) SpringContextUtils.getBean("quoteAsyncExecutor");
        log.info("【ORDER-ASYNC-EXECUTOR INFO】the core pool size is 【{}】, " +
                        "the max pool size is 【{}】," +
                        "the active count is 【{}】," +
                        "the pool size is 【{}】," +
                        "the keep alive seconds is 【{}】," +
                        "the thread group is 【{}】," +
                        "the ThreadNamePrefix is 【{}】," +
                        "the ThreadPriority is 【{}】,",
                quoteAsyncExecutor.getCorePoolSize(),
                quoteAsyncExecutor.getMaxPoolSize(),
                quoteAsyncExecutor.getActiveCount(),
                quoteAsyncExecutor.getPoolSize(),
                quoteAsyncExecutor.getKeepAliveSeconds(),
                quoteAsyncExecutor.getThreadGroup(),
                quoteAsyncExecutor.getThreadNamePrefix(),
                quoteAsyncExecutor.getThreadPriority());

        ThreadPoolExecutor bizOrderExecutor = (ThreadPoolExecutor) SpringContextUtils.getBean("bizOrderThreadPool");
        ThreadPoolDynamicInfoDto infoDto = new ThreadPoolDynamicInfoDto();
        infoDto.setActiveCount(
                bizOrderExecutor.getActiveCount()
        );
         infoDto.setCompletedTaskCount(
                 bizOrderExecutor.getCompletedTaskCount()
         );
        infoDto.setCorePoolSize(
                bizOrderExecutor.getCorePoolSize()
        );
//        infoDto.setKeepAliveTime(
//                bizOrderExecutor.getKeepAliveTime()
//        );
        infoDto.setLargestPoolSize(
                bizOrderExecutor.getLargestPoolSize()
        );
        infoDto.setMaximumPoolSize(
                bizOrderExecutor.getMaximumPoolSize()
        );
        infoDto.setPoolSize(
                bizOrderExecutor.getPoolSize()
        );
        infoDto.setQueue(
                bizOrderExecutor.getQueue()
        );
//        infoDto.setRejectedExecutionHandler(
//                bizOrderExecutor.getRejectedExecutionHandler()
//        );
        infoDto.setTaskCount(
                bizOrderExecutor.getLargestPoolSize()
        );
        return infoDto;
    }

    @GetMapping("/modify-config")
    public void modifyTheadPool() {
        ThreadPoolTaskExecutor globalAsyncExecutor = (ThreadPoolTaskExecutor) SpringContextUtils.getBean("globalAsyncExecutor");
        globalAsyncExecutor.setCorePoolSize(100);
        globalAsyncExecutor.initialize();
    }

}
