package code;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamStudy {
    public static void main(String[] args) {
        List stringList = new ArrayList();
        stringList.add("hello");
        stringList.add("qiu");
        stringList.add("ying");

        Stream stream = stringList.stream();
        Stream personStream = stringList.parallelStream();

        System.out.println("stream=" + stream);
        System.out.println("personStream=" + personStream);
    }
}
