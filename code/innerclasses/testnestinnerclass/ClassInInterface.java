package innerclasses.testnestinnerclass;

/**
 * @创建者 倪军
 * @创建时间 2017/8/13
 * @描述 如果想要某些接口的实现共用某些公共的代码,可以在接口内部使用嵌套类.
 */
public interface ClassInInterface {

    void howdy();
    class Test implements  ClassInInterface{

        @Override
        public void howdy() {
            System.out.println("Howdy");
        }

        public static void show(ClassInInterface inInterface){//这说明接口内的所有成员都是静态和公共的
            inInterface.howdy();
        }

        public static void main(String[] args) {
            new Test().howdy();
        }
    }
}

class Test{
    public static void main(String[] args) {
        ClassInInterface inInterface = new ClassInInterface.Test();
        inInterface.howdy();
        ClassInInterface.Test.show(inInterface);//,传递接口的实例进去,调用接口中的静态方法
    }
}