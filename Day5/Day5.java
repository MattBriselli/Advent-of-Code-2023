package main.java.org.adventofcode2023.Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day5 {

    private static class Triple {
        private final BigInteger rangeStart;
        private final BigInteger rangeEnd;
        private final BigInteger sumMe;

        Triple(BigInteger rangeStart, BigInteger rangeEnd, BigInteger sumMe) {
            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
            this.sumMe = sumMe;
        }

        public BigInteger getRangeStart() {
            return rangeStart;
        }

        public BigInteger getRangeEnd() {
            return rangeEnd;
        }

        public BigInteger getSumMe() {
            return sumMe;
        }
    }

    private static final Set<BigInteger> seedSet = new HashSet<>();

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day5/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day5/Test"));

        Map<String, Map<String, List<Triple>>> map = iterate(reader);

        System.out.println(getMinSeedLocation(map));
//        System.out.println(map.get("soil").get("fertilizer").getOrDefault(81, 81));
    }

    private static Map<String, Map<String, List<Triple>>> iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        // First key: From
        // Second Key: To
        // Maps from a-to-b
        Map<String, Map<String, List<Triple>>> seedTomap = new HashMap<>();
        String currentFrom = "";
        String currentTo = "";

        while (line != null) {
            System.out.println(line);
            if (line.contains("seeds:")) {
                String seedString = line.split(": ")[1];
                String[] seedList = seedString.split(" ");
                for (String seed : seedList) {
                    seedSet.add(BigInteger.valueOf(Integer.parseInt(seed)));
                }
            } else if (line.contains("-to-")) {
                currentFrom = line.split("-to")[0];
                currentTo = line.split("-to-")[1].split(" ")[0];
                if (!seedTomap.containsKey(currentFrom)) {
                    seedTomap.put(currentFrom, new HashMap<>());
                }
                if (!seedTomap.get(currentFrom).containsKey(currentTo)) {
                    seedTomap.get(currentFrom).put(currentTo, new ArrayList<>());
                }
            } else if (line.isBlank()) {
                currentTo = "";
                currentFrom = "";
            } else {
                // processessing numbers!!!
                String[] numbers = line.split(" ");
                BigInteger firstNum = BigInteger.valueOf(-1);
                BigInteger secondNum = BigInteger.valueOf(-1);
                BigInteger range = BigInteger.valueOf(-1);

                for (int i = 0; i < numbers.length; i++) {
                    int num = Integer.parseInt(numbers[i]);
                    if (i == 0) {
                        firstNum = BigInteger.valueOf(num);
                    } else if (i == 1) {
                        secondNum = BigInteger.valueOf(num);
                    } else {
                        range = BigInteger.valueOf(num);
                    }
                }
                Triple newTrip = new Triple(secondNum, secondNum.add(range).subtract(BigInteger.ONE), firstNum.subtract(secondNum));
                seedTomap.get(currentFrom).get(currentTo).add(newTrip);
            }
            line = reader.readLine();
        }

        return seedTomap;
    }

    private static BigInteger getMinSeedLocation(Map<String, Map<String, List<Triple>>> map) {
        BigInteger minSeedLocation = BigInteger.valueOf(-1);

        List<BigInteger> seedList = seedSet.stream().toList();
        for (BigInteger seed : seedList) {
            Map<String, List<Triple>> seedMap = map.get("seed");
            System.out.println("Seed "+  seed);
            for (String key : seedMap.keySet().stream().toList()) {
                BigInteger seedLocation = getSeedLocation(seed, "seed", key, map);

                System.out.println("Seed "+  seed +" location is: " + seedLocation);
                if (minSeedLocation.equals(BigInteger.valueOf(-1)) || seedLocation.compareTo(minSeedLocation) <= 0) {
                    minSeedLocation = seedLocation;
                }
            }
        }

        return minSeedLocation;

    }

    private static BigInteger getSeedLocation(BigInteger index, String from, String to, Map<String, Map<String, List<Triple>>> map) {
        BigInteger seedLoc = processTriples(index, map.get(from).get(to));
//        System.out.println("From: " + from + " To: " + to + " is " + seedLoc);

        if (to.equals("location")) {
            return seedLoc;
        } else {
            List<String> keyList = map.get(to).keySet().stream().toList();
            for (String key : keyList) {
                return getSeedLocation(seedLoc, to, key, map);
            }
        }
        return BigInteger.valueOf(-1);
    }

    private static BigInteger processTriples(BigInteger index, List<Triple> list) {
        for (Triple triple : list) {
            if (index.intValue() <= triple.getRangeEnd().intValue() && index.intValue() >= triple.getRangeStart().intValue()) {
//                System.out.println("that's a bingo!!");
                return index.add(triple.getSumMe());
            }
        }
        return index;
    }
}
