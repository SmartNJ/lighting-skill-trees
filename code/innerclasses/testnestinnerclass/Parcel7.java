package innerclasses.testnestinnerclass;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述 nest class 嵌套类
 */
public class Parcel7 {

    static class Go {
        static class To {

        }
    }

    public static Go go() {
        return new Go();
    }

    public static Go.To to() {
        return new Go.To();
    }

    public static void main(String[] args) {
        Go go = Parcel7.go();
        Go go1 = new Go();
    }
}

