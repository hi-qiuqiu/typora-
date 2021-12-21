package classAndObject.demo;

public class Car {
    String name;

    public Engine getEngine() {
        return new Engine();
    }

    private class Engine{
        private void run() {
            System.out.println(name + "的发动机启动了");
        }
    }

    public static void main(String[] args) {
        Car car = new Car();
        car.name = "大笨笨";

        Engine engine = car.getEngine();
        engine.run();
    }
}
