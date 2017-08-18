# Error Handling with Exceptions



关键字：`Exception Handler`   `Exception condition`    `throwing an exception`  `guarded region` 

`termination `   `resumption`  `exception specificaion`  





> The basic philosophy of Java is that “badly formed code will not be run”.



- 使用好异常的好处
  - Java 能在编译阶段就发现错误，即在你尝试运行程序之前。
  - 使用异常能减低错误处理代码的复杂度，不必在方法调用处进行检查，只需在一个地方处理，这也叫做 “Exception Handler”。
  - 把 “正常执行” 和 “出了问题” 的代码分离开来，使阅读、编写和调试更加有效率。




- Java 有三种异常
  - [非受检异常](#UncheckedException)
  - [受检异常](#CheckedException)
  - [错误](#Error)






###  UncheckedException

`Error`，`RuntimeException`及其子类都属于`UncheckedException`

RuntimeException在默认情况下会得到自动处理。所以通常用不着捕获RuntimeException。

RuntimeException 是那些可能在 Java 虚拟机正常运行期间抛出的异常的超类。可能在执行方法期间抛出但未被捕获的 RuntimeException  的任何子类都无需在 throws 子句中进行声明。

- 常见的uncheckedExcepiton。
  - Java.lang.ArithmeticException
  - Java.lang.ArrayStoreExcetpion
  - Java.lang.ClassCastException
  - Java.lang.EnumConstantNotPresentException
  - Java.lang.IllegalArgumentException
  - Java.lang.IllegalThreadStateException
  - Java.lang.IllegalThreadStateException
  - Java.lang.NumberFormatException
  - Java.lang.IllegalMonitorStateException
  - Java.lang.IllegalStateException
  - Java.lang.IndexOutOfBoundsException
  - Java.lang.ArrayIndexOutOfBoundsException
  - Java.lang.StringIndexOutOfBoundsException
  - Java.lang.NegativeArraySizeException
  - Java.lang.NullPointerException
  - Java.lang.SecurityException
  - Java.lang.TypeNotPresentException
  - Java.lang.UnsupprotedOperationException

并未全部概括。





### CheckedException

除了Runtim`Error`，`RuntimeException`及其子类以外的异常，都属于checkedException，它们都在java.lang库内部定义。Java编译器要求程序必须捕获或声明抛出这种异常。

- 一个方法必须通过throws语句在方法的声明部分说明它可能抛出但并未捕获的所有checkedException。
  - Java.lang.ClassNotFoundException
  - Java.lang.CloneNotSupportedException      clone操作不支持
  - Java.lang.IllegalAccessException     没有访问权限
  - Java.lang.InterruptedException     并发中断异常
  - Java.lang.NoSuchFieldException     没有此字段
  - Java.lang.NoSuchMetodException  没有此方法

  ​

### Error

这些是应用程序外部的特殊错误，并且应用程序通常无法预期或恢复。假设应用程序成功打开文件进行输入，但由于硬件或系统故障而无法读取该文件。不成功的读取将会抛出`java.io.IOError`。







- Exception

  ​        获取详细信息

  - String getMessage( )

  - String getLocalizedMessage( )
  - String toString( )
    打印 Throwable 的调用栈轨迹
  - void printStackTrace( )
  - voidprintStackTrace(PrintStream)
  - voidprintStackTrace(java.io.PrintWriter)




  - 轨迹栈

  e.getStackTrace 会返回一个由轨迹栈中的元素所组成的数组，每个元素就是栈中的一帧，元素 0  是栈顶元素，也就是异常抛出的地方。最后一个元素就是调用序列中的第一个方法调用。



- 重新抛出异常

  在捕获异常之后，你可以不作处理，再次抛出将它交给上一层，但是直接抛出捕获的的 e ，e 中包含的还是原来抛出点的信息。可以使用 e.fillInStackTrace() 将当前调用栈信息填入原来的异常对象 e。 注：这么做会将原始的异常信息丢失。 



- 清理异常对象

  异常对象都是用 new 在堆上创建对象，垃圾回收期会自动把他们清理掉。



- 异常链

  e.initCause(Exception) 可以将异常串起来，相当于把传入的异常对象压入栈顶。






- 如果不捕获 RuntimeException 会怎么样？

  - 答：异常会直接到达main()，而且在程序退出时将调用异常的printStackTrace()方法。

    因为RuntimeException代表的是编程错误，是无法预料到的错误，比如在外部传入null引用到你的程序中。我们在程序中还是要注意检查这些错误，比如数组越界、类型转换、非法状态等。





- 什么情况下 finally 语句块不会执行？
  - 答：一种情况是 try 语句块没有被执行，在之前就返回了。第二中情况是 try 中有 System.exit(0) ，Java 虚拟机一旦停止，整个程序直接退出。



- 如果在 try 或者在 catch 中有return 语句，finally块还会执行吗？
  - 答：会，**finally语句是在return语句执行之后，return返回之前执行**。finally常用来关闭和清理资源。






练习题：

---

- **Question**: Match each situation in the first list with an item in the second list.

  a、int[] A;
  ​      A[0] = 0;
  b、The JVM starts running your program, but the JVM can't find the Java platform classes. (The Java platform classes reside in classes.zip or rt.jar.)
  c、A program is reading a stream and reaches the end of stream marker.
  d、Before closing the stream and after reaching the end of stream marker, a program tries to read the stream again.

1. error
2. checked exception
3. compile error
4. no exception


- **Answer**
  - a. compile error，编译器直接 "might not have been intialized"
  - b. error
  - c. no exception
  - d. checked exception 





- **Question**: Will this code compile?

  ```
  try {

  } catch (Exception e) {
     
  } catch (ArithmeticException a) { 
      
  }
  ```

-  **Answer** : compile error





- Exercise:

   Modify the following cat method so that it will compile:

  ```
  public static void cat(File file) {
      RandomAccessFile input = null;
      String line = null;

      try {
          input = new RandomAccessFile(file, "r");
          while ((line = input.readLine()) != null) {
              System.out.println(line);
          }
          return;
      } finally {
          if (input != null) {
              input.close();
          }
      }
  }
  ```

  **Answer:** The code to catch exceptions is shown in bold:

  ```
  public static void cat(File file) {
      RandomAccessFile input = null;
      String line = null;

      try {
          input = new RandomAccessFile(file, "r");
          while ((line = input.readLine()) != null) {
              System.out.println(line);
          }
          return;
      } catch(FileNotFoundException fnf) {
          System.err.format("File: %s not found%n", file);
      } catch(IOException e) {
          System.err.println(e.toString());
      } finally {
          if (input != null) {
              try {
                  input.close();
              } catch(IOException io) {
              }
          }
      }
  }
  ```