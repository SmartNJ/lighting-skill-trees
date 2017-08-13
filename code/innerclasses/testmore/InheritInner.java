package innerclasses.testmore;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述 内部类的继承
 */

class WithInner{
    class Inner{}
}

public class InheritInner  extends WithInner.Inner{

    //因为内部类的创建必须连接到其外部类对象的引用,
    //这个引用必须被初始化,而导出类不存在这个引用
    //所以需要向构造函数传入外部类的对象
    //用这个对象调用 .super() 进行必要的初始化
    public InheritInner(WithInner withInner) {
        withInner.super(); 
    }

//    public InheritInner(){}//Won't  compile

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner inner = new InheritInner(wi);
    }
}
