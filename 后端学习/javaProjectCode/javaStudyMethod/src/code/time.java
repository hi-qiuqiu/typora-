package code;

import java.text.SimpleDateFormat;
import java.util.Date;

public class time<t> {

    public static void main(String[] args){
        long time = System.currentTimeMillis();
        System.out.println("time=" + time);

        Date date = new Date();
        System.out.println(date);
        System.out.println(date.toString()); // 转换为String
        System.out.println(date.getTime());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

}
