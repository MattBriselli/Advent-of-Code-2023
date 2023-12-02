package main.java.org.adventofcode2023.Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Day2 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day2/List"));
//        BufferedReader testReader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day2/Test"));

        int total = iterate(reader);

        System.out.println(total);
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int minRed = -1;
        int minGreen = -1;
        int minBlue = -1;

        int redStock = 0;
        int greenStock = 0;
        int blueStock = 0;
        int gameTotal = 0;

        // Do stuff

        while (line != null) {
            System.out.println(line);
            String[] rounds = line.split(":")[1].split(",");

            for (String round : rounds) {
                String[] games = round.split(";");
                for (String game : games) {
                    game = game.trim();
                    int number = Integer.parseInt(game.split(" ")[0]);
                    String color = game.split(" ")[1];
                    switch (color) {
                        case "blue":
                        case "blue,":
                            blueStock += number;
                            break;
                        case "red":
                        case "red,":
                            redStock += number;
                            break;
                        case "green":
                        case "green,":
                            greenStock += number;
                            break;
                    }
                    System.out.println(blueStock + " : " + greenStock + " : " + redStock);
                    if (minBlue < blueStock) {
                        minBlue = blueStock;
                    }
                    if (minRed < redStock) {
                        minRed = redStock;
                    }
                    if (minGreen < greenStock) {
                        minGreen = greenStock;
                    }

                    redStock = 0;
                    greenStock = 0;
                    blueStock = 0;
                }
            }
            System.out.println(minBlue + " : " + minGreen + " : " + minRed);
            gameTotal += (minBlue * minGreen * minRed);

            minBlue = 0;
            minRed = 0;
            minGreen = 0;

            line = reader.readLine();
        }

        return gameTotal;
    }
}
