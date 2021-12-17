package com.company;

public class Main {
    public int minValue(int[] arr){
        int min = arr[0];
        for(int arrValue: arr){
            if(arrValue < min){
                min = arrValue;
            }
        }
        return min;
    }

    public static void main(String[] args) {
	    int age = 24;
        String name = "qiuYing";
        int[] arr = {5,4,1,6};
        Main main1 = new Main();
        int minValue = main1.minValue(arr);

        System.out.println("minValue:" + minValue + '\n');
        System.out.println("age:" + age + '\n' + "name:" + name);
    }
}
