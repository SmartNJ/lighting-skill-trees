package innerclasses.testanonymous;

/**
 * @创建者 倪军
 * @创建时间 2017/8/12
 * @描述 工厂方法, 可以优雅地创建对象,并进行初始化
 */
class Factory {

    public Color getColor() {
        return new Color("red");
    }

    public Color color(final String color) {
        return new Color(color) {
            { //初始化代码块
                String c = color;  //实例初始化
            }
        };
    }
}

public class Color {

    private String color;

    public Color(String color) {
        this.color = color;
    }

    public void  showColor(){
        System.out.println(color);
    }

    public static void main(String[] args) {
        Factory factory = new Factory();
        Color green = factory.color("green");
        green.showColor();
    }
}
