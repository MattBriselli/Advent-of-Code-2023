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

    static class Triple {
        private boolean inPath;
        private Direction dirOne;
        private Direction dirTwo;

        Triple(boolean inPath, Direction dirOne, Direction dirTwo) {
            this.inPath = inPath;
            this.dirOne = dirOne;
            this.dirTwo = dirTwo;
        }

        public boolean isInPath() {
            return inPath;
        }

        public boolean connects(Direction newDir) {
            return switch (newDir) {
                case Down -> dirOne == Direction.Up || dirTwo == Direction.Up;
                case Up -> dirOne == Direction.Down || dirTwo == Direction.Down;
                case Left -> dirOne == Direction.Right || dirTwo == Direction.Right;
                case Right -> dirOne == Direction.Left || dirTwo == Direction.Left;
            };
        }

        public Direction getDirTwo() {
            return dirTwo;
        }
    }


    private static final List<List<String>> board = new ArrayList<>();

    private static int startingX = -1;
    private static int startingY = -1;

    private static final List<List<Triple>> pathBoard = new ArrayList<>();

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
        return getLoopSize(Direction.Up, startingY + 1, startingX, 0);
    }

    private static boolean notValidMove(Direction direction, String s) {
        switch (direction) {
            case Down:
                if (!s.equals("F") && !s.equals("7") && !s.equals("|")) {
                    return true;
                }
                return false;
            case Up:
                if (!s.equals("L") && !s.equals("J") && !s.equals("|")) {
                    System.out.println("Wrong char going: Down - " + s);
                    return true;
                }
                return false;
            case Right:
                if (!s.equals("L") && !s.equals("F") && !s.equals("-")) {
                    return true;
                }
                return false;
            case Left:
                if (!s.equals("J") && !s.equals("7") && !s.equals("-")) {
                    return true;
                }
                return false;
        }
        return true;
    }

    private static int getLoopSize(Direction entry, int y, int x, int count) {
        count++;

        if (y == startingY && x == startingX) {
            // We did it!!! Break from the loop
            return count / 2;
        }

        String s = board.get(y).get(x);

        System.out.println("The count is: " + count + " we've got a " + s + " and we came from " + entry.toString());
        switch (entry) {
            case Down:
                if (notValidMove(Direction.Down, s)) {
                    System.out.println("Wrong char going Tail: Down - " + s);
                    return -1;
                }
                switch (s) {
                    case "F":
                        if (board.get(y).size() == x + 1) {
                            // Can't move right
                            System.out.println("Can't move further Right on the F");
                            return -1;
                        }
                        x++;
                        entry = Direction.Left;
                        break;
                    case "7":
                        if (x - 1 < 0) {
                            // Can't move left
                            System.out.println("Can't move further Left on the 7");
                            return -1;
                        }
                        x--;
                        entry = Direction.Right;
                        break;
                    case "|":
                        if (y-1 < 0) {
                            // Can't move up
                            System.out.println("Can't move further Up on the |");
                            return -1;
                        }
                        y--;
                        break;
                    default:
                        return -1;
                }
                break;
            case Up:
                if (notValidMove(Direction.Up, s)) {
                    System.out.println("Wrong char going: Up - " + s);
                    return -1;
                }
                switch (s) {
                    case "L":
                        if (board.get(y).size() == x + 1) {
                            // Can't move right
                            System.out.println("Can't move further Right on the L");
                            return -1;
                        }
                        x++;
                        entry = Direction.Left;
                        break;
                    case "J":
                        if (x - 1 < 0) {
                            // Can't move left
                            System.out.println("Can't move further Left on the J");
                            return -1;
                        }
                        x--;
                        entry = Direction.Right;
                        break;
                    case "|":
                        if (y + 1 == board.size()) {
                            // Can't move down
                            System.out.println("Can't move further Down on the |");
                            return -1;
                        }
                        y++;
                        break;
                    default:
                        return -1;
                }
                break;
            case Right:
                if (notValidMove(Direction.Right, s)) {
                    System.out.println("Wrong char going: Right - " + s);
                    return -1;
                }
                switch (s) {
                    case "L":
                        if (y-1 < 0) {
                            // Can't move up
                            System.out.println("Can't move further Up on the L");
                            return -1;
                        }
                        y--;
                        entry = Direction.Down;
                        break;
                    case "F":
                        if (y + 1 == board.size()) {
                            // Can't move down
                            System.out.println("Can't move further Down on the F");
                            return -1;
                        }
                        y++;
                        entry = Direction.Up;
                        break;
                    case "-":
                        if (x - 1 < 0) {
                            // Can't move left
                            System.out.println("Can't move further Left on the -");
                            return -1;
                        }
                        x--;
                        break;
                    default:
                        return -1;
                }
                break;
            case Left:
                if (notValidMove(Direction.Left, s)) {
                    System.out.println("Wrong char going: Left - " + s);
                    return -1;
                }
                switch (s) {
                    case "J":
                        if (y-1 < 0) {
                            // Can't move up
                            System.out.println("Can't move further Up on the J");
                            return -1;
                        }
                        y--;
                        entry = Direction.Down;
                        break;
                    case "7":
                        if (y + 1 == board.size()) {
                            // Can't move down
                            System.out.println("Can't move further Down on the 7");
                            return -1;
                        }
                        y++;
                        entry = Direction.Up;
                        break;
                    case "-":
                        if (board.get(y).size() == x + 1) {
                            // Can't move right
                            System.out.println("Can't move further Right on the -");
                            return -1;
                        }
                        x++;
                        break;
                    default:
                        return -1;
                }
                break;
        }
        return getLoopSize(entry, y, x, count);
    }
}
