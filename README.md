# 首页



以下包括我在学习过程中的走过的坑和学到的经验，全都记录下来，以便以后回顾。





### 并发

- [并发](concurrent/concurrent-question-answer.md)



- 简要概述以下 yield() 的作用？我们可以依赖 yield() 吗？

  - yield() 是 Thread 类的静态方法，调用它相当于对*线程调度器*（Java 线程机制的一部分，可以将CPU从一个线程转移到另一个线程）的一种建议，它在声明：“我已经执行完生命周期中最重要的部分了，此刻正是切换给其他任务执行一段时间的大好时机。”  注意，这完全是选择性的。也就是说线程调度器并不一定会将听取它的建议。

  ​

- Thread 类的 run() 和 start() 的区别。

  - run() 方法与普通方法并无区别，它没有内在的线程能力。如果直接调用 run() 方法，就只是调用一个方法而已，程序中依然只有一个主线程。要实现线程行为，你必须显式地将一个任务附着到线程上。
  - start() 是新线程执行必需的初始化操作，用它来启动线程，真正实现了多线程运行，这时无需等待run()方法体执行完毕而直接继续执行下面的代码，即start()方法迅速地返回了。注意，调用完 start() 之后，线程是处于就绪状态，并没有运行，然后通过Thread类调用方法run()来完成其运行操作的（注意与直接调用run()方法区别），run()方法运行结束，此线程终止。


- 对于每个Thread正在运行中的任务，垃圾回收器会清除它吗？
  - 不会，每个Thread都“注册”了自己，因此有一个对它的引用，而且在它的任务退出其run()并死亡之前，垃圾回收器无法清除它。


- 简要概述一下 Executor 框架。
  - Java从1.5版本开始，为简化多线程并发编程，引入全新的并发编程包：java.util.concurrent及其并发编程框架（Executor框架）。它的最大优点是把任务的**提交**和**执行**解耦。要执行任务只需把Task描述清楚，然后提交即可。这个Task是这么被执行的，被谁执行的，什么时候执行的，提交的人就不用关心了。
  - 说的官方一点，Executor在客户端和任务执行之间提供了一个间接层；与客户端直接执行任务不同（直接使用Thread），这个中介对象将执行任务。Executor允许你管理异步任务的执行，而无须显式地管理线程的生命周期。
  - 说的具体点，提交一个Callable对象给ExecutorService（如最常用的线程池ThreadPoolExecutor），将得到一个Future对象，调用Future对象的get方法等待执行结果。提交任务获取结果的过程大大简化，调用者直接从提交的地方就可以等待获取执行结果。
  - 部门主管找到管10个人的小团队的项目经理，要他办点事情，部门主管的直接联系人是项目经理，项目经理将这些事情交给手下的码农去做。

```java
// 一个有10个作业线程的线程池，部门主管找到管10个人的小团队的项目经理
ExecutorService manager = Executors.newFixedThreadPool(10);
//提交作业给经理，作业内容封装在Callable中，约定好了输出的类型是String。
String outputs = manager.submit(
  new Callable<String>() {
    public String call() throws Exception 
    {			                
      return "I am a task, which submited by the so called laoda, and run by those anonymous workers";
    }
    //提交后就等着结果吧，到底是手下10个码农（线程）中谁领到任务了，经理是不关心的。
  }).get();

System.out.println(outputs);
```







- 什么是 Executors ？	
  - 正如 Arrays 一样，Executors是管理线程池的工具类。
  - 它有如下四种静态方法可以产生线程池：
    - newCachedThreadPool()：生成一个无界、可以自动回收的线程池。
    - newFixedThreadPool()：生成固定大小的线程池。
    - newSingleThreadExecutor()：确保任意时刻只有唯一的任务在运行，也就不需要在共享资源上处理同步。（当然更好的解决方法是在资源上同步）
    - newScheduledThreadPool()：生成支持周期任务的线程池。



- 简要概述一下ExecutorService 。
  - ExecutorService.submit()有三个重载的方法，
    - `<T> Future<T> submit(Callable<T> task);`  
    - `<T> Future<T> submit(Runnable task, T result);`  
    - `Future<?> submit(Runnable task);`





- 说一说 ExecutorService 的 shutdown 和 shutdownNow() 方法的区别，？

  - shutdown() 方法可以防止新任务被提交给这个 Executor，调用shutdown()方法的线程将继续运行在shutdown()被调用之前提交的所有任务，在所有任务都完成之后才停止。
  - shutdownNow() 可以立即终止同一个executor上的所有任务。具体的，如果在Executor上调用了shutdownNow() ，它将发送一个interrupt()调用给它启动的所有线程。

  ​

- Callable接口和Runnable接口的区别？

  - Runnable接口是执行工作的独立任务，但是它不返回值。
  - Callable接口是一种具有类型参数的泛型，它的类型参数表示的是从 call（而不是run()）中返回的值，并且必须使用 ExecutorService.submit()方法调用它。





- 什么是 Future<V> 接口？

ExecutorService.submit()方法的返回值类型就是Future<V>，这个接口中声明了5个方法。

```java
public interface Future<V> {
    boolean cancel(boolean mayInterruptIfRunning);
    boolean isCancelled();
    boolean isDone();
    V get() throws InterruptedException, ExecutionException;
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

- 下面解释一下每个方法的作用：
  - cancel()：用来取消任务，如果将true传递给cancel()，那么它就会拥有在该线程上调用interrupt()以停止这个线程的权限。cancel也是一种中断由Executor启动的单个线程的方式。如果中断任务成功则返回true，如果中断任务失败则返回false。如果取消已经完成的任务会返回false。如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
  - isCancelled()：表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
  - isDone()：表示任务是否已经完成，若任务完成，则返回true。
  - get()：用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
  - get(long timeout, TimeUnit unit)：用来获取执行结果，有一个超时时间。如果在指定时间内，还没获取到结果，就直接返回null。注意，这里如果超时了，还会抛出java.util.concurrent.TimeoutException异常。

　　也就是说Future提供了三种功能：

　　1）判断任务是否完成；

　　2）能够中断任务；

　　3）能够获取任务执行结果。

​	

- 简述一下FutureTask。

  因为Future只是一个接口，所以是无法直接用来创建对象使用的，因此就有了FutureTask。FutureTask的实现：

  ```
  public class FutureTask<V> implements RunnableFuture<V> {
  }
  ```

  FutureTask实现了RunnableFuture接口，RunnableFuture接口的实现：

  ```
  public interface RunnableFuture<V> extends Runnable, Future<V> {
  }
  ```

  RunnableFuture接口同时继承了Runnable接口和Future接口，也就是说FutureTask既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值。

  FutureTask提供了2个构造器：

  ```
  public FutureTask(Callable<V> callable) {
  }
  public FutureTask(Runnable runnable, V result) {
  }
  ```

  事实上，FutureTask是Future接口的一个唯一实现类。



- 如何使用Callable+Future获取执行结果？







- 如果没有 Callable、Future和FutureTask ，我们要获取执行结果，就必须怎么做？
  - 就必须共享变量或者使用线程通信的方式来达到效果。





- 选用Executor而不是显式调用创建Thread对象的原因有哪些？
  - Executor在客户端和任务执行之间提供了一个间接层；与客户端直接执行任务不同（直接使用Thread），这个中介对象将执行任务。Executor允许你管理异步任务的执行，而无须显式地管理线程的生命周期。
  - 最好不要在构造方法中启动线程。因为，另一个任务有可能在构造器结束之前开始执行，这意味着该任务能够访问处于不稳定状态的对象。因此，优选Executor而不是显示的创建Thread对象。
  - 最好不要通过继承Thread来实现线程，因为在 Java 中只可以单继承。线程并不是任务。线程是驱动任务的。任务是附着在一个线程上，随着线程的执行而执行。因此，任务跟线程其实没有什么关系。





# 共享受限资源



解决共享资源竞争

- 并发模式下解决线程冲突问题采用什么方案？
  - 都是采用*序列化访问共享资源* 的方案。当多个线程对共享资源同时访问的时候，对共享资源加锁，使得同一时间内只有一个任务可以运行这段代码。这种机制也叫做**互斥量**（mutex）。



synchronized

- 解释一下 synchronized 执行流程。
  - 检查锁是否可用，然后获取锁，执行代码，释放锁。


- 概述一下 synchronized 。
  - 在java中，**所有对象都自动含有单一的锁**。当某个任务调用某个对象上标识为 synchronized 的 f() 方法时，其他任务就不能再调用这个对象上标识为 synchronized 的任意方法了，但是这个任务却可以在 f() 方法中嵌套调用其他 synchronized 方法，这是因为一个任务可以多次获得同一个对象的锁。
  - 在第677页有说到：**JVM负责跟踪对象被加锁的次数**。如果一个对象被解锁（锁被完全释放），其计数变为0。当一个任务第一次给对象加锁的时候，计数器变为1，每当这个相同的任务在这个对象上获得锁时（嵌套调用），计数就会递增。只有首先获得了锁的任务才能继续获得锁。每当任务离开一个 synchronized方法，计数递减，当计数为0时，锁被完全释放，其他任务可以获得锁。
  - 在java中，针对每个类，也有一个锁（作为类 Class 对象的一部分）。这样，synchronized static方法可以在类的范围内防止对static数据的并发访问。
  - 每个访问共享资源的方法都必须被同步。
  - 如果要想测试并发产生的问题，将 yield() 方法插入在两个操作读写中间，可以促使其发生。但是在Synchronized标识的方法，则不会有并发问题。

锁

- 简述一下 Lock 对象
  - Lock是显式的的互斥机制。Lock对象必须被显式地创建、锁定和释放。它与內建的锁形式相比（synchronized）代码缺乏优雅性，但是对于某些类型的问题来说，它更加灵活。
  - 注意使用Lock对象来同步时的惯用法是：对lock()的调用之后，紧接着后面是try-finally语句，return 语句必须在try语句中，unlock() 方法必须在finally中。否则如果unlock先执行,就有可能导致将数据暴露给其他任务.



原子性和易变性

- volatile关键字的作用？什么时候使用？有没有其他机制也可以达到这种效果？
  - volatile 具有下列特性：
    - 可视性：对一个volatile变量的写，别的任务总是能读取到最新的的数据。
    - 原子性：对任意单个 volatile 变量的读/写具有原子性，但是自增这种复合操作不具有原子性。
  - 变量的读写都是在内存中发生的。原本一个线程对变量修改过的内容是存储在当前CPU的缓存中，别的任务是无法看到了。如果将一个域设置为volatile，volatile域会立即被写入到主存中。
  - 当然 synchronized 同步（加锁）机制也可以保证这种可视性，它将变量的修改直接写到内存中。
  - 单个任务中任何的写入操作对这个任务来说都是可视的。
  - 当一个域的值依赖它之前的值时（递增），volatile 就无法工作了。如果某个域的值受到其他域的值的限制，那么 volatile 也无法工作。
  - 使用 volatile 的唯一安全的情况是类中只有一个可变的域。原子性+volatile可以保证并发的正确性，但是编码时还是尽量用同步加锁来保证并发的正确性，第一选择应该是**synchronized**关键字。

 

- 简述一下 java 中的原子性？
  - 原子操作是不能被线程调度机制中断的操作，一旦操作开始，那么它一定可以在可能发生的”上下文切换“之前（切换到其他线程）执行完毕。
  - 原子操作可由线程机制来保证其不可中断。 
  - 原子性+volatile可以保证并发的正确性。



- java 中的递增是原子性的吗？
  - 不是。递增自身也需要多个步骤（包括读、写），并且在递增过程中任务可能会线程机制挂起。
  - 如果将一个域设置为volatile，它就会告诉编译器不要做任何移除读取和写入操作的优化。正因为如此，volatile 并不能对递增不是原子性操作这一事实产生影响。



- “原子操作不需要进行同步控制。” 这句话对吗？

  - 不对。原子性不能保证并发代码的正确性。比如，在多核处理器系统，一个任务对某个变量做出的修改可能只是暂时性地存储在本地CPU的缓存当中，而其他线程对该变量的访问只是局限在各自的CPU缓存中，这样导致了不一致性。同步机制强制规定，一个任务做出的修改必须在应用中是可视的。而volatile关键字就确保了应用中的可视性。

  ​


- 什么才属于原子操作呢？
  - 除了long和double之外，对域中的值做赋值和返回操作通常都是原子性的。
  - JVM可以将64位（long和double变量）的读取和写入当做两个分离的32位操作来执行，这其中会引入上下文切换，从而可能导致不正确的结果。如果给 ”long“ 和 ”double“ 加上volatile关键字就可以获得原子性。



- 什么是 Brian 同步规则？
  - 如果你正在写一个变量，它可能接下来将被另一个线程读取，或者正在读取一个上一次已经被另一个线程写过的变量，那么你必须使用同步，并且，读写线程都必须用相同的监视器锁同步。



原子类

[:arrow_up:](#首页)