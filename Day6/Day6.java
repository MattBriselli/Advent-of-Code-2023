package main.java.org.adventofcode2023.Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day6 {

    public static void main(String[] args) throws IOException {
                BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day6/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day6/Test"));

        System.out.println(iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        List<Integer> timeList = new ArrayList<>();
        List<Integer> distanceList = new ArrayList<>();
        List<Integer> winners = new ArrayList<>();

        while (line != null) {
            System.out.println(line);
            String title = line.split(":")[0];
            String values = line.split(":")[1].trim();

            String[] valueList = values.split(" ");
            for (String v : valueList) {
                if (!v.isBlank()) {
                    if (title.equals("Time")) {
                        timeList.add(Integer.parseInt(v));
                    } else {
                        distanceList.add(Integer.parseInt(v));
                        winners.add(0);
                    }
                }
            }
            line = reader.readLine();
        }

        for (int i = 0; i < timeList.size(); i++) {
            int time = timeList.get(i);
            int distance = distanceList.get(i);

            for (int j = 0; j < time; j++) {
                int speed = (time - j);
                int turnDistance = speed * j;
                System.out.println(turnDistance);
                if (turnDistance > distance) {
                    winners.set(i, winners.get(i) + 1);
                }
            }
        }

        int total = 1;
        for (int w : winners) {
            System.out.println("winner: " + w);
            total *= w;
        }

        return total;
    }
}
