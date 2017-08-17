单例模式



实现思路：

一个类能返回对象一个引用(永远是同一个)和一个获得该实例的方法（必须是静态方法，通常使用getInstance这个名称）；当我们调用这个方法时，如果类持有的引用不为空就返回这个引用，如果类保持的引用为空就创建该类的实例并将实例的引用赋予该类保持的引用；同时我们还将该类的构造函数定义为私有方法，这样其他处的代码就无法通过调用该类的构造函数来实例化该类的对象，只有通过该类提供的静态方法来得到该类的唯一实例。

单例模式在多线程的应用场合下必须小心使用。如果当唯一实例尚未创建时，有两个线程同时调用创建方法，那么它们同时没有检测到唯一实例的存在，从而同时各自创建了一个实例，这样就有两个实例被构造出来，从而违反了单例模式中实例唯一的原则。 解决这个问题的办法是为指示类是否已经实例化的变量提供一个互斥锁(虽然这样会降低效率)。



通常单例模式在[Java语言](https://zh.wikipedia.org/wiki/Java%E8%AF%AD%E8%A8%80)中，有两种构建方式：

- 懒汉方式。指全局的单例实例在第一次被使用时构建。
- 饿汉方式。指全局的单例实例在类装载时构建。

饿汉模式：

```
public class Singleton{
  private final static INSTANCE = new Singleton();
  
  //私有构造函数
  private Singleton(){}
  
  //定义公共的构造器
  public void Singleton getInstance(){
    return INSTANCE;
  }
}
```



懒汉模式：

```
public class Singleton{
  private volatile Singleton INSTANCE = null;
  
  //私有构造函数
  private Singleton(){}
  
  //定义公共的构造器
  public static Singleton getInstance(){
    if(INSTANCE == null){
      synchronized(Singleton.class){
      	//当多个线程同时通过了第一个 null 检查，为了避免同时创建新的实例，需要加锁，并再次判断是否为null
        if(INSTANCE == null){
          INSTANCE = new Singleton();
        }
      }
    }
    return INSTANCE;
  }
}
```



ThreadLocal 与 单例模式

```java
class ThreadLocalSingleton{
  private ThreadLocalSingleton(){}
  
  //把ThreadLocal封装在类内部 
  private static ThreadLocal<ThreadLocalSingleton> map 
  				= new ThreadLocal<ThreadLocalSingleton>;
  
  //注：每个线程map.get()得到的都是自己线程中可见的instance，所以不存在多个线程访问同一个变量
  public static /*synchronized*/ ThreadLocalSingleton getThreadInstance(){
      ThreadLocalSingleton instance = map.get();
    if(instance == null){
        instance = new ThreadLocalSingleton();
      	map.set(instance);
    }
  }	
  
  //attributes ...
  //getter&setter ...
}
```

优雅的方式，得到

```
//存入
ThreadLocalSingleton.getThreadInstance().setXxx();
//读取
ThreadLocalSingleton.getThreadInstance().getXxx();
```

