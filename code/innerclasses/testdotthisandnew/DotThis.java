package innerclasses.testdotthisandnew;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述
 */
public class DotThis {

    void f() {
        System.out.println("DotThis f()");
        //new Inner().g();  //StackOverflowError
    }

    public class Inner {
        public DotThis outer() {
            return DotThis.this;
        }

        public void g() {
            f();
        }
    }

    public Inner inner() {
        return new Inner();
    }

    public static void main(String[] args) {
        DotThis dt = new DotThis();
        dt.f();
        System.out.println(dt);

        DotThis.Inner inner = dt.inner();
        DotThis outer = inner.outer();
        outer.f();
        System.out.println(outer);
        inner.g();
    }
}
