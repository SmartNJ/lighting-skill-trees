package innerclasses.testmore;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述 使用内部类实现多继承
 */
class D{}

abstract class E{}

class Z extends  D{
    E makeE(){
        return new E() {};//继承E
    }
}
public class MultiImplementation {
    static void takesD(D d){}
    static void takesE(E e){}

    public static void main(String[] args) {
        Z z = new Z();
        takesD(z);
        takesE(z.makeE());
    }
}
