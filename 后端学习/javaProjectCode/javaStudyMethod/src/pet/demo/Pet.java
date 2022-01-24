package pet.demo;

public class Pet<String> {
    private String name;
    private int age;
    protected java.lang.String birthday = "2020-4";

    public Pet(String name, int age){
        this.setName(name);
        this.setAge(age);
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }

    public void eat(){
        System.out.println(this.getName() + "在吃东西");
    }
    protected void shout(){
        System.out.println(this.getName() + "汪汪汪的叫我姐姐~");
    }
}
