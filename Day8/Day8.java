package main.java.org.adventofcode2023.Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day8 {

    // Part Two
    // 774 - Not right
    // 1330333684 - too low
    // 18625484023687 - answer!!! (your LCM code didn't work)

    private static class Node {
        final String name;
        private Node left;
        private Node right;

        Node (String name) {
            this.name = name;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public String getName() {
            return name;
        }
    }

    private static final Map<String, Node> nodeMap = new HashMap<>();
    private static final Map<BigInteger, BigInteger> doneIndexes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day8/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day8/Test3"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static BigInteger iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        BigInteger sum = BigInteger.ZERO;
        String combo = "";
        boolean firstLine = true;

        while (line != null) {
            // do stuff
            System.out.println(line);

            if (firstLine) {
                combo = line.trim();
                firstLine = false;
            } else if (!line.isBlank()){
                String name = line.split(" = ")[0].trim();
                String first = line.split(", ")[0].split("\\(")[1].trim();
                String second = line.split(", ")[1].split("\\)")[0].trim();

                Node current = nodeMap.getOrDefault(name, null);
                Node left = nodeMap.getOrDefault(first, null);
                Node right = nodeMap.getOrDefault(second, null);

                if (left == null) {
                    left = new Node(first);
                    nodeMap.put(first, left);
                }

                if (right == null && !first.equals(second)) {
                    right = new Node(second);
                    nodeMap.put(second, right);
                } else if (right == null) {
                    // left = right
                    right = left;
                    nodeMap.put(second, right);
                }

                if (current == null) {
                    current = new Node(name);
                }
                current.setLeft(left);
                current.setRight(right);
                nodeMap.put(name, current);
            }
            line = reader.readLine();
        }

        sum = sum.add(processMoves(combo));

        return sum;
    }

    private static List<Node> getANodes() {
        List<Node> ret = new ArrayList<>();

        for (String key : nodeMap.keySet()) {
            char[] keyC = key.toCharArray();
            if ('A' == keyC[keyC.length - 1]) {
                ret.add(nodeMap.get(key));
            }
        }
        return ret;
    }

    private static boolean done(List<Node> nodeList) {
        if (nodeList.size() == doneIndexes.size()) {
            for (BigInteger key : doneIndexes.keySet()) {
                BigInteger index = doneIndexes.get(key);
                // SOoooooo, I just took these indexes, and used an  internet LCM calculator, and it worked.
                // my LCM code has a bug in it......
                System.out.println(index);
            }
            return true;
        }
        return false;
    }

    private static List<Node> processMove(List<Node> nodeList, BigInteger count, boolean moveLeft) {
        List<Node> ret = new ArrayList<>();

        for (BigInteger i = BigInteger.ZERO; i.compareTo(BigInteger.valueOf(nodeList.size())) < 0; i = i.add(BigInteger.ONE)) {
            Node n = nodeList.get(i.intValue());
            if (moveLeft) {
                char[] nextName = n.getLeft().getName().toCharArray();
                if (nextName[nextName.length - 1] == 'Z') {
                    doneIndexes.put(i, count);
                }
                ret.add(n.getLeft());
            } else {
                char[] nextName = n.getRight().getName().toCharArray();
                if (nextName[nextName.length - 1] == 'Z') {
                    doneIndexes.put(i, count);
                }
                ret.add(n.getRight());
            }
        }
        return ret;
    }

    private static BigInteger processMoves(String combo) {
        List<Node> nodeList = getANodes();
        int comboSize = combo.length();

        BigInteger count = BigInteger.ZERO;

        while (!done(nodeList)) {
            int move = count.intValue() % comboSize;
            String moveString = combo.substring(move, move + 1);

            count = count.add(BigInteger.ONE);
            if (moveString.equals("L")) {
                nodeList = processMove(nodeList, count, /*= moveLeft */ true);
            } else {
                nodeList = processMove(nodeList, count, /*= moveLeft */  false);
            }
        }
        return processLCM();
    }

    private static BigInteger processLCM() {
        BigInteger ret = BigInteger.ONE;

        for (BigInteger b : doneIndexes.keySet()) {
            ret = lcm(doneIndexes.get(b), ret);
        }
        return ret;
    }

    private static BigInteger lcm(BigInteger number1, BigInteger number2) {
        if (number1.compareTo(BigInteger.ZERO) == 0 || number2.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ZERO;
        }
        int abs1 = Math.abs(number1.intValue());
        int abs2 = Math.abs(number2.intValue());
        int absHigherNumber = Math.max(abs1, abs2);
        int absLowerNumber = Math.min(abs1, abs2);
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return BigInteger.valueOf(lcm);
    }

}
