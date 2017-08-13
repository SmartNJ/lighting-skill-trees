package innerclasses.testnestinnerclass;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述
 */
public class TestBed {
    public void f() {
        System.out.println("f()");
    }

    public static class Tester {
        public static void main(String[] args) {
            TestBed tb = new TestBed();
            tb.f();
        }
    }
}
