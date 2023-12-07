package main.java.org.adventofcode2023.Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day7 {

    // Part one
    // 128875101 - not right

    static class Triple {

        private final String key;
        private final Integer bet;
        private final Hands hands;

        Triple(String key, Integer bet, Hands hands) {
            this.key = key;
            this.bet = bet;
            this.hands = hands;
        }

        public String getKey() {
            return key;
        }

        public Integer getBet() {
            return bet;
        }

        public Hands getHands() {
            return hands;
        }
    }


    enum Hands {

        FiveKind(6),
        FourKind(5),
        FullHouse(4),
        ThreeKind(3),
        TwoPair(2),
        OnePair(1),
        HighCard(0);

        public final int value;


        Hands(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static final List<String> list = List.of("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");

    public static void main(String[] args) throws IOException {
                BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day7/List"));
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/M/IdeaProjects/Advent of Code 2023/src/main/java/org/adventofcode2023/Day7/Test"));

        System.out.println(iterate(reader));
    }

    private static int iterate(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int sum = 0;
        List<Triple> sortedList = new ArrayList<>();

        while (line != null) {
            System.out.println(line);
            String hand = line.split(" ")[0];
            int bet = Integer.parseInt(line.split(" ")[1]);

            Hands handValue = getValue(hand);
            Triple trip = new Triple(hand, bet, handValue);
//            Map<Hands, Integer> innerMap = new HashMap<>();
//            innerMap.put(handValue, bet);

//            lookupMap.put(hand, trip);
            sortAdd(sortedList, trip);
            line = reader.readLine();
        }

        sum = score(sortedList);

        return sum;
    }

    private static int score(List<Triple> list) {
        int score = 0;

        for (int i = 0; i < list.size(); i++) {
            Triple trip = list.get(i);

            System.out.println("Rank #: " + i + " goes to " + trip.getKey());
            System.out.println(trip.getBet());
            score += trip.getBet() * (list.size() - i);

        }

        return score;
    }

    private static void sortAdd(List<Triple> sortedList, Triple addMe) {
        int newValue = addMe.getHands().getValue();
        String addKey = addMe.getKey();

        if (sortedList.isEmpty()) {
            sortedList.add(addMe);
            return;
        }

        for (int i = 0; i < sortedList.size(); i++) {
            Triple trip = sortedList.get(i);
            int val = trip.getHands().getValue();
            String key = trip.getKey();

            System.out.println(key + " : " + addKey);

            if (val < newValue) {
                sortedList.add(i, addMe);
                System.out.println("val : " + val + " less than " + newValue);
//                System.out.println("new List : ");
                return;
            } else if (val == newValue) {
                System.out.println(val);
                // Tie breaker
                for (int j = 0; j < addKey.length(); j++) {
                    String existing = key.split("")[j];
                    String addChar = addKey.split("")[j];
                    System.out.println("Comparing: " + existing + " and " + addChar);
                    System.out.println("Comparing: " + list.indexOf(existing) + " and " + list.indexOf(addChar));

                    if (list.indexOf(existing) > list.indexOf(addChar)) {
                        sortedList.add(i, addMe);
                        return;
                    } else if (list.indexOf(existing) < list.indexOf(addChar)) {
                        break;
                    }
                }
            }
        }
    }

    private static Hands getValue(String hand) {
        Set<String> cardSet = new HashSet<>(Arrays.asList(hand.split("")));

        if (cardSet.size() == 5) {
            return Hands.HighCard;
        }
        if (cardSet.size() == 1) {
            return Hands.FiveKind;
        }
        if (cardSet.size() == 3) {
            // Three kind or Two Pair
            StringBuilder spares = new StringBuilder();
            Set<String> newSet = new HashSet<>();
            for (String s : hand.split("")) {
                if (!newSet.contains(s)) {
                    newSet.add(s);
                } else {
                    spares.append(s);
                }
            }
            if (spares.toString().split("")[0].equals(spares.toString().split("")[1])) {
                return Hands.ThreeKind;
            }
            return Hands.TwoPair;
        }
        if (cardSet.size() == 4) {
            return Hands.OnePair;
        }
        if (cardSet.size() == 2) {
            // Full House or Four Kind
            String spares = "";
            Set<String> newSet = new HashSet<>();
            for (String s : hand.split("")) {
                if (!newSet.contains(s)) {
                    newSet.add(s);
                } else {
                    spares += s;
                }
            }
            if (spares.split("")[0].equals(spares.split("")[1]) && spares.split("")[0].equals(spares.split("")[2])) {
                return Hands.FourKind;
            }
            return Hands.FullHouse;
        }
        return Hands.HighCard;
    }
}
