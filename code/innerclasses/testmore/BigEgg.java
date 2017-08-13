package innerclasses.testmore;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述 覆盖内部类
 */
class Egg {
    public Egg() {
        System.out.println("New Egg");  //2
    }

    protected class Yolk {
        public Yolk() {
            System.out.println("Egg.Yolk()");// 1  3
        }

        public void f() {
            System.out.println("Egg.Yolk.f()");
        }
    }

    public void insertYolk(Yolk yolk) {
        this.yolk = yolk;
    }

    private Yolk yolk = new Yolk();

    public void g() {
        yolk.f();
    }
}

public class BigEgg extends Egg {
    public BigEgg() {
        insertYolk(new Yolk());
    }
    //这样是无法覆盖父类中的内部类的,需要使用下面的方式
    //    public class Yolk{
    //        public Yolk(){
    //            System.out.println("BigEgg.Yolk()");
    //        }
    //    }

    public class Yolk extends Egg.Yolk {
        public Yolk() {
            System.out.println("BigEgg.Yolk()"); //4
        }

        public void f() {
            System.out.println("BigEgg.Yolk.f()");//5
        }
    }

    /**
     * new BigEgg() ,首先执行父类的构造函数,
     */
    public static void main(String[] args) {
        Egg egg = new BigEgg();
        egg.g();
    }
}
/**************************
 * output:
 * Egg.Yolk()
 * New Egg
 * Egg.Yolk()
 * BigEgg.Yolk()
 * BigEgg.Yolk.f()
 **************************/