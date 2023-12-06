package main.java.org.adventofcode2023.Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day5 {

    private static class SeedTriple {
        private final BigInteger rangeStart;
        private final BigInteger rangeEnd;

        SeedTriple(BigInteger rangeStart, BigInteger rangeEnd) {
            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
        }

        public BigInteger getRangeStart() {
            return rangeStart;
        }

        public BigInteger getRangeEnd() {
            return rangeEnd;
        }
    }

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

    private static final Set<SeedTriple> seedSet = new HashSet<>();

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
                for (int i = 0; i < seedList.length; i += 2) {
                    BigInteger rangeStart = new BigInteger(seedList[i]);
                    seedSet.add(new SeedTriple(rangeStart, new BigInteger(seedList[i+1]).add(rangeStart).subtract(BigInteger.ONE)));
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
                    BigInteger num = new BigInteger(numbers[i]);
                    if (i == 0) {
                        firstNum = num;
                    } else if (i == 1) {
                        secondNum = num;
                    } else {
                        range = num;
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

        List<SeedTriple> seedRangeList = seedSet.stream().toList();
        Set<BigInteger> seeds = new HashSet<>();
        for (SeedTriple seedTriple : seedRangeList) {
            BigInteger rangeStart = seedTriple.getRangeStart();
            BigInteger rangeEnd = seedTriple.getRangeEnd();
            while (rangeStart.compareTo(rangeEnd) < 0) {
//                System.out.println("Adding a seed");
                seeds.add(rangeStart);
                rangeStart = rangeStart.add(BigInteger.ONE);
            }
        }

        for (BigInteger seed : seeds.stream().toList()) {
            Map<String, List<Triple>> seedMap = map.get("seed");
            System.out.println(seed.intValue());

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
