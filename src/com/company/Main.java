package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String testValue = "MMCDXCVI";

    private enum conversionKey {
        I (1, "glob"),
        V (5, "prok"),
        X (10, "pish"),
        L (50, "tegj"),
        C (100, ""),
        D (500, ""),
        M (1000, "");

        private int value;
        private String language;

        private conversionKey(int value, String lang) {
            this.value = value;
            this.language = lang;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {

        char[] numerals = parseNumerals(testValue);
        List<Integer> arabic = convertToArabic(numerals);
        Integer cost = calculateCost(arabic);
        System.out.println(cost.toString());
    }

    public static char[] parseNumerals(String rawinput) {

        String rawInput = rawinput.toUpperCase();
        char[] numerals = rawInput.toCharArray();
        return numerals;

    }

    private static List<Integer> convertToArabic(char[] numerals) {

        List<Integer> arabicList = new ArrayList<>();

        for (char ch : numerals) {
            Integer tmp = conversionKey.valueOf(String.valueOf(ch)).getValue();
            arabicList.add(tmp);
        }

        return arabicList;
    }

    private static Integer calculateCost(List<Integer> arabic) {

        Integer cost = 0;

        boolean added = false;

        for (int i = arabic.size() - 1; i > 0; i--) {
            if (arabic.get(i) > arabic.get(i - 1)) {
                if(!added) {
                    cost += arabic.get(i);
                }
                added = true;
                cost -= arabic.get(i - 1);
            } else if (arabic.get(i) < arabic.get(i - 1)) {
                if(!added) {
                    cost += arabic.get(i);
                }
                added = true;
                cost += arabic.get(i - 1);
            } else {
                cost += arabic.get(i - 1);
                added = false;
            }
        }

        return cost;
    }
}