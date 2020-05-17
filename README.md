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
    * 挂起的Blocked（Thread.sleep()、Thread.join())
    * 等待的Blocked（Object.wait()，释放同步锁，等待唤醒)
    * 锁定的Blocked（获取不到同步锁而进入同步阻塞状态）

### 控制线程
