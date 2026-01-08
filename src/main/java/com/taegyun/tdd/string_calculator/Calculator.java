package com.taegyun.tdd.string_calculator;

public class Calculator {

    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }

        String delimiter = ",|\n";

        // 커스텀 구분자 처리
        if (numbers.startsWith("//")) {
            int newlineIndex = numbers.indexOf("\n");
            delimiter = numbers.substring(2, newlineIndex);
            numbers = numbers.substring(newlineIndex + 1);
        }

        int result = 0;
        String[] split = numbers.split(delimiter);
        for (String s : split) {
            result += Integer.parseInt(s.trim());
        }

        return result;
    }

}
