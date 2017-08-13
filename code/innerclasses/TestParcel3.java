package innerclasses;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述
 *             匿名内部类的实例初始化, 字段初始化.
 */
class Outer2 {
    private int id = 100;

    private String f() {
        return "I am a private method.";
    }

    public Outer h(){
        return new Outer(){
            {
                int i = 2;
                if(i>1){
                    System.out.println("i > 1");
                }
            }
        };
    }
}

public class TestParcel3 {
    public static void main(String[] args) {
        Outer2 outer = new Outer2();
        outer.h();
    }
}
