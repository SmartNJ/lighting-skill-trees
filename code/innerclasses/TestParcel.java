package innerclasses;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述
 * 测试:
 *  1、protected 作用域
 *  2、尝试将内部类向上转型(upcasting)
 *  3、外部类能否访问private内部类
 *  4、外部类能否访问内部类的private元素
 */


interface  Destination{
    String readLabel(); //接口的所有成员自动被设置为public
}

interface Contents {
    int  value();
}

class Parcel{
       private class  PContents implements  Contents{ //private
        private int i = 100;

        @Override
        public int value() {
            return i;
        }
    }

    /**
     * 子类及其包中的类可访问
     */
    protected  class  PDestination implements  Destination{ //protected
        private String label ;
        @Override
        public String readLabel() {
            return label;
        }

        public PDestination(String label) {
            this.label = label;
        }
    }

    public PContents contents(){
        return new PContents();
    }

    public PDestination destination(String  label){
        return new PDestination(label);
    }
}

public class TestParcel {

    public static void main(String[] args) {
        Parcel  parcel =  new Parcel();
        //test1
        Parcel.PDestination destination = parcel.new PDestination("hello world");//同一包下
        System.out.println(destination.readLabel());

        //test2
        Contents contents = parcel.contents(); //向上转型
        System.out.println(contents.value());


        //test3
        Destination destination1 = parcel.destination("good news");
        System.out.println(destination1.readLabel());

        //test4
        //外部类不能访问private内部类
        // Contents contents1 =parcel.new PContents(); //has private access in    "innerclasses.Parcel"

        //test5
        //外部类不能访问非内部类的 private元素
        //Contents contents1 =parcel.new PContents();
        //contents1.i;  //cannot resolve symbol i
    }
}
