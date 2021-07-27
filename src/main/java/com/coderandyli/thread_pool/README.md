# 线程池
## 什么是线程池

### 原理
线程池（Thread Pool）是一种基于池化思想管理线程的工具
> Pooling is the grouping together of resources (assets, equipment, personnel, effort, etc.) for the purposes of maximizing advantage or minimizing risk to the users. The term is used in finance, computing and equipment management.——wikipedia
最大化收益并最小化风险，而将资源统一在一起管理的一种思想

### 线程池的作用
- **降低资源的消耗**: 过池化技术重复利用已创建的线程，降低线程创建和销毁造成的损耗
- **提高响应速度**：任务到达时，无需等待线程创建即可立即执行。
- **提高线程的可管理性**：线程是稀缺资源，如果无限制创建，不仅会消耗系统资源，还会因为线程的不合理分布导致资源调度失衡，降低系统的稳定性。使用线程池可以进行统一的分配、调优和监控。
- **提供更多更强大的功能**：线程池具备可拓展性，允许开发人员向其中增加更多的功能。比如延时定时线程池ScheduledThreadPoolExecutor，就允许任务延期执行或定期执行。

## 线程池的实现
- ThreadPooolExector UML类图
- 线程池内部构建了一个生产者-消费者模型，将线程和任务解耦, 二者不直接关联，从而有良好的任务缓冲、线程复用。主要分为【任务管理】和【线程管理】两部分，

### 线程池生命周期
![image](../../../../../../images/thread-pool/thread-pool-lifecycle.png)


## 线程池需要关闭吗？
- 局部线程池，请务必务必要手动关闭








# Reference
- [Java线程池实现原理及其在美团业务中的实践](https://zhuanlan.zhihu.com/p/123328822)
- [Spring官方-scheduling-annotation-support-async](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling-annotation-support-async)
- [springboot通过AsyncConfigurer接口实现异步线程池自动化配置组件](https://blog.csdn.net/yaomingyang/article/details/108165496)
- [线程池运用不当的一次线上事故](https://www.heapdump.cn/article/646639)
- [如何优雅的使用和理解线程池](https://segmentfault.com/a/1190000015808897)
- [execute与submit方法的区别](https://stackoverflow.com/questions/18730290/what-is-the-difference-between-executorservice-submit-and-executorservice-execut?answertab=votes#tab-top)