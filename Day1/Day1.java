package main.java.org.adventofcode2023.Day1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day1/List"));
//        BufferedReader testReader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day1/Test"));

        int total = iterate(reader);

        System.out.println(total);
    }

    private static int iterate(BufferedReader reader) throws IOException {
        int total = 0;
        String line = reader.readLine();
        int first = -1;
        int last = -1;

        while (line != null) {
            char[] lineArr = line.toCharArray();
            for (int i = 0; i < lineArr.length; i++) {
                char l = lineArr[i];
                if (Character.isDigit(l)) {
                    if (first == -1) {
                        first = Integer.parseInt(Character.toString(l));
                    } else {
                        last = Integer.parseInt(Character.toString(l));
                    }
                } else if (startsWord(l)) {
                    switch (l) {
                        case 'o':
                            if (lineArr.length >= i+2 && lineArr[i+1] == 'n' && lineArr[i+2] == 'e') {
                                if (first == -1) {
                                    first = 1;
                                } else {
                                    last = 1;
                                }
                            }
                        case 't':
                            if (lineArr.length >= i+2 && lineArr[i+1] == 'w' && lineArr[i+2] == 'o') {
                                if (first == -1) {
                                    first = 2;
                                } else {
                                    last = 2;
                                }
                            } else if (lineArr.length >= i+4 && lineArr[i+1] == 'h' && lineArr[i+2] == 'r' && lineArr[i+3] == 'e' && lineArr[i+4] == 'e') {
                            if (first == -1) {
                                first = 3;
                            } else {
                                last = 3;
                            }
                        }
                        case 'f':
                            if (lineArr.length >= i+3 && lineArr[i+1] == 'o' && lineArr[i+2] == 'u' && lineArr[i+3] == 'r') {
                                if (first == -1) {
                                    first = 4;
                                } else {
                                    last = 4;
                                }
                            } else if (lineArr.length >= i+3 && lineArr[i+1] == 'i' && lineArr[i+2] == 'v' && lineArr[i+3] == 'e') {
                                if (first == -1) {
                                    first = 5;
                                } else {
                                    last = 5;
                                }
                            }
                        case 's':
                            if (lineArr.length >= i+2 && lineArr[i+1] == 'i' && lineArr[i+2] == 'x') {
                                if (first == -1) {
                                    first = 6;
                                } else {
                                    last = 6;
                                }
                            } else if (lineArr.length >= i+4 && lineArr[i+1] == 'e' && lineArr[i+2] == 'v' && lineArr[i+3] == 'e' && lineArr[i+4] == 'n') {
                                if (first == -1) {
                                    first = 7;
                                } else {
                                    last = 7;
                                }
                            }
                        case 'e':
                            if (lineArr.length >= i+4 && lineArr[i+1] == 'i' && lineArr[i+2] == 'g' && lineArr[i+3] == 'h' && lineArr[i+4] == 't') {
                                if (first == -1) {
                                    first = 8;
                                } else {
                                    last = 8;
                                }
                            }
                        case 'n':
                            if (lineArr.length >= i+3 && lineArr[i+1] == 'i' && lineArr[i+2] == 'n' && lineArr[i+3] == 'e') {
                                if (first == -1) {
                                    first = 9;
                                } else {
                                    last = 9;
                                }
                            }

                    }
                }
            }
            if (last != -1) {
                total = total + (10 * first) + last;
            } else {
                total = total + (10 * first) + first;
            }
            first = -1;
            last = -1;

            line = reader.readLine();
        }
        return total;
    }
    private static boolean startsWord(char l) {
        return switch (l) {
            case 'o', 't', 'f', 's', 'e', 'n' -> true;
            default -> false;
        };
    }
}
