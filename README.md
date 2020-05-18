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
    * 固定加锁顺序（一种方式是，使用对象的hash值的大小来决定加锁顺序）
2. 动态顺序死锁
3. 使用定时锁 --- SynchronizedTest5.java
    * .tryLock(long timeout, TimeUnit)

### volatile关键字与内存可见性
1. VolatileTest.java运行结果可以看出：
    * 主线程读取flag，为false
    * 子线程将flag改为true，但主线程并未读取到正确的值

2. 共享变量
    * 如果一个变量在多个线程的工作内存中都存在副本，那么这个变量就是这几个线程的共享变量
3. 可见性
    * 一个线程对共享变量值的修改，能够及时的被其他线程看到
4. 共享变量可见性的实现原理
    * 把工作内存1中更新过的共享变量刷新到主内存中
    * 将主内存中最新的共享变量的值更新到工作内存2中
> Java内存模型  

![java内存模型 图片](https://tse3-mm.cn.bing.net/th/id/OIP._4tPQnnQYjAQnJbselC4egHaF9?pid=Api&rs=1)
1. JVM主内存：操作系统给进程分配的内存空间，存储所有的线程共享的变量
2. 工作内存（working memory）：其实是cache和寄存器的一个抽象，并不是内存中的某个部分
3. 线程之间无法直接访问其他线程工作内存中的变量，线程间的变量值的传递需要通过主内存来完成

> 解决共享变量在线程中的及时可见性问题  
1. volatile关键字实现可见性（将flag变量加上volatile关键字）
2. 前面学习的Synchronized也可以实现可见性，JMM有两条相关的规定
    * 线程解锁前，必须把共享变量的最新值刷新到主内存中
    * 线程加锁是，将清空工作内存中共享变量的值
    * 这种方式确实可以保证可见性，但是程序效率太低了，对计算机硬件资源是高开销动作
    
> volatile 和 Synchronized比较
1. volatile不需要加锁，比Synchronized更轻量级，不会阻塞线程，效率高
2. volatile不具备互斥性
3. Synchronized具备互斥性，也就是只能存在一个线程抢到锁

> 不具备互斥性带来的问题（不能保证原子性）  
1. 原子性 --- VolatileTest2.java
    * 这个例子中，num++自增操作可以分为三个子操作
        * 读取变量原始值（volatile可以保证读取时num值和主内存中num值统一）
        * 进行加1操作
        * 写入工作内存
    * 也就是说自增操作不具备原子性
2. 原子性问题解决 ---- VolatileTest3.java
    * JUC.atomic中原子操作类来封装共享变量
        * AtomicBoolean,AtomicInteger,AtomicReference
    * 这些类都保证了一下两点：
        * 类中变量都用volatile保证内存可见性
        * 使用CSA算法，保证对这些数据的操作具有原子性
        
> CSA算法逻辑的理解  
> CSA ---- Compare and Swap (即比较再交换)
1. 乐观锁和悲观锁  
    * 传统的锁机制，例如Synchronized关键字，代表了Java中悲观锁技术（能够很好的保证线程安全，但性能开销大）  
    * 乐观锁的核心思想是：  
        寄希望于在没有冲突的情况下完成一次更新操作。使用乐观锁更新是会进行冲突检测来判断是否有其他线程干扰，若有则视本次更新操作失败，一般会进行重试
2. Compare and Swap就是典型的乐观锁技术
3. CAS指令在Intel CPU上称为CPMXCHG指令
    * 它的作用是将指定内存地址中内容与所给的某个值相比，如果相等，则将内容替换为指令中提供的新值，如果不等，则更新失败
    * 这一比较并交换的操作是原子的，虽然看上去也包含了（读，比，写），但是CAS是通过硬件实现的原子性的
4. CAS有三个操作数，内存值V、旧的预期值A、要修改的新值B
    * 当且仅当预期值A与内存值V相等时，将内存值V修改为B，否则返回V
    * 否则放弃已经所做的操作，然后重新执行刚才的操作

### JUC里的同步容器类
1. 线程安全的集合对象
    * Vector
    * HashTable
    * StringBuffer
2. 非线程安全的集合对象
    * Arraylist
    * LinkedList
    * HashMap
    * HashSet
    * TreeMap
    * StringBuilder
3. JUC里的同步容器类
    * ConcurrentHashMap
    * ConcurrentSkipListMap
    * CopyOnWriteArrayList
    * CopyOnWriteArraySet
    
### JUC中同步一批线程的行为
1. CountDownLatch 计数器闭锁 --- CountDownLatchDemo.java  
    （阻塞当前线程直到计数器值减为0）  
    * .await() --- 阻塞当前线程
    * .countDown() --- 计数器减1

2. Semaphore 信号量 --- SemaphoreDemo.java
    * .acquire([int])
    * .release([int])
    
3. CyclicBarrier 回环栅栏 --- CyclicBarrierDemo.java  
    * 通过它可以实现让一组线程等待至某一个状态之后再全部同时执行
    * 例：大巴旅游，三个景点，每个景点约定游玩时间，游玩结束一起出发到下一个景点
    * .await() --- 等待一组线程到达某一状态
    * .getNumberWaiting() --- 获取到达等待状态的线程数