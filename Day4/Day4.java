package main.java.org.adventofcode2023.Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day4 {

    public static void main(String[] args) throws IOException {
        //        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day4/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day4/Test"));

        System.out.println(iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;

        while (line != null) {
            // do stuff

            line = reader.readLine();
        }

        return sum;
    }
}
