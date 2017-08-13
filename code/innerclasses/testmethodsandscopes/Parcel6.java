package innerclasses.testmethodsandscopes;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述 局部内部类主要定义在方法和作用域内
 *  使用理由:
 *  1、实现了某接口,需要创建并返回对其的引用
 *  2、需要解决一个复杂的问题,想创建一个辅助类,但不希望是公共的
 */

public class Parcel6 {

    public static void main1(String[] args) {
        /**
         * 局部内部类不像成员内部类那样,可以先声明再定义,
         *  所以下面的语句是不能通过编译的
         */
        //   InnerClass innerClass = new InnerClass();

        //静态方法内的局部内部类
        class InnerClass {
            public void h() {
                System.out.println("hello world");
            }
        }
        InnerClass innerClass = new InnerClass();
        innerClass.h();
    }

    static boolean isGood = true;

    public static void main(String[] args) {
        /**
         * 作用域内的局部内部类,这并不意味着该类的创建是有条件的,
         * 它其实与别的类一起编译过了.除了作用域的区别外,与普通类一样
         */

        if (isGood) {
            class InnerClass {
                public void h() {
                    System.out.println("hello world");
                }
            }
            InnerClass innerClass = new InnerClass();
            innerClass.h();
        }
    }
}
