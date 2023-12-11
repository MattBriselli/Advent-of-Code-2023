package main.java.org.adventofcode2023.Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day11 {

    // Part one:
    // 8873506 - not right

    // Part two:
    // 82000210 - not right
    // 82000292 - not right
    // 177713698 - not right

    record Coord(int x, int y) { }

    private static final List<Coord> starList = new ArrayList<>();
    private static final Set<Integer> blankCols = new HashSet<>();
    private static final Set<Integer> blankRows = new HashSet<>();

    private static final List<List<String>> board = new ArrayList<>();

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day11/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day11/Test"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;
        int ticker = 0;

        while (line != null) {
            System.out.println(line);
            List<String> row = new ArrayList<>(Arrays.asList(line.split("")));
            board.add(ticker, row);

            ticker++;
            line = reader.readLine();
        }

        for (int i = 0; i < board.size(); i++) {
            boolean blankRow = true;
            for (int j = 0; j < board.get(0).size(); j++) {
                if (board.get(i).get(j).equals("#")) {
                    blankRow = false;
                    starList.add(new Coord(j, i));
                }
            }
            if (blankRow) {
                blankRows.add(i);
            }
        }

        for (int i = 0; i < board.get(0).size(); i++) {
            boolean blankCol = true;
            for (int j = 0; j < board.size(); j++) {
                if (board.get(j).get(i).equals("#")) {
                    blankCol = false;
                }
            }
            if (blankCol) {
                blankCols.add(i);
            }
        }

        for (int i = 0; i < starList.size(); i++) {
            for (int j = i + 1; j < starList.size(); j++) {
                Coord first = starList.get(i);
                Coord second = starList.get(j);

                int lowerY = Math.min(second.y, first.y);
                int higherY = Math.max(second.y, first.y);
                int lowerX = Math.min(second.x, first.x);
                int higherX = Math.max(second.x, first.x);
                int addY = 0;
                int addX = 0;

                for (int iNext = lowerY; iNext <= higherY; iNext++) {
                    if (blankRows.contains(iNext)) {
                        addY++;
                    }
                }

                for (int iNext = lowerX; iNext <= higherX; iNext++) {
                    if (blankCols.contains(iNext)) {
                        addX++;
                    }
                }

                sum += Math.abs(second.y - first.y) + Math.abs(second.x - first.x) + (addY * (1000000)) + (addX  * (1000000));
            }
        }

        return sum;
    }
}
