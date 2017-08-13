package innerclasses.testdotthisandnew;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述 去掉main方法后的数字运行
 */
public class DotNew {

    public class Inner {  //非静态
        public void f() {
            System.out.println("hello world");
        }
    }

    /**
     *    创建成员内部类实例必须要有外部类的的实例
     *    通过这个实例才能创建成员内部类的实例
     */
    public static void main1(String[] args) {
        //can  not be referenced from a static context
//        DotNew.Inner i=new DotNew.Inner();
        DotNew dn = new DotNew();
        dn.other();
    }

    //非静态方法
    public void other() {
        DotNew dn = new DotNew();
        DotNew.Inner inner = new DotNew.Inner();
        inner.f();
    }

    /**********************************************************************/
    /**
     * 静态成员内部类,不需要对外部类对象的引用即可创建实例
     */
    public static class Inner2 {
        public void f() {
            System.out.println("hello world");
        }
    }

    public static void main2(String[] args) {
        DotNew.Inner2 inner = new DotNew.Inner2();
        inner.f();
    }

    /**********************************************************************/

    public class Inner3 {
        public void f() {
            System.out.println("hello world");
        }
    }

    /**
     * 在拥有外部类对象之前是不可能创建内部类对象的.因为内部类对象
     * 会暗暗地连接到它的外部类对象上
     */
    public static void main3(String[] args) {
        DotNew dn = new DotNew();
        DotNew.Inner3 inner = dn.new Inner3();  // .new 语法
        inner.f();
    }
}

/**
 * 在外部其他的类中,创建DotNew类中内部类的实例.
 */
class outOtherClass {
    public static void main4(String[] args) {
        DotNew dn = new DotNew();
        DotNew.Inner3 inner = dn.new Inner3();  // 还是 .new 语法
        inner.f();
    }

    /**
     * Inner  is not static so it requires an instance of the outer class.
     * The simplest solution is to make Inner and any nested class static if you can.
     * I would also make any fields final or static final that you can as well.
     */
    public static void main(String[] args) {
        //is not an enclosing class
        // DotNew.Inner inner1 = new DotNew.Inner();

    }
}
