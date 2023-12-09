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

    private static class Node {
        final String name;
        private Node left;
        private Node right;

        Node (String name) {
            this.name = name;
        }

        Node(String name, Node left, Node right) {
            this.name = name;
            this.left = left;
            this.right = right;
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
    private static Map<BigInteger, BigInteger> doneIndexes = new HashMap<>();

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
            Set<Character> lineSet = new HashSet<>();

            if (firstLine) {
                combo = line.trim();
                firstLine = false;
            } else if (!line.isBlank()){
                String name = line.split(" = ")[0].trim();
                String first = line.split(", ")[0].split("\\(")[1].trim();
                String second = line.split(", ")[1].split("\\)")[0].trim();
                System.out.println(name + " : " + first + " : " + second);

                Node current = nodeMap.getOrDefault(name, null);
                Node left = nodeMap.getOrDefault(first, null);
                Node right = nodeMap.getOrDefault(second, null);

                System.out.println(name);
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
                if (name.equals("AAA")) {
                    System.out.println(current.getLeft().name);
                }
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
            return true;
        }

//        for (Node n  : nodeList) {
//            char[] name = n.getName().toCharArray();
//            if (name[name.length-1] != 'Z') {
//                return false;
//            }
//        }
        return false;
    }

    private static List<Node> processMove(List<Node> nodeList, BigInteger count, boolean moveLeft) {
        List<Node> ret = new ArrayList<>();
        System.out.println("the count is: " + count);

        for (BigInteger i = BigInteger.ZERO; i.compareTo(BigInteger.valueOf(nodeList.size())) < 0; i = i.add(BigInteger.ONE)) {
            Node n = nodeList.get(i.intValue());
            if (moveLeft) {
                char[] nextName = n.getLeft().getName().toCharArray();
                if (nextName[nextName.length - 1] == 'Z') {
                    if (doneIndexes.size() >= count.intValue() && doneIndexes.get(count) != null && doneIndexes.get(count).intValue() >= 0) {
                        System.out.println("we got a duplicate? " + doneIndexes.get(count) + " : " + count);
                    }
                    System.out.println("Z!! " + "put " + count + " into " + i);
                    doneIndexes.put(i, count);
                }
                ret.add(n.getLeft());
            } else {
                char[] nextName = n.getRight().getName().toCharArray();
                if (nextName[nextName.length - 1] == 'Z') {
                    System.out.println("Z!!");
                    if (doneIndexes.size() >= count.intValue() && doneIndexes.get(count) != null && doneIndexes.get(count).intValue() >= 0) {
                        System.out.println("we got a duplicate? " + doneIndexes.get(count) + " : " + count);
                    }
                    System.out.println("Z!! " + "put " + count + " into " + i);
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
            System.out.println(nodeList.get(0).getName());
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
        int absHigherNumber = Math.max(number1.intValue(), number2.intValue());
        int absLowerNumber = Math.min(number1.intValue(), number2.intValue());
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return BigInteger.valueOf(lcm);
    }

}
