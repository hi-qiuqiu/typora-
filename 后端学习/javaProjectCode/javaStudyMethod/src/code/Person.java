package code;

public class Person {
    public static void main(String[] args){
        Integer num = new Integer(3);
        Integer num1 = new Integer("38");
        byte numByte = num1.byteValue();

        System.out.println("num1=" + num1);
        System.out.println("numByte=" + num1.byteValue());
    }
}
