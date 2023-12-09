package main.java.org.adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DayX {

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/DayX/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/DayX/Test"));

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
