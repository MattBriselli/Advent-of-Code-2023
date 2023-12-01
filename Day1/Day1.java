package main.java.org.adventofcode2023.Day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1 {

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day1/List"));
        BufferedReader testReader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day1/Test"));

        int total = 0;
        total = iterate(testReader, total);

        System.out.println(total);
    }

    private static int iterate(BufferedReader reader, int total) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        String line = reader.readLine();

        while (line != null) {
            list.add(Integer.parseInt(line));
            line = reader.readLine();
            total++;
        }

        return total;
    }
}
