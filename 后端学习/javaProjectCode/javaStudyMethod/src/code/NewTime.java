package code;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NewTime {
    public static void main(String[] args){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        System.out.println(localDateTime);
        System.out.println(localDate);
        System.out.println(localTime);

        // 2020-9-30
        LocalDate date = LocalDate.of(2020, 9, 30);
// 14:15:10
        LocalTime time = LocalTime.of(14, 15, 10);
// 将date和time组合成一个LocalDateTime
        LocalDateTime dateTime1 = LocalDateTime.of(date, time);
// 设置 年、月、日、时、分、秒
        LocalDateTime dateTime2 = LocalDateTime.of(2020, 10, 21, 14, 14);

        System.out.println("dateTime1=" + dateTime1);
        System.out.println("dateTime2=" + dateTime2);
    }
}
