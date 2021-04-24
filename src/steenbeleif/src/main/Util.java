package main;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<String> getComplementary(List<String> c1, List<String> c2) {
        if (c1 == c2) {
            System.out.println("JEG ER DEN SAMME");
            return c1;
        }

        List<String> complementary = c1;
        complementary.retainAll(c2);
        return complementary;

    }

}
