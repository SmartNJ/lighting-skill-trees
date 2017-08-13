package innerclasses;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述 含有 private 元素的外部类,其中创建一个内部类,
 * 它有个方法可以修改外部类的域,可以调用外部类的方法
 * 再在外部类中创建此内部类的对象,并调用它的方法.
 */
class Outer {
    private int id = 100;

    private String f() {
        return "I am a private method.";
    }

    //    成员内部类可以是静态的
    private /*static*/ class Inner {
        /**
         * 如果上面 Inner 成员内部类不定义成静态的
         * 那么edit() 也不能定义成静态的
         */
        private /*static*/ void edit() {//inner class cannot have static declarations
            Outer outer = new Outer();
            outer.id = 200;
            System.out.println("I am a private variable, I've been changed.");
            System.out.println(outer.f());
        }
    }

    void outerEdit() {
        /**
         *      外部类不可以直接访问成员内部类,
         *      只能通过实例化内部类对象进行访问.
         */
        Inner inner = new Inner();
        inner.edit();
    }


    /**
     * 静态成员方法可以调用静态的内部类中的静态方法
     * 去掉上面static注释即可.
     */
    static void g() {
        //            Outer.Inner.edit();
    }
}

public class TestParcel2 {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.outerEdit();
//        Outer.g();
    }
}
