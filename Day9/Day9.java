package main.java.org.adventofcode2023.Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9 {

    // Part One
    // 1581676777 - Not the right answer

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day9/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day9/Test"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static BigInteger iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        BigInteger sum = BigInteger.ZERO;

        List<BigInteger> differenceList = new ArrayList<>();

        while (line != null) {
            System.out.println(line);

            Map<BigInteger, List<BigInteger>> depthMaps = new HashMap<>();
            BigInteger depth = BigInteger.ZERO;
            String[] lineSplit = line.split(" ");

            for (String s: lineSplit) {
                if (!s.isBlank()) {
                    System.out.println(s);
                    differenceList.add(new BigInteger(s));
                }
            }

            while (!allZero(differenceList)) {
                depthMaps.put(depth, differenceList);
                differenceList = generateDiffList(differenceList);
                depth = depth.add(BigInteger.ONE);
            }
            // For the final all 0 layer
            depth = depth.add(BigInteger.ONE);
            sum = sum.add(getFinalDigit(depthMaps, depth));
            differenceList = new ArrayList<>();

            line = reader.readLine();
        }
        return sum;
    }

    private static BigInteger getFinalDigit(Map<BigInteger, List<BigInteger>> map, BigInteger totalDepth) {
        List<BigInteger> newNumbers = new ArrayList<>();

        for (BigInteger i = totalDepth; i.compareTo(BigInteger.ZERO) > 0; i = i.subtract(BigInteger.ONE)) {
            if (i.compareTo(totalDepth) == 0) {
                // First round
                newNumbers.add(BigInteger.ZERO);
            } else {
                // Prev add
                BigInteger prevAdd = newNumbers.get(totalDepth.subtract(i).subtract(BigInteger.ONE).intValue());
                List<BigInteger> rowList = map.get(i.subtract(BigInteger.ONE));
                for (BigInteger b : rowList) {
                    System.out.println("row: " + b);
                }
                BigInteger finalDigit = rowList.get(rowList.size() - 1);
                System.out.println("final digit is: " + finalDigit + " and we previously added: " + prevAdd);
                newNumbers.add(finalDigit.add(prevAdd));
            }
        }
        System.out.println(newNumbers.get(totalDepth.intValue() - 1));
        return newNumbers.get(totalDepth.intValue() - 1);
    }

    private static boolean allZero(List<BigInteger> list) {
        for (BigInteger b : list) {
            if (b.compareTo(BigInteger.ZERO) != 0) {
                return false;
            }
        }
        return true;
    }

    private static List<BigInteger> generateDiffList(List<BigInteger> list) {
        BigInteger prev = BigInteger.valueOf(-1000);
        List<BigInteger> ret = new ArrayList<>();

        for (BigInteger next : list) {
            if (prev.compareTo(BigInteger.valueOf(-1000)) != 0) {
                // Not equal to -1
                System.out.println("next is: " + next +  ", adding: " + next.subtract(prev));
                ret.add(next.subtract(prev));
            }
            prev = next;
        }
        return ret;
    }
}
