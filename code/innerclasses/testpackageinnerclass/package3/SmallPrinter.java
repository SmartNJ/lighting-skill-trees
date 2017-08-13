package innerclasses.testpackageinnerclass.package3;

import innerclasses.testpackageinnerclass.package1.IPrintable;
import innerclasses.testpackageinnerclass.package2.Company;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述
 */
public class SmallPrinter extends Company {

    public static void main(String[] args) {
        SmallPrinter smallPrinter = new SmallPrinter();
        IPrintable printer = smallPrinter.getPrintableThing();
        printer.print("I am a printer,I can print poetry");
    }

    public IPrintable getPrintableThing(){
        Printer printer = new Printer();
        return printer;
    }
}
