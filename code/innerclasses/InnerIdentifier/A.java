package innerclasses.InnerIdentifier;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述  内部类的标识符
 */
// C.class
interface  C {}

//A.class
public class A {
    //A$B.class
    class B {

    }

    public C c(){
        //编译器只会简单地产生一个数字作为其标识符
        //A.1.class
        return  new C() {};
    }

    public static void main(String[] args) {

    }
}
