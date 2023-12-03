package main.java.org.adventofcode2023.Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day3/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day3/Test"));

        System.out.println(iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;

        char[][] board = new char[line.length()][];

        int ticker = 0;
        while (line != null) {
            board[ticker] = line.toCharArray();
            ticker++;
            line = reader.readLine();
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char c = board[i][j];
                if (c == '*') {
                    System.out.println("found a * at: " + i + ", " + j);
                    sum += grabSurrounding(board, i, j);
                }
            }

        }
//        Part 1
//        for (int i = 0; i < board.length; i++) {
//            StringBuilder wordNum = new StringBuilder();
//
//            for (int j = 0; j < board[i].length; j++) {
//                char c = board[i][j];
//                int newJ = j;
//
//                if (Character.isDigit(c)) {
//                    while (newJ < board[i].length && Character.isDigit(board[i][newJ])) {
//                        wordNum.append(board[i][newJ]);
//                        newJ++;
//                    }
//                }
//                if (!wordNum.isEmpty()) {
//                    System.out.println(wordNum);
//                    int num = Integer.parseInt(wordNum.toString());
//                    if (connectedToChar(num, board, i, j, newJ)) {
//                        sum += num;
//                    }
//                    System.out.println("New sum is: " + sum);
//                    j = newJ;
//                    wordNum = new StringBuilder();
//                }
//            }
//        }
        return sum;
    }

    private static int grabSurrounding(char[][] board, int row, int col) {
        int total = 1;
        int found = 0;

        int maxX = col == board[0].length ? col : col + 1;
        int minX = col == 0 ? col : col -1;
        int minY = row == 0 ? 0 : row - 1;
        int maxY = row == board.length ? row : row + 1;

        if (maxX == board[0].length) {
            maxX--;
        }

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                char c = board[i][j];
                if (Character.isDigit(c)) {
                    StringBuilder numString = new StringBuilder(Character.toString(c));
                    int tempJ = j - 1;
                    while(tempJ >= 0 && Character.isDigit(board[i][tempJ])) {
                        numString = new StringBuilder(Character.toString(board[i][tempJ])).append(numString);
                        tempJ--;
                    }
                    tempJ = j + 1;
                    while(tempJ < board.length && Character.isDigit(board[i][tempJ])) {
                        numString = new StringBuilder(numString).append(Character.toString(board[i][tempJ]));
                        tempJ++;
                    }
                    j = tempJ;
                    total *= Integer.parseInt(numString.toString());
                    found++;
                }
            }
        }

        if (found < 2) {
            total = 0;
        }
        return total;
    }

    private static boolean connectedToChar(int num, char[][] board, int row, int startCol, int endCol) {
        int minX = startCol == 0 ? 0 : startCol - 1;
        int maxX = endCol >= (board[0].length - 1) ? endCol : endCol + 1;
        int minY = row == 0 ? 0 : row - 1;
        int maxY = row >= (board.length - 1) ? row : row + 1;

        if (maxX == board[0].length) {
            maxX--;
        }

        System.out.println("minX: " + minX + " maxX: " + maxX +  " and minY: " + minY +  " and maxY: " + maxY);

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j < maxX; j++) {
                char c = board[i][j];
                System.out.println("checking: " + c + " at x: " + i +  " and y: " + j);
                if (!Character.isDigit(c) && c != '.') {
                    System.out.println("FOUND!! " + c + " at x: " + i +  " and y: " + j);
                    return true;
                }
            }
        }
        return false;
    }
}