package main.java.org.adventofcode2023.Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day8 {

    // Part Two
    // 774 - Not right

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
    private static boolean isDone = true;

    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day8/List"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day8/Test3"));

        System.out.println("we're guessing: " + iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;
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
                    System.out.println("left was null");
                    left = new Node(first);
                    nodeMap.put(first, left);
                }

                if (right == null && !first.equals(second)) {
                    System.out.println("right was null");
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

        sum = processMoves(combo);

        return sum;
    }

    private static Set<Node> getANodes() {
        Set<Node> ret = new HashSet<>();

        for (String key : nodeMap.keySet()) {
            char[] keyC = key.toCharArray();
            if ('A' == keyC[keyC.length - 1]) {
                ret.add(nodeMap.get(key));
            }
        }
        return ret;
    }

    private static boolean done(Set<Node> nodeList) {
        if (!isDone) {
            isDone = true;
            return false;
        }
        for (Node n  : nodeList) {
            char[] name = n.getName().toCharArray();
            if (name[name.length-1] != 'Z') {
                return false;
            }
        }
        return true;
    }

    private static Set<Node> processMove(Set<Node> nodeList, boolean moveLeft) {
        Set<Node> ret = new HashSet<>();
        for (Node n : nodeList) {
            if (moveLeft) {
                char[] nextName = n.getLeft().getName().toCharArray();
                if (nextName[nextName.length - 1] != 'Z') {
                    isDone = false;
                }
                ret.add(n.getLeft());
            } else {
                char[] nextName = n.getRight().getName().toCharArray();
                if (nextName[nextName.length - 1] != 'Z') {
                    isDone = false;
                }
                ret.add(n.getRight());
            }
        }
        return ret;
    }

    private static int processMoves(String combo) {
        Set<Node> nodeSet = getANodes();
        List<Node> nodeList = getANodes().stream().toList();
        int comboSize = combo.length();

        int count = 0;

        while (!done(nodeSet)) {
            int move = count % comboSize;
            String moveString = combo.substring(move, move + 1);
            if (moveString.equals("L")) {
                nodeSet = processMove(nodeSet, /*= moveLeft */ true);
            } else {
                nodeSet = processMove(nodeSet, /*= moveLeft */  false);
            }
            System.out.println(nodeSet.stream().toList().get(0).getName());
            count++;
        }
        return count;
    }
}
