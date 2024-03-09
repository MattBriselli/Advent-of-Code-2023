package main.java.org.adventofcode2023.Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12 {

    static class Ambiguity {
        int size;
        boolean leftFree;
        boolean rightFree;

        Ambiguity(int size, boolean leftFree, boolean rightFree) {
            this.size = size;
            this.leftFree = leftFree;
            this.rightFree = rightFree;
        }
    }

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day12/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day12/Test"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;

        while (line != null) {
            System.out.println(line);
            String[] score = line.split(" ")[1].split(",");
            List<Integer> pattern = new ArrayList<>();
            for (String s : score) {
                pattern.add(Integer.parseInt(s));
            }
            String[] board = line.split(" ")[0].split("");

            List<Ambiguity> ambList = new ArrayList<>();

            boolean contiguous = false;
            int contiguityCount = 0;
            int ambiguityCount = 0;
            int ticker = 0;

            for (String s : board) {
                if (s.equals("?") || s.equals("#")) {
                    contiguous = true;
                    contiguityCount++;
                } else if (contiguous) {
                    contiguous = false;
                    ambList.add(new Ambiguity(contiguityCount, (ticker - contiguityCount - 1) < 0 || board[ticker - contiguityCount - 1].equals("."), board.length >= ticker + 1 && board[ticker].equals(".")));
                    contiguityCount = 0;
                }
                ticker++;
            }
            System.out.println(ambList.size());
            for (Ambiguity amb : ambList) {
                System.out.println(amb.size + " : " + amb.leftFree + " : " +  amb.rightFree);
            }

            line = reader.readLine();
        }

        return sum;
    }
}
