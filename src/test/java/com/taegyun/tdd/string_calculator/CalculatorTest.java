package com.taegyun.tdd.string_calculator;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;

class CalculatorTest {

    @Test
    void 빈_문자열_또는_null이면_0을_반환한다() {
        Calculator calculator = new Calculator();

        Assertions.assertThat(calculator.add(null)).isEqualTo(0);
        Assertions.assertThat(calculator.add("")).isEqualTo(0);
    }

    @Test
    void 숫자_하나면_해당_숫자를_반환한다() {
        Calculator calculator = new Calculator();

        Assertions.assertThat(calculator.add("7")).isEqualTo(7);
    }

    @Test
    void 쉼표_구분자로_숫자의_합을_반환한다() {
        Calculator calculator = new Calculator();

        Assertions.assertThat(calculator.add("1,2,3")).isEqualTo(6);
    }

    @Test
    void 공백을_포함한_쉼표_구분자로_숫자의_합을_반환한다() {
        Calculator calculator = new Calculator();

        Assertions.assertThat(calculator.add("1, 2, 3")).isEqualTo(6);
    }

    @Test
    void 쉼표_외에_줄바꿈_구분자도_숫자의_합을_반환한다() {
        Calculator calculator = new Calculator();

        Assertions.assertThat(calculator.add("1, 2\n 3")).isEqualTo(6);
    }
}
