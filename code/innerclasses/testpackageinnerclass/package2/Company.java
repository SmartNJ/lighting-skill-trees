package innerclasses.testpackageinnerclass.package2;


import innerclasses.testpackageinnerclass.package1.IPrintable;
/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述
 */
public class Company {

    public class Printer implements IPrintable {
        @Override
        public void print(String text) {
            System.out.println(text);
        }
    }
}
