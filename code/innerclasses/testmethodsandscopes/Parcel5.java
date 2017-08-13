package innerclasses.testmethodsandscopes;

import innerclasses.testpackageinnerclass.package1.IPrintable;
/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述 方法内的局部内部类
 */
public class Parcel5 {

    /**
     * printable() 执行完了,并不意味着PPrintable内部类就不可用了.它依旧可用.
     * PPrintable 的作用域仅限于 printable() 方法.
     * 也就是说,你可以在当前目录下任意类中的内部类使用这个相同的名字
     */
    public IPrintable printable(String text) {
        //使用局部内部类而不使用匿名内部类的情况是:  当需要一个已命名的构造器,或者需要重载构造器.
        //当然还有一个理由: 需要不止一个该内部类的对象
        class PPrintable implements IPrintable {
            String text;

            public PPrintable(String text) {
                this.text = text;
                print(this.text);
            }

            @Override
            public void print(String text) {
                System.out.println(text);
            }
        }
        return new PPrintable(text);
    }

    //使用匿名内部类完成相同的功能, 匿名内部类只能用于实例初始化.
    public IPrintable printable1(final String text) {

        return  new IPrintable() {
            { //实例初始化,相当于具名类中的构造器
                print(text);
            }
            @Override
            public void print(String text) {
                System.out.println(text);
            }
        };
    }

//    PPrintable pp = new PPrintable(); //can not access 局部内部类的名字在方法外是不可见的


    public static void main(String[] args) {
        Parcel5 parcel5 = new Parcel5();
        IPrintable printable = parcel5.printable("我是定义在方法内部的局部内部类.");
    }
}
