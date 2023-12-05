package main.java.org.adventofcode2023.Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day4 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day4/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day4/Test"));

        System.out.println(iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;

        HashSet<Integer> winnerSet = new HashSet<>();
        HashMap<Integer, Integer> wonCards = new HashMap<>();
        int ticker = 0;

        while (line != null) {
            int winCount = 0;

            String numbers = line.split(":")[1].trim();
            String[] numbersSet = numbers.split(" | ");

            boolean special = true;

            if (!wonCards.containsKey(ticker)) {
                // No earlier rounds have added cards for this round, so we've just got the one
                wonCards.put(ticker, 1);
            }

            // Let's iterate through and get the win tally's from each Card
            for (String n : numbersSet) {
                if (n.equals("|")) {
                    special = false;
                } else if (!n.isBlank()) {
                    if (special) {
                        winnerSet.add(Integer.parseInt(n));
                    } else {
                        if (winnerSet.contains(Integer.parseInt(n))) {
                            winCount++;
                        }
                    }
                }
            }
            for (int z = 1; z <= winCount; z++) {
                int j = 0;
                int currentMagnifier = wonCards.getOrDefault(ticker, 1);
                while (j < currentMagnifier) {
                    int init = wonCards.getOrDefault(z + ticker, 1);
                    wonCards.put(z + ticker, init + 1);
                    j++;
                }
            }
            winnerSet = new HashSet<>();

            sum += wonCards.getOrDefault(ticker, 1);

            ticker++;
            line = reader.readLine();
        }
        return sum;
    }
}
