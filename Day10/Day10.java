package main.java.org.adventofcode2023.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day10 {

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/Test"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;

        while (line != null) {
            // do stuff

            System.out.println(line);

            line = reader.readLine();
        }

        return sum;
    }
}
