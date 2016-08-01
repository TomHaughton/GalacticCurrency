package com.company;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String testValue = "IX";

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
        if (validateNumerals(numerals)) {
            List<Integer> arabic = convertToArabic(numerals);
            Integer cost = calculateCost(arabic);
            System.out.println(cost == null ? "Invalid Number" : cost.toString());
        } else{
            System.out.println("Invalid number");
        }
    }

    private static char[] parseNumerals(String rawinput) {

        String rawInput = rawinput.toUpperCase();
        char[] numerals = rawInput.toCharArray();

        return numerals;
    }

    private static boolean validateNumerals(char[] nums){

        char compare = 'A';
        int count = 0;
        for (char ch:nums) {

            if (ch != 'I' && ch != 'V' && ch != 'X' &&  ch != 'L' && ch != 'C' && ch != 'D' && ch != 'M'){
                return false;
            }

            if (ch == 'I' || ch == 'X' || ch == 'C' || ch == 'M'){
                if (ch == compare){
                    count++;
                    if (count == 3){
                        return false;
                    }
                } else{
                    compare = 'A';
                    count = 0;
                }
                compare = ch;
            }
            else{
                compare = 'A';
                count = 0;
            }
        }
        return true;
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
        boolean subtracted = false;

        for (int i = arabic.size() - 1; i > 0; i--) {
            if (arabic.get(i) > arabic.get(i - 1)) {
                if(!added) {
                    cost += arabic.get(i);
                }
                added = true;
                if (!subtracted) {
                    cost -= arabic.get(i - 1);
                    subtracted = true;
                } else {
                    return null;
                }
            } else if (arabic.get(i) < arabic.get(i - 1)) {
//                if (subtracted){
//                    return null;
//                }
                if(!added) {
                    cost += arabic.get(i);
                }
                added = true;
                subtracted = false;
                cost += arabic.get(i - 1);
            } else if (arabic.get(i).equals(arabic.get(i - 1))) {
                if (subtracted){
                    return null;
                }
                if(!added) {
                    cost += arabic.get(i);
                }
                cost += arabic.get(i - 1);
                subtracted = false;
                added = true;
            } else {
                if (subtracted){
                    return null;
                }
                cost += arabic.get(i - 1);
                added = false;
                subtracted = false;
            }
        }

        return cost;
    }
}