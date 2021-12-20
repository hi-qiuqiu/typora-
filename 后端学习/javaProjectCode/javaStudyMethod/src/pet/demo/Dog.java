package pet.demo;

import javax.swing.*;

class Dog extends Pet {
    public Dog(String name, int age){
        super(name, age);
        System.out.println(super.birthday);
    }

    public void eat(){
        super.eat();
        System.out.println("吃完啦");
    }

    public static void main(String[] args){
        Dog jiLi = new Dog("吉利", 4);
        jiLi.shout();
        System.out.println(jiLi.birthday);
    }
}
