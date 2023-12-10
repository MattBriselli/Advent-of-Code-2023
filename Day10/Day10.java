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
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/Test3"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int ticker = 0;
        int startX = -1;
        int startY = -1;

        while (line != null) {
            System.out.println(line);
            if (line.contains("S")) {
                startY = ticker;
                startX = line.indexOf("S");
            }
            board.add(ticker, new ArrayList<>(Arrays.asList(line.split(""))));

            ticker++;
            line = reader.readLine();
        }

        System.out.println("found the 'S' F at y:" + startY + " x:" + startX);
        return getLoopSize(Direction.Up, Direction.Left,startY + 1, startX, startY, startX + 1, 0);
    }

    private static boolean validMove(Direction direction, String s) {
        switch (direction) {
            case Down:
                if (!s.equals("F") && !s.equals("7") && !s.equals("|")) {
                    return false;
                }
                return true;
            case Up:
                if (!s.equals("L") && !s.equals("J") && !s.equals("|")) {
                    System.out.println("Wrong char going: Down - " + s);
                    return false;
                }
                return true;
            case Right:
                if (!s.equals("L") && !s.equals("F") && !s.equals("-")) {
                    return false;
                }
                return true;
            case Left:
                if (!s.equals("J") && !s.equals("7") && !s.equals("-")) {
                    return false;
                }
                return true;
        }
        return false;
    }

    private static int getLoopSize(Direction headEntry, Direction tailEntry, int headY, int headX, int tailY, int tailX, int count) {
        count++;
        
        if (headY == tailY && headX == tailX) {
            // We did it!!! Break from the loop
            return count;
        }

        String headS = board.get(headY).get(headX);
        String tailS = board.get(tailY).get(tailX);

        System.out.println("The count is: " + count + " we've got a " + tailS + " Tail and we came from " + tailEntry.toString());
        switch (tailEntry) {
            case Down:
                if (!validMove(Direction.Down, tailS)) {
                    System.out.println("Wrong Tail char going Tail: Down - " + tailS);
                    return -1;
                }
                switch (tailS) {
                    case "F":
                        if (board.get(tailY).size() == tailX + 1) {
                            // Can't move right
                            return -1;
                        }
                        tailX++;
                        tailEntry = Direction.Left;
                        break;
                    case "7":
                        if (tailX - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        tailX--;
                        tailEntry = Direction.Right;
                        break;
                    case "|":
                        if (tailY-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        tailY--;
                        tailEntry = Direction.Down;
                        break;
                    default:
                        return -1;
                }
                break;
            case Up:
                if (!validMove(Direction.Up, tailS)) {
                    System.out.println("Wrong Tail char going: Up - " + tailS);
                    return -1;
                }
                switch (tailS) {
                    case "L":
                        if (board.get(tailY).size() == tailX + 1) {
                            // Can't move right
                            return -1;
                        }
                        tailX++;
                        tailEntry = Direction.Left;
                        break;
                    case "J":
                        if (tailX - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        tailX--;
                        tailEntry = Direction.Right;
                        break;
                    case "|":
                        if (tailY + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        tailY++;
                        tailEntry = Direction.Up;
                        break;
                    default:
                        return -1;
                }
                break;
            case Right:
                if (!validMove(Direction.Right, tailS)) {
                    System.out.println("Wrong Tail char going: Right - " + tailS);
                    return -1;
                }
                switch (tailS) {
                    case "L":
                        if (tailY-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        tailY--;
                        tailEntry = Direction.Down;
                        break;
                    case "F":
                        if (tailY + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        tailY++;
                        tailEntry = Direction.Up;
                        break;
                    case "-":
                        if (tailX - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        tailX--;
                        tailEntry = Direction.Right;
                        break;
                    default:
                        return -1;
                }
                break;
            case Left:
                if (!validMove(Direction.Left, tailS)) {
                    System.out.println("Wrong Tail char going: Left - " + tailS);
                    return -1;
                }
                switch (tailS) {
                    case "J":
                        if (tailY-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        tailY--;
                        tailEntry = Direction.Down;
                        break;
                    case "7":
                        if (tailY + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        tailY++;
                        tailEntry = Direction.Up;
                        break;
                    case "-":
                        if (board.get(tailY).size() == tailX + 1) {
                            // Can't move right
                            return -1;
                        }
                        tailX++;
                        tailEntry = Direction.Left;
                        break;
                    default:
                        return -1;
                }
                break;
        }
        switch (headEntry) {
            case Down:
                if (!validMove(Direction.Down, headS)) {
                    System.out.println("Wrong Head char going: Down - " + headS);
                    return -1;
                }
                switch (headS) {
                    case "F":
                        if (board.get(headY).size() == headX + 1) {
                            // Can't move right
                            return -1;
                        }
                        headX++;
                        headEntry = Direction.Left;
                        break;
                    case "7":
                        if (headX - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        headX--;
                        headEntry = Direction.Right;
                        break;
                    case "|":
                        if (headY-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        headY--;
                        headEntry = Direction.Down;
                        break;
                    default:
                        return -1;
                }
                break;
            case Up:
                if (!validMove(Direction.Up, headS)) {
                    System.out.println("Wrong Head char going: Up - " + headS);
                    return -1;
                }
                switch (headS) {
                    case "L":
                        if (board.get(headY).size() == headX + 1) {
                            // Can't move right
                            return -1;
                        }
                        headX++;
                        headEntry = Direction.Left;
                        break;
                    case "J":
                        if (headX - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        headX--;
                        headEntry = Direction.Right;
                        break;
                    case "|":
                        if (headY + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        headY++;
                        headEntry = Direction.Up;
                        break;
                    default:
                        return -1;
                }
                break;
            case Right:
                if (!validMove(Direction.Right, headS)) {
                    System.out.println("Wrong Head char going: Right - " + headS);
                    return -1;
                }
                switch (headS) {
                    case "L":
                        if (headY-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        headY--;
                        headEntry = Direction.Down;
                        break;
                    case "F":
                        if (headY + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        headY++;
                        headEntry = Direction.Up;
                        break;
                    case "-":
                        if (headX - 1 < 0) {
                            // Can't move left
                            return -1;
                        }
                        headEntry = Direction.Right;
                        headX--;
                    default:
                        return -1;
                }
                break;
            case Left:
                if (!validMove(Direction.Left, headS)) {
                    System.out.println("Wrong Head char going: Left - " + headS);
                    return -1;
                }
                switch (headS) {
                    case "J":
                        if (headY-1 < 0) {
                            // Can't move up
                            return -1;
                        }
                        headY--;
                        headEntry = Direction.Down;
                        break;
                    case "7":
                        if (headY + 1 == board.size()) {
                            // Can't move down
                            return -1;
                        }
                        headY++;
                        headEntry = Direction.Up;
                        break;
                    case "-":
                        if (board.get(headY).size() == headX + 1) {
                            // Can't move right
                            return -1;
                        }
                        headX++;
                        headEntry = Direction.Left;
                        break;
                    default:
                        return -1;
                }
                break;
        }
        return getLoopSize(headEntry, tailEntry, headY, headX, tailY, tailX, count);
    }
}
