package innerclasses.testnestinnerclass;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述 多层嵌套类能透明地访问外部类(即使是private)成员,与嵌套多少层并无关系
 */

class MNA {
    private void f() {
        System.out.println("f()");
    }

    class A {
        private void g() {
            System.out.println("g()");
        }

        class B {
            void h() {
                g();
                f();
//                Class<MNA> aClass = MNA.class;
//                MNA mna = MNA.this;
//                Object o = MNA.super;
            }
            //callback()方法返回了内部类对象hook的外围类对象的引用，使用语法 外围类名.this， 注意函数的返回类型！
            MNA callback(){return MNA.this;}
        }
    }
}

public class MultiNestingAccess {

    public static void main(String[] args) {
        MNA.A.B b = new MNA().new A().new B();
        b.h();

    }
}
