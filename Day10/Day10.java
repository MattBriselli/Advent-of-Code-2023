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

    private static int startingX = -1;
    private static int startingY = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/Test2"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int ticker = 0;

        while (line != null) {
            if (line.contains("S")) {
                startingY = ticker;
                startingX = line.indexOf("S");
            }
            board.add(ticker, new ArrayList<>(Arrays.asList(line.split(""))));

            ticker++;
            line = reader.readLine();
        }

        System.out.println("found the 'S' F at y:" + startingY + " x:" + startingX);
        return getLoopSize(startingY + 1, startingX);
    }

    private static int getLoopSize(int y, int x) {
        int count = 1;
        int moveX = x;
        int moveY = y;
        // We're starting moving down from the initial F.
        Direction dir = Direction.Up;

        while (moveY != startingY || moveX != startingX) {
            String s = board.get(moveY).get(moveX);
            count += 1;

            System.out.println("The count is: " + count + " we've got a " + s + " and we came from " + dir.toString());
            switch (dir) {
                case Down:
                    switch (s) {
                        case "F":
                            moveX += 1;
                            dir = Direction.Left;
                            break;
                        case "7":
                            moveX -= 1;
                            dir = Direction.Right;
                            break;
                        case "|":
                            moveY--;
                            break;
                        default:
                            return -1;
                    }
                    break;
                case Up:
                    switch (s) {
                        case "L":
                            moveX++;
                            dir = Direction.Left;
                            break;
                        case "J":
                            moveX--;
                            dir = Direction.Right;
                            break;
                        case "|":
                            moveY++;
                            break;
                        default:
                            return -1;
                    }
                    break;
                case Right:
                    switch (s) {
                        case "L":
                            moveY--;
                            dir = Direction.Down;
                            break;
                        case "F":
                            moveY++;
                            dir = Direction.Up;
                            break;
                        case "-":
                            moveX--;
                            break;
                        default:
                            return -1;
                    }
                    break;
                case Left:
                    switch (s) {
                        case "J":
                            moveY--;
                            dir = Direction.Down;
                            break;
                        case "7":
                            moveY++;
                            dir = Direction.Up;
                            break;
                        case "-":
                            moveX++;
                            break;
                        default:
                            return -1;
                    }
                    break;
            }
        }
        return count / 2;
    }
}
