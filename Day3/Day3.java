package main.java.org.adventofcode2023.Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3 {

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day3/List"));
        BufferedReader testReader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day3/Test"));

        int total = iterate(testReader);

        System.out.println(total);
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int total = 0;

        while (line != null) {

            // do stuff

            line = reader.readLine();
        }

        return total;
    }
}