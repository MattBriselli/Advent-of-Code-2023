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

    enum Tile {
        In_Path,
        Not_Squeezable,
        Squeezable
    }

    private static final List<List<String>> board = new ArrayList<>();
    private static final Map<Integer, Map<Integer, Tile>> tileBoard = new HashMap<>();

    private static int startingX = -1;
    private static int startingY = -1;

    private static Set<String> upDownSet = Set.of("F", "J", "|", "L", "7");
    private static Set<String> leftRightSet = Set.of("F", "J", "-", "L", "7");

    private static int count = 0;

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day10/Test4"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int ticker = 0;

        while (line != null) {
            tileBoard.put(ticker, new HashMap<>());
            if (line.contains("S")) {
                startingY = ticker;
                startingX = line.indexOf("S");
            }
            board.add(ticker, new ArrayList<>(Arrays.asList(line.split(""))));

            ticker++;
            line = reader.readLine();
        }

        System.out.println("found the 'S' F at y:" + startingY + " x:" + startingX);
        setupLoop(startingY + 1, startingX);
        return boardTraverser();
    }

    private static int boardTraverser() {
        int countTrapped = 0;

        for (int y = 0; y < board.size(); y++) {
            for (int x = 0; x < board.size(); x++) {
                boolean onPath = tileBoard.get(y).containsKey(x);
                if (!onPath && !escapable(y, x)) {
                    System.out.println("trapped y:" + y + ", x:" + x);
                    countTrapped++;
                }
            }
        }
        return countTrapped;
    }

    private static boolean escapable(int y, int x) {
        if (y < 0 || x < 0 || y >= board.size() || x >= board.get(0).size()) {
            // Fell out of the bounding box
            tileBoard.get(y).put(x, Tile.Not_Squeezable);
            return false;
        }
        Tile tile = tileBoard.get(y).get(x);
        if (tileBoard.get(y).containsKey(x) && (tile == Tile.In_Path || tile == Tile.Not_Squeezable)) {
            // Obviously not squeezable.
            tileBoard.get(y).put(x, Tile.Not_Squeezable);
            return false;
        }
        if (y == 0 || x == 0 || x == board.get(0).size() -1 || y == board.size()) {
            // We're on a boundary!!!
            tileBoard.get(y).put(x, Tile.Squeezable);
            return true;
        }

        // TODO: ensure that we haven't already looked at each of these first {@code tileBoard}
        boolean topLeftUp = squeezable(Direction.Up, y-1, x-1);
        boolean topLeftLeft = squeezable(Direction.Left, y-1, x-1);
        boolean top = squeezable(Direction.Up, y-1, x);
        boolean topRightUp = squeezable(Direction.Up, y-1, x+1);
        boolean topRightRight = squeezable(Direction.Right, y-1, x+1);
        boolean right = squeezable(Direction.Right, y-1, x+1);
        boolean bottomRightDown = squeezable(Direction.Down, y+1, x+1);
        boolean bottomRightRight = squeezable(Direction.Right, y+1, x+1);
        boolean bottom = squeezable(Direction.Down, y+1, x);
        boolean bottomLeftDown = squeezable(Direction.Down, y+1, x-1);
        boolean bottomLeftLeft = squeezable(Direction.Left, y+1, x-1);
        boolean left = squeezable(Direction.Left, y, x+1);

        // Need to check 8 directions, if any one is squeezable, this does NOT count!!!
        return topLeftUp || topLeftLeft || top || topRightUp || topRightRight || right || bottomRightDown || bottomRightRight || bottom || bottomLeftDown || bottomLeftLeft || left;
    }

    private static boolean squeezable(Direction dir, int y, int x) {
        if (y < 0 || x < 0 || y >= board.size() || x >= board.get(0).size()) {
            // Fell out of the bounding box
            tileBoard.get(y).put(x, Tile.Not_Squeezable);
            return false;
        }
        Tile tile = tileBoard.get(y).get(x);
        if (tileBoard.get(y).containsKey(x) && (tile == Tile.In_Path || tile == Tile.Not_Squeezable)) {
            // Obviously not squeezable.
            tileBoard.get(y).put(x, Tile.Not_Squeezable);
            return false;
        }
        if (y == 0 || x == 0 || x == board.get(0).size() -1 || y == board.size()) {
            // We're on a boundary!!!
            tileBoard.get(y).put(x, Tile.Squeezable);
            return true;
        }
        if (tileBoard.get(y).get(x) == Tile.Squeezable) {
            return true;
        }

        String down = (y+1 == board.size()) ? "" : board.get(y+1).get(x);
        String downRight = (y+1 == board.size() || x+1 == board.get(0).size()) ? "" : board.get(y+1).get(x+1);
        String downLeft = (y+1 == board.size() || x-1 <= 0) ? "" : board.get(y+1).get(x-1);
        String up = (y-1 <= 0) ? "" : board.get(y-1).get(x);
        String upRight = (y-1 <= 0 || x+1 == board.get(0).size()) ? "" : board.get(y-1).get(x+1);
        String upLeft = (y-1 <= 0 || x-1 <= 0) ? "" : board.get(y-1).get(x-1);
        String right = (x+1 == board.get(0).size()) ? "" : board.get(y).get(x+1);
        String left = (x-1 <= 0) ? "" : board.get(y).get(x-1);

        switch (dir) {
            case Down:
            case Up:
                // Down Right
                if (validUpDown(down, downRight) && (escapable(y+1, x) || escapable(y+1, x+1)) ||
                        // Down Left
                        validUpDown(down, downLeft) && (escapable(y+1, x) || escapable(y+1, x-1)) ||
                        // Up Right
                        validUpDown(up, upRight) && (escapable(y-1, x) || escapable(y-1, x+1))  ||
                        // Up Left
                        validUpDown(up, upLeft) && (escapable(y-1, x) || escapable(y-1, x-1))) {
                    tileBoard.get(y).put(x, Tile.Squeezable);
                    count++;
                    return true;
                }
                break;
            case Left:
            case Right:
                if (validLeftRight(right, downRight) && (escapable(y, x+1) || escapable(y+1, x+1)) ||
                        // Up Right
                        validLeftRight(right, upRight) && (escapable(y, x+1) || escapable(y-1, x+1)) ||
                        // Down Left
                        validLeftRight(left, downLeft) && (escapable(y, x-1) || escapable(y+1, x-1))  ||
                        // Up Left
                        validLeftRight(left, upLeft) && (escapable(y, x+1) || escapable(y-1, x+1))) {
                    tileBoard.get(y).put(x, Tile.Squeezable);
                    count++;
                    return true;
                }
        }
        return false;
    }

    private static boolean validUpDown(String upDown, String leftRight) {
        return upDownSet.contains(upDown) && upDownSet.contains(leftRight) &&
                !(upDown.equals("F") && !leftRight.equals("7")) &&
                !(upDown.equals("L") && !leftRight.equals("J"));
    }

    private static boolean validLeftRight(String upDown, String leftRight) {
        return leftRightSet.contains(upDown) && leftRightSet.contains(leftRight) &&
                !(upDown.equals("F") && !leftRight.equals("L")) &&
                !(upDown.equals("7") && !leftRight.equals("J"));
    }


    private static void setupLoop(int y, int x) {
        int moveX = x;
        int moveY = y;
        // We're starting moving down from the initial F.
        Direction dir = Direction.Up;

        while (moveY != startingY || moveX != startingX) {
            String s = board.get(moveY).get(moveX);
            tileBoard.get(moveY).put(moveX, Tile.In_Path);

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
                            return;
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
                            return;
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
                            return;
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
                            return;
                    }
            }
        }
    }
}
