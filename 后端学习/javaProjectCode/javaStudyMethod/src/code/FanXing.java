package code;

import java.util.ArrayList;

public class FanXing {
    public static void main(String[] args){
        ArrayList list = new ArrayList();
        list.add("Hello");
// 获取到Object，必须强制转型为String:
        String first = (String) list.get(0);

        list.add(new Integer(123));
// ERROR: ClassCastException:
        System.out.println(list.get(1));
    }
}
