package com.coderandyli.thread_pool;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2021/7/16 11:37 上午
 * @Created by lizhenzhen
 */
@Slf4j
public class OrderBiz implements Runnable{
    @Override
    public void run() {
        log.info("执行order异步任务, the thread name is 【{}】", Thread.currentThread().getName());
    }
}
