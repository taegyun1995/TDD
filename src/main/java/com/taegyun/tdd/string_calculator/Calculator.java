package com.taegyun.tdd.string_calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 문자열 계산기
 * - 문자열로 전달된 숫자들의 덧셈/뺄셈을 계산한다.
 * - 기본 구분자: 쉼표(,), 줄바꿈(\n)
 * - 커스텀 구분자: "//구분자\n" 또는 "//[구분자1][구분자2]\n" 형식
 */
public class Calculator {

    private static final String DEFAULT_DELIMITER = ",|\n";

    private record ParsedInput(
            String delimiter,
            String numbers
    ) {}

    /**
     * 숫자들의 합을 반환한다.
     * - 빈 문자열 또는 null → 0 반환
     * - 1000 초과 숫자는 무시
     */
    public int add(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        ParsedInput parsed = parse(input);
        String[] tokens = parsed.numbers().split(parsed.delimiter());

        validate(tokens);

        return sum(tokens);
    }

    /**
     * 첫 번째 숫자에서 나머지 숫자들을 뺀 결과를 반환한다.
     * - 빈 문자열 또는 null → 0 반환
     * - 1000 초과 숫자는 무시
     */
    public int subtract(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        ParsedInput parsed = parse(input);
        String[] tokens = parsed.numbers().split(parsed.delimiter());

        validate(tokens);

        int result = Integer.parseInt(tokens[0].trim());
        for (int i = 1; i < tokens.length; i++) {
            int number = Integer.parseInt(tokens[i].trim());
            if (number <= 1000) {
                result -= number;
            }
        }

        return result;
    }

    /**
     * 입력 문자열에서 구분자와 숫자를 분리한다.
     * - 기본 구분자: 쉼표(,) 또는 줄바꿈(\n)
     * - 단일 커스텀 구분자: "//;\n1;2;3"
     * - 여러 커스텀 구분자: "//[*][%]\n1*2%3"
     */
    private ParsedInput parse(String input) {
        if (!input.startsWith("//")) {
            return new ParsedInput(DEFAULT_DELIMITER, input);
        }

        int newlineIndex = input.indexOf("\n");
        String delimiterPart = input.substring(2, newlineIndex);
        String numbers = input.substring(newlineIndex + 1);

        String delimiter = delimiterPart.startsWith("[")
                ? parseMultipleDelimiters(delimiterPart)
                : Pattern.quote(delimiterPart);

        return new ParsedInput(delimiter, numbers);
    }

    /**
     * 대괄호로 묶인 여러 구분자를 정규식 OR 패턴으로 변환한다.
     * 예: "[*][%]" → "\*|%"
     */
    private String parseMultipleDelimiters(String delimiterPart) {
        Pattern pattern = Pattern.compile("\\[(.+?)\\]");
        Matcher matcher = pattern.matcher(delimiterPart);

        List<String> delimiters = new ArrayList<>();
        while (matcher.find()) {
            delimiters.add(Pattern.quote(matcher.group(1)));
        }

        return String.join("|", delimiters);
    }

    /**
     * 토큰 유효성 검증
     * - 숫자가 아닌 값 → RuntimeException
     * - 음수 → RuntimeException
     */
    private void validate(String[] tokens) {
        List<String> invalidValues = new ArrayList<>();
        List<Integer> negativeNumbers = new ArrayList<>();

        for (String token : tokens) {
            String trimmed = token.trim();
            try {
                int number = Integer.parseInt(trimmed);
                if (number < 0) {
                    negativeNumbers.add(number);
                }
            } catch (NumberFormatException e) {
                invalidValues.add(trimmed);
            }
        }

        if (!invalidValues.isEmpty()) {
            throw new RuntimeException("숫자가 아닌 값이 포함되어 있습니다: " + String.join(", ", invalidValues));
        }

        if (!negativeNumbers.isEmpty()) {
            String negativeStr = negativeNumbers.stream()
                                                .map(String::valueOf)
                                                .reduce((a, b) -> a + ", " + b)
                                                .orElse("");
            throw new RuntimeException("음수는 허용되지 않습니다: " + negativeStr);
        }
    }

    /**
     * 숫자들의 합을 계산한다. (1000 초과 숫자는 무시)
     */
    private int sum(String[] tokens) {
        int result = 0;
        for (String token : tokens) {
            int number = Integer.parseInt(token.trim());
            if (number <= 1000) {
                result += number;
            }
        }
        return result;
    }
}
