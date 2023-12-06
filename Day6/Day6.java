package main.java.org.adventofcode2023.Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day6 {

    public static void main(String[] args) throws IOException {
                BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day6/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day6/Test"));

        System.out.println(iterate(reader));
    }

    private static BigInteger iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        BigInteger time;
        BigInteger distance;
        BigInteger winners = BigInteger.ZERO;

        StringBuilder timeS = new StringBuilder();
        StringBuilder distanceS = new StringBuilder();

        while (line != null) {
            System.out.println(line);
            String title = line.split(":")[0];
            String values = line.split(":")[1].trim();

            String[] valueList = values.split(" ");

            for (String v : valueList) {
                if (!v.isBlank()) {
                    if (title.equals("Time")) {
                        timeS.append(v);
                    } else {
                        distanceS.append(v);
                    }
                }
            }
            line = reader.readLine();
        }

        time = new BigInteger(timeS.toString());
        distance = new BigInteger(distanceS.toString());

        for (BigInteger i = BigInteger.ZERO; i.compareTo(time.divide(BigInteger.valueOf(2))) < 0; i = i.add(BigInteger.ONE)) {
            BigInteger optimalJ = i.divide(BigInteger.valueOf(2)).add(BigInteger.ONE);
                BigInteger speed = time.subtract(optimalJ);
                BigInteger turnDistance = speed.multiply(optimalJ);
                if (turnDistance.compareTo(distance) > 0) {
                    System.out.println(optimalJ);
                    return time.subtract(optimalJ.multiply(BigInteger.valueOf(2)).subtract(BigInteger.valueOf(1)));
                }
        }

        System.out.println("winners: " + winners);

        return winners;
    }
}
