package main.java.org.adventofcode2023.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day10 {

    enum Direction {
        Down,
        Up,
        Left,
        Right
    }


    private static final List<List<String>> board = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/Test2"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;

        int ticker = 0;
        while (line != null) {
            System.out.println(line);
            board.add(ticker, new ArrayList<>(Arrays.asList(line.split(""))));

            ticker++;
            line = reader.readLine();
        }

        // Finding the starting index
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                String s = board.get(i).get(j);
                if (s.equals("S")) {
                    // We're going to start going down, assuming we've found the top left corner of the maze (progressing left -> right, top -> down).
                    if (board.size() == i + 1) {
                        System.out.println("Can't go down :(");
                        break;
                    }
                    System.out.println("found an F at y:" + i + " x:" + j);
                    int loopSize = getLoopSize(board.get(i + 1).get(j), Direction.Up, i + 1, j, 0);
                    if (loopSize != -1) {
                        return loopSize;
                    }
                }
            }
        }
        System.out.println("we're out");
        return sum;
    }

    private static int getLoopSize(String s, Direction entryDirection, int y, int x, int count) {
//        System.out.println("StartingX is " + startingX + " and X is " + x + ". StartingY is " + startingY + " and Y is " + y);
        if (s.equals("S")) {
            // We did it!!! Break from the loop
            System.out.println("total path length is: " + count);
            return (count + 1) / 2;
        }
        count++;

        System.out.println("The count is: " + count + " we've got a " + s + " and we came from " + entryDirection.toString());
        // Make sure that the current "new" position, is a continuation from the previous one
        switch (entryDirection) {
            case Down:
                if (!s.equals("F") && !s.equals("7") && !s.equals("|")) {
                    return -1;
                }
                switch (s) {
                    case "F":
                        if (board.get(y).size() == x + 1) {
                            // Can't move right
                            return -1;
                        }
                        return getLoopSize(board.get(y).get(x+1), Direction.Left, y, x+1, count);
                    case "7":
                        if (x - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        return getLoopSize(board.get(y).get(x-1), Direction.Right, y, x-1, count);
                    case "|":
                        if (y-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        return getLoopSize(board.get(y-1).get(x), Direction.Down, y-1, x, count);
                    default:
                        return -1;
                }
            case Up:
                if (!s.equals("L") && !s.equals("J") && !s.equals("|")) {
                    System.out.println("Wrong char going: Down - " + s);
                    return -1;
                }
                switch (s) {
                    case "L":
                        if (board.get(y).size() == x + 1) {
                            // Can't move right
                            return -1;
                        }
                        return getLoopSize(board.get(y).get(x+1), Direction.Left, y, x+1, count);
                    case "J":
                        if (x - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        return getLoopSize(board.get(y).get(x-1), Direction.Right, y, x-1, count);
                    case "|":
                        if (y + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        return getLoopSize(board.get(y+1).get(x), Direction.Up, y+1, x, count);
                    default:
                        return -1;
                }
            case Right:
                if (!s.equals("L") && !s.equals("F") && !s.equals("-")) {
                    return -1;
                }
                switch (s) {
                    case "L":
                        if (y-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        return getLoopSize(board.get(y-1).get(x), Direction.Down, y-1, x, count);
                    case "F":
                        if (y + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        return getLoopSize(board.get(y+1).get(x), Direction.Up, y+1, x, count);
                    case "-":
                        if (x - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        return getLoopSize(board.get(y).get(x-1), Direction.Right, y, x-1, count);
                    default:
                        return -1;
                }
            case Left:
                if (!s.equals("J") && !s.equals("7") && !s.equals("-")) {
                    return -1;
                }
                switch (s) {
                    case "J":
                        if (y-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        return getLoopSize(board.get(y-1).get(x), Direction.Down, y-1, x, count);
                    case "7":
                        if (y + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        return getLoopSize(board.get(y+1).get(x), Direction.Up, y+1, x, count);
                    case "-":
                        if (board.get(y).size() == x + 1) {
                            // Can't move right
                            return -1;
                        }
                        return getLoopSize(board.get(y).get(x+1), Direction.Left, y, x+1, count);
                    default:
                        return -1;
                }
        }
        return count;
    }
}
