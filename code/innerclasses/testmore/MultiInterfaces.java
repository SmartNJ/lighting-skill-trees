package innerclasses.testmore;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述 使用内部类来实现接口
 */
interface  A{}
interface  B{}

class X implements  A,B{}

class Y implements A{
    //工厂方法
    B makeB(){
        return new B() {};
    }
}
public class MultiInterfaces {
    static void takesA(A a){}
    static void takesB(B b){}

    public static void main(String[] args) {
        X x = new X();
        Y y = new Y();
        takesA(x);
        takesA(y);
        takesB(x);
        takesB(y.makeB());
    }
}
