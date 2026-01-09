package com.taegyun.tdd.string_calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Calculator {

    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }

        String delimiter = ",|\n";

        // 커스텀 구분자 처리
        if (numbers.startsWith("//")) {
            int newlineIndex = numbers.indexOf("\n");
            delimiter = Pattern.quote(numbers.substring(2, newlineIndex));
            numbers = numbers.substring(newlineIndex + 1);
        }

        int result = 0;
        String[] split = numbers.split(delimiter);
        List<Integer> negativeNumbers = new ArrayList<>();

        for (String s : split) {
            int number = Integer.parseInt(s.trim());

            if (number < 0) {
                negativeNumbers.add(number);
            }
            result += number;
        }

        if (!negativeNumbers.isEmpty()) {
            String negativeStr = String.join(
                    ", ",
                    negativeNumbers.stream().map(String::valueOf).toList()
            );
            throw new RuntimeException("음수는 허용되지 않습니다: " + negativeStr);
        }

        return result;
    }

}
