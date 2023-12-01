package main.java.org.adventofcode2023.Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day2/List"));
//        BufferedReader testReader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day2/Test"));

        int total = 0;
        total = iterate(reader, total);

        System.out.println(total);
    }

    private static int iterate(BufferedReader reader, int total) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        String line = reader.readLine();

        // Do stuff

        line = reader.readLine();

        return total;
}
