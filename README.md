# JUC
Java多线程与JUC
### 线程的生命周期
1. 状态
    * new  --- 新建状态
    * Runnable  --- 调用start()之后，等待CPU
    * Running  --- 获取CPU
    * Dead  --- 退出run()
    * Blocked  
    
![线程生命周期 图片](https://tse2-mm.cn.bing.net/th/id/OIP.9OCSNFgtI90NewcSOv9skwAAAA?pid=Api&rs=1)

2. 强调一下Blocked
    * 挂起的Blocked（Thread.sleep()、.join())
    * 等待的Blocked（Object.wait()，释放同步锁，暂停线程，等待唤醒)
    * 锁定的Blocked（获取不到同步锁而进入同步阻塞状态）

### 控制线程
1. .join()方法==.join(0)  
    waits for this thread to die (当main中调用t1.join()时，main线程进入blocked状态)
2. .join(long)方法
    waits at most .. ms for this thread to die 
3. 后台线程（DaemonThread）又称守护进程  
    .setDaemon(true);(在.start()之前)  
    特征：所有前台线程都死亡了，后台线程也会自动死亡
4. Thread.sleep(long)  
    推荐另一种方法：  
        TimeUnit.SECONDS.sleep(1)  
        TimeUnit.MINUTES.sleep(1)
5. .setPriority()改变线程优先级（1~10）1的优先级最低  
    通过PriorityTest.java运行结果可以看出：  
    * 子线程优先级默认为父线程优先级（默认为5）
    * 优先级只是代表获取CPU的概率高点，不一定必须先执行
6. Thread.yield()线程让步，让当前线程进入到就绪状态，从而让其他具有相同优先级的线程获取CPU执行（进入到就绪状态也意味着可以去竞争CPU）  

### 线程同步
1. 同步代码块  --- SynchronizedTest1.java
2. 同步方法  --- SynchronizedTest2.java
    * 普通同步方法的锁是当前实例对象
    * 静态同步方法的锁是类的Class对象
3. 使用重入锁  --- SynchronizedTest3.java
    * JUC里的Lock接口与synchronized方法和块具有相同的基本行为
    * 但Lock接口能够更明确地控制从哪里开始锁，在哪里释放锁
    * ReentrantLock类（默认为不公平锁）
    * 大量线程同时竞争时，Lock的性能要远远优于Synchronized
4. 使用局部变量（其实不属于线程同步） --- SynchronizedTest4.java
    * ThreadLocal管理变量
    * .get .set
    * 重写.initialValue设置初值
    * 实现每个线程卖100张票，多个线程使用一个变量，但变量的值不需要在各个线程之间共享

5. 同步锁释放问题  
    （任何线程进入同步代码之前，必须获取同步检测器的锁定，那么谁释放对同步检测器的锁定呢？）
    * 释放的情况：
        * 当前线程的同步方法、同步代码块执行结束，当前线程释放同步检测器的锁定
        * 当前线程的同步方法、同步代码块遇到return或break终止了程序继续执行，释放
        * 同步方法、同步代码块出现未处理的error或异常导致程序异常结束，释放同步检测器的锁定
        * 执行同步方法、同步代码块时，程序调用了同步检测器的wait()方法，当前线程暂停，线程会释放同步检测器的锁定
    * 不会释放的情况
        * Thread.sleep() Thread.yield()

### 死锁
* 当前线程拥有其他线程需要的资源
* 当前线程等待其他线程已拥有的资源
* 都不放弃自己拥有的资源

1. 锁顺序死锁  
2. 动态顺序死锁
    * 