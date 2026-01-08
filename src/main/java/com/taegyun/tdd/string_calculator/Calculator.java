package com.taegyun.tdd.string_calculator;

public class Calculator {

    public int add(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        int result = 0;
        String[] split = text.split(",|\n");
        for (String s : split) {
            int integer = Integer.parseInt(s.trim());
            result += integer;
        }

        return result;
    }

}
