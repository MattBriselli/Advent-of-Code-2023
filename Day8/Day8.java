package main.java.org.adventofcode2023.Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day8 {

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

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day8/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day8/Test2"));

        System.out.println(iterate(reader));
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

    private static int processMoves(String combo) {
        Node next = nodeMap.get("AAA");
        int comboSize = combo.length();

        int count = 0;

        System.out.println(nodeMap.size());
        System.out.println(nodeMap.containsKey("AAA"));

        while (!next.getName().equals("ZZZ")) {
            int move = count % comboSize;
            String moveString = combo.substring(move, move + 1);
            System.out.println(moveString);
            System.out.println(next.getName());
            if (moveString.equals("L")) {
                next = next.getLeft();
            } else {
                next = next.getRight();
            }
            count++;
        }
        return count;
    }
}
